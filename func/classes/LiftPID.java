package org.firstinspires.ftc.teamcode.func.classes;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.func.classes.superclasses.PID;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

@Config
public class LiftPID {
    public RobotPortal R;
    public static double kp = .3;
    public static double ki = .03;
    public static double kd = .5;
    public static int possibleDifference = 10;
    public PID pid;
    int curTicks = 0;
    public LiftPID(RobotPortal R) {
        this.R = R;
        pid = new PID(kp, ki, kd);
        R.lift.initencoder();
    }
    public void setPosition(int ticks) {
        curTicks = ticks;
    }
    public double tick() {
        double Er = curTicks - R.lift.L.getCurrentPosition();
        return pid.tick(Er);
    }
    public void getToPosition() {
        while ( Math.abs( curTicks - R.lift.L.getCurrentPosition() ) > possibleDifference ) {
            R.lift.L.setPower(tick());
            R.P.telemetry.addData("Er", curTicks - R.lift.L.getCurrentPosition());
            R.P.telemetry.update();
        }
    }
}
