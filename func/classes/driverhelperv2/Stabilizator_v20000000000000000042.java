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
    }
    public double getTarget() {
        return targetAngle;
    }
    public double tick() {
        int err180fix = 0;
        if ( err180state == 1 && as.RobotAngle < 0 ) { err180fix = 1; }
        if ( err180state == -1 && as.RobotAngle > 0 ){ err180fix = -1; }
        if ( err180state == 0 && as.RobotAngle > 90 && targetAngle < 0 ) { err180state = 1; }
        if ( err180state == 0 && as.RobotAngle < 90 && targetAngle > 0 ) { err180state = -1; }
        if ( err180state != 0 && Math.abs(as.RobotAngle) < 90 ) { err180state = 0; err180fix = 0; }
        double target = targetAngle + ( err180state * 360 );
        double angle = ( as.RobotAngle + ( err180fix * 360 ) );
        R.P.telemetry.addData("target", target);
        R.P.telemetry.addData("we", angle);
        return pdpdp.tick(target-angle);
    }
}
