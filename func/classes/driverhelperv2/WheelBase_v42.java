package org.firstinspires.ftc.teamcode.func.classes.driverhelperv2;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.modules.IMUv2;
import org.firstinspires.ftc.teamcode.modules.Wheelbase;

@Config
public class WheelBase_v42 extends Wheelbase {
    public static double rotationSpeed = 10;
    public AngleSponsor as;
    public boolean isTimer = false;
    long timeout = System.currentTimeMillis();
    double timeredRx = 0;
    double timeredRy = 0;
    double timeredRr = 0;
    long timeredTimer = 0;
    public Stabilizator_v20000000000000000042 stblz;
    public void initWithAngleSponsor(AngleSponsor as) { this.as = as; }
    public void anotherDhInit() {
        stblz = new Stabilizator_v20000000000000000042(as.R, as);
    }
    @Override
    public void tele() {
        double Ry = -P.gamepad1.left_stick_y * Math.cos(Math.toRadians(as.RobotAngle)) + P.gamepad1.left_stick_x * Math.cos(Math.toRadians(90-as.RobotAngle));
        double Rx = P.gamepad1.left_stick_x * Math.cos(Math.toRadians(as.RobotAngle)) + P.gamepad1.left_stick_y * Math.cos(Math.toRadians(90-as.RobotAngle));
        P.telemetry.addData("angle", as.RobotAngle);
        P.telemetry.addData("real angle", as.R.imuv2.getAngle());
        P.telemetry.addData("Ry", Ry);
        P.telemetry.addData("Rx", Rx);
        double[] timeredPw = timerTick();
        stblz.setTarget(stblz.getTarget()+(P.gamepad1.right_stick_x*rotationSpeed));
        if ( P.gamepad1.a ) { stblz.setTarget(0); }
        if ( P.gamepad1.b ) { stblz.setTarget(90);}
        if ( P.gamepad1.x ) { stblz.setTarget(-90);}
        if ( P.gamepad1.y ) { stblz.setTarget(179.5);}
        setAxisPower(Rx+timeredPw[0], Ry+timeredPw[1], stblz.tick()+timeredPw[2]);
    }
    public void timeredMoving(double Rx, double Ry, double Rr, int timer) {
        timeredRx = Rx;
        timeredRy = Ry;
        timeredRr = Rr;
        timeredTimer = timer;
        timeout = System.currentTimeMillis();
        isTimer = true;
    }
    double[] timerTick() {
        if ( isTimer ) {
            if ( System.currentTimeMillis() - timeout > timeredTimer ) {
                isTimer = false;
                return new double[] {0, 0, 0};
            }
            return new double[] {timeredRx, timeredRy, timeredRr};
        }
        return new double[] {0, 0, 0};
    }
}
