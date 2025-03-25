package org.firstinspires.ftc.teamcode.func.classes.driverhelperv2;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.func.classes.superclasses.PD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

@Config
public class Stabilizator_v20000000000000000042 {
    public static double kp = .05;
    public static double kd = .1;
    public RobotPortal R;
    public AngleSponsor as;
    double targetAngle = 0;
    PD pdpdp;
    int err180state = 0;
    double lastAngle = 0;
    int _180_govnosranoe = 0;
    public Stabilizator_v20000000000000000042(RobotPortal R, AngleSponsor as) {
        this.R = R;
        this.as = as;
        pdpdp = new PD(kp, kd);
        targetAngle = as.upAngle;
    }
    public void setTarget(double angle) {
        if ( angle > 180 ) {
            angle = - ( 180 - ( angle - 180 ));
        }
        else if ( angle <= -180 ) {
            angle = 180 - ( angle + 180 );
        }
        targetAngle = angle;
        err180state = 0;
    }
    double constrain(double val, double abs_max) {
        if ( val > abs_max) { val = abs_max; }
        else if ( val < -abs_max ) { val = -abs_max; }
        return val;
    }
    public double getTarget() {
        return targetAngle;
    }
    public double tick() {
        as.tick();
        if ( Math.abs(as.RobotAngle) > 170 && Math.abs(targetAngle) > 170 ) { return R.P.gamepad1.right_stick_x*.6; }
        if ( R.P.gamepad1.start ) { setTarget(0); }
        int err180fix = 0;
        double target;
        if ( err180state == 1 && as.RobotAngle < 0 ) { err180fix = 1; }
        if ( err180state == -1 && as.RobotAngle > 0 ){ err180fix = -1; }
        if ( err180state == 0 && as.RobotAngle > 90 ) { err180state = 1; if ( targetAngle < 0 ) { target = targetAngle + 360; }}
        if ( err180state == 0 && as.RobotAngle < -90 ) { err180state = -1; if ( targetAngle > 0 ) { target = targetAngle - 360; }}
        target = targetAngle + ( -err180fix * 360 );
        double angle = ( as.RobotAngle + ( -err180fix * 360 ) );
        R.P.telemetry.addData("target", target);
        R.P.telemetry.addData("we", angle);
        return constrain(pdpdp.tick(target-angle), .6);
    }
}
