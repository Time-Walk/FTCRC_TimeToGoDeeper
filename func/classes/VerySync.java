package org.firstinspires.ftc.teamcode.func.classes;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.func.classes.superclasses.PD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

@Config
public class VerySync {
    PD pd;
    RobotPortal R;
    public static double kp = .04;
    public static double kd = .3;
    public boolean isRot = false;
    public double upAngle = -91;
    public double goal = 0;
    public int err180fix = 0;
    public float directionOfRotation = 0;

    public double pwFL = 0;
    public double pwFR = 0;
    public double pwBL = 0;
    public double pwBR = 0;

    public VerySync(RobotPortal R) {
        this.R = R;
        pd = new PD(kp, kd);
    }
    public void calcDir(double last, double goal) {
        directionOfRotation = Math.signum(Math.round(goal - last));
        if ( Math.abs(last-goal) > 180 ) {
            if ( last < 0 ) {
                err180fix = 1;
                this.goal = goal+360;
                directionOfRotation *= -1;
            }
            else {
                err180fix = -1;
                this.goal = goal-360;
                directionOfRotation *= -1;
            }
        }
        else {
            this.goal = goal;
        }
        isRot = true;
    }
    public void tick() {
        if ( R.P.gamepad1.dpad_up ) { calcDir(R.imuv2.getAngle(), upAngle); }
        else if ( R.P.gamepad1.dpad_left ) { calcDir(R.imuv2.getAngle(), upAngle - 90); }
        else if ( R.P.gamepad1.dpad_down ) { calcDir(R.imuv2.getAngle(), upAngle - 180);}
        else if ( R.P.gamepad1.dpad_right ) { calcDir(R.imuv2.getAngle(), upAngle + 90); }
        else if ( R.P.gamepad1.options ) { upAngle = R.imuv2.getAngle(); }
        if ( isRot ) {
            double angle = R.imuv2.getAngle();
            if (angle > 0 && err180fix == 1) {
                goal -= 360;
                err180fix = 0;
            }
            if (angle < 0 && err180fix == -1) {
                goal += 360;
                err180fix = 0;
            }
            angle += (360 * err180fix);
            double Er = angle - goal;
            double pw = pd.tick(Er);
            pwBL = pw;
            pwBR = pw;
            pwFL = pw;
            pwFR = pw;
            if ( directionOfRotation < 0 ) {
                if ( angle < goal || Math.abs(angle - goal) < 5 ) {
                    isRot = false;
                    pwFR = 0; pwBR = 0; pwFL = 0; pwBL = 0;
                }
            }
            else {
                if ( angle > goal || Math.abs(angle - goal) < 5) {
                    isRot = false;
                    pwBL = 0; pwFL = 0; pwFR = 0; pwBR = 0;
                }
            }
        }
    }
}
