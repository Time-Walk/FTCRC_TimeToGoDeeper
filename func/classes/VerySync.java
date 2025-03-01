package org.firstinspires.ftc.teamcode.func.classes;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.func.classes.superclasses.PD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

@Config
public class VerySync {
    PD pd;
    RobotPortal R;
    public static double kp = .015;
    public static double kd = .065;
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
        if ( goal > 180 ) { goal = -180 - (180 - goal); }
        else if ( goal < -180 ) { goal = 180 + (180 + goal); }
        directionOfRotation = Math.signum(Math.round(goal - last));
        if ( Math.abs(last-goal) > 180 ) {
            if ( last < 0 ) {
                err180fix = 1;
                this.goal = goal+360;
                //directionOfRotation *= -1;
            }
            else {
                err180fix = -1;
                this.goal = goal-360;
                //directionOfRotation *= -1;
            }
        }
        else {
            this.goal = goal;
        }
        isRot = true;
    }
    public void tick() {
        if ( R.P.gamepad1.y ) { calcDir(R.imuv2.getAngle(), upAngle); }
        else if ( R.P.gamepad1.x ) { calcDir(R.imuv2.getAngle(), upAngle - 90); }
        else if ( R.P.gamepad1.a ) { calcDir(R.imuv2.getAngle(), upAngle - 180);}
        else if ( R.P.gamepad1.b ) { calcDir(R.imuv2.getAngle(), upAngle + 90); }
        else if ( R.P.gamepad1.options ) { upAngle = R.imuv2.getAngle(); }
        else if ( R.P.gamepad1.share ) { isRot = false; err180fix = 0; pwFR = 0; pwBR = 0; pwFL = 0; pwBL = 0; }
        if ( isRot ) {
            double angle = R.imuv2.getAngle();
            if (angle > 0 && err180fix == 1) {
                goal -= 360;
                err180fix = 0;
                directionOfRotation *= -1;
            }
            if (angle < 0 && err180fix == -1) {
                goal += 360;
                err180fix = 0;
                directionOfRotation *= -1;
            }
            angle += (360 * err180fix);
            double Er = goal - angle;
            double pw = pd.tick(Er);
            pwBL = pw;
            pwBR = pw;
            pwFL = pw;
            pwFR = pw;
            R.P.telemetry.addData("pw", pw);
            R.P.telemetry.addData("angle", angle);
            R.P.telemetry.addData("Er", Er);
            R.P.telemetry.addData("goal", goal);
            R.P.telemetry.addData("upAngle", upAngle);
            R.P.telemetry.addData("err180fix", err180fix);
            R.P.telemetry.addData("directionOfRot", directionOfRotation);
            R.P.telemetry.addData("RealAngle", R.imuv2.getAngle());
            R.P.telemetry.update();
            if ( directionOfRotation < 0 ) {
                if ( angle < goal || Math.abs(angle - goal) < 5 ) {
                    isRot = false;
                    pwFR = 0; pwBR = 0; pwFL = 0; pwBL = 0;
                    err180fix = 0;
                }
            }
            else {
                if ( angle > goal || Math.abs(angle - goal) < 5) {
                    isRot = false;
                    pwBL = 0; pwFL = 0; pwFR = 0; pwBR = 0;
                    err180fix = 0;
                }
            }
        }
        /*R.P.telemetry.addData("goal", goal);
        R.P.telemetry.addData("upAngle", upAngle);
        R.P.telemetry.addData("err180fix", err180fix);
        R.P.telemetry.addData("directionOfRot", directionOfRotation);
        R.P.telemetry.addData("RealAngle", R.imuv2.getAngle());
        R.P.telemetry.update();*/
    }
}
