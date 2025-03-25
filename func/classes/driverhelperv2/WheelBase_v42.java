package org.firstinspires.ftc.teamcode.func.classes.driverhelperv2;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.modules.IMUv2;
import org.firstinspires.ftc.teamcode.modules.Wheelbase;

@Config
public class WheelBase_v42 extends Wheelbase {
    public static double rotationSpeed = 10;
    public static double dpadSpeed = .7;
    public static double speedKoef = .33;
    public AngleSponsor as;
    public boolean isTimer = false;
    long timeout = System.currentTimeMillis();
    double timeredRx = 0;
    double timeredRy = 0;
    double timeredRr = 0;
    long timeredTimer = 0;
    boolean speedFlag = false;
    double currSpeedKoef = 1;
    public Stabilizator_v20000000000000000042 stblz;
    public DebugedLol dbg;
    boolean wasRotatedByStick = false;
    boolean subMode = false;
    public void initWithAngleSponsor(AngleSponsor as) { this.as = as; }
    public void anotherDhInit(DebugedLol dbg) {
        stblz = new Stabilizator_v20000000000000000042(as.R, as);
        this.dbg = dbg;
    }
    @Override
    public void tele() {
        double addJx = 0;
        double addJy = 0;
        if ( P.gamepad1.dpad_down )  { addJy = -dpadSpeed; }
        if ( P.gamepad1.dpad_up )    { addJy = dpadSpeed;  }
        if ( P.gamepad1.dpad_left )  { addJx = -dpadSpeed; }
        if ( P.gamepad1.dpad_right ) { addJx = dpadSpeed;  }
        double Ry = -P.gamepad1.left_stick_y * Math.cos(Math.toRadians(as.RobotAngle)) + P.gamepad1.left_stick_x * Math.cos(Math.toRadians(90-as.RobotAngle));
        Ry += addJy * Math.cos(Math.toRadians(as.RobotAngle)) + addJx * Math.cos(Math.toRadians(90-as.RobotAngle));
        double Rx = P.gamepad1.left_stick_x * Math.cos(Math.toRadians(as.RobotAngle)) + P.gamepad1.left_stick_y * Math.cos(Math.toRadians(90-as.RobotAngle));
        Rx += addJx * Math.cos(Math.toRadians(as.RobotAngle)) + addJy * Math.cos(Math.toRadians(90-as.RobotAngle));
        P.telemetry.addData("angle", as.RobotAngle);
        P.telemetry.addData("real angle", as.R.imuv2.getAngle());
        P.telemetry.addData("Ry", Ry);
        P.telemetry.addData("Rx", Rx);
        double[] timeredPw = timerTick();
        //stblz.setTarget(stblz.getTarget()+(P.gamepad1.right_stick_x*rotationSpeed));
        if ( P.gamepad1.left_bumper ) {
            subMode = true;
        }
        if ( P.gamepad1.left_trigger > .25 ) {
            subMode = false;
        }
        double Rr = stblz.tick();
        if ( Math.abs(P.gamepad1.right_stick_x) > .0851234 || subMode ) {
            wasRotatedByStick = true;
            Rr = P.gamepad1.right_stick_x*.6;
        }
        else {
            if  ( wasRotatedByStick ) {
                as.tick();
                stblz.setTarget(as.RobotAngle);
                wasRotatedByStick = false;
            }
        }
        if ( P.gamepad1.y ) { stblz.setTarget(0); }
        if ( P.gamepad1.b ) { stblz.setTarget(90);}
        if ( P.gamepad1.x ) { stblz.setTarget(-90);}
        if ( P.gamepad1.a ) { stblz.setTarget(179.5);}
        if ( P.gamepad1.right_bumper ) {
            if ( !speedFlag ) {
                speedFlag = true;
                if ( currSpeedKoef > .99 ) { currSpeedKoef = speedKoef; }
                else { currSpeedKoef = 1; }
            }
        }
        else { if ( speedFlag ) { speedFlag = false; } }
        setAxisPower(Rx+timeredPw[0], Ry+timeredPw[1], Rr+timeredPw[2]);
    }
    public void timeredMoving(double Rx, double Ry, double Rr, int timer) {
        timeredRx = Rx;
        timeredRy = Ry;
        timeredRr = Rr;
        timeredTimer = timer;
        timeout = System.currentTimeMillis();
        isTimer = true;
    }
    @Override
    public void setAxisPower(double Rx, double Ry, double Rr) {
        double lf = Ry + Rx + Rr;
        double lb = Ry - Rx + Rr;
        double rf = -Ry + Rx + Rr;
        double rb = -Ry - Rx + Rr;
        setMtPower(lf*currSpeedKoef, lb*currSpeedKoef, rf*currSpeedKoef, rb*currSpeedKoef);
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
