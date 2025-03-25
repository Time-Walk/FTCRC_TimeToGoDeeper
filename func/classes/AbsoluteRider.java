package org.firstinspires.ftc.teamcode.func.classes;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.func.classes.superclasses.Gettable;
import org.firstinspires.ftc.teamcode.func.classes.superclasses.PD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

@Config
public class AbsoluteRider {
    public static double xkp = .04;
    public static double xkd = .2;
    public static double ykp = .04;
    public static double ykd = .2;
    public static double rkp = .05;
    public static double rkd = .1;
    public static double x_maxdiff = 2;
    public static double y_maxdiff = 2;
    public static double r_maxdiff = 3;
    public PD xPd;
    public PD yPd;
    public PD rPd;
    public RobotPortal R;

    double targetX = 0;
    double targetY = 0;
    double targetR = 0;
    boolean resetR = true;

    public AbsoluteRider(RobotPortal R) {
        this.R = R;
        xPd = new PD(xkp, xkd);
        yPd = new PD(ykp, ykd);
        rPd = new PD(rkp, rkd);
    }

    public void setGoal(double tx, double ty) {
        targetX = tx; targetY = ty;
    }

    public void andRot(double tr) {
        targetR = tr; resetR = true;
    }

    public void notRotNotReset() {
        resetR = false;
    }

    public void notRot() {
        targetR = R.imuv2.getAngle();
        resetR = false;
    }

    double constrain(double val, double abs_max) {
        if ( val > abs_max) { val = abs_max; }
        else if ( val < -abs_max ) { val = -abs_max; }
        return val;
    }

    public double[] tick(double absX, double absY) {
        double Rx = xPd.tick(targetX-absX);
        double Ry = yPd.tick(targetY-absY);
        double Rr = rPd.tick(targetR-R.imuv2.getAngle());
        Rx = Rx * Math.cos(Math.toRadians(R.imuv2.getAngle())) + Ry * Math.cos(Math.toRadians(90+R.imuv2.getAngle()));
        Ry = Ry * Math.cos(Math.toRadians(R.imuv2.getAngle())) - Rx * Math.cos(Math.toRadians(90+R.imuv2.getAngle()));
        double Rx_ = Ry;
        Ry = Rx;
        Rx = -Rx_;
        Rx = constrain(Rx, .3);
        Ry = constrain(Ry, .3);
        Rr = constrain(Rr, .3);
        double lf = constrain(Ry + Rx + Rr, .5);
        double lb = constrain(Ry - Rx + Rr, .5);
        double rf = constrain(-Ry + Rx + Rr, .5);
        double rb = constrain(-Ry - Rx + Rr, .5);
        return new double[] {lf, lb, rf, rb};
    }

    public void getToPos(Gettable gettable) {
        if ( !resetR ) { targetR = R.imuv2.getAngle(); }
        double[] abses = gettable.getNewVals();
        while ( ( Math.abs(targetX - abses[0]) > x_maxdiff || Math.abs(targetY - abses[1]) > y_maxdiff || Math.abs(targetR - R.imuv2.getAngle()) > r_maxdiff ) && R.P.L.opModeIsActive() ) {
            abses = gettable.getNewVals();
            double[] pw = tick(abses[0], abses[1]);
            R.P.telemetry.addData("abs x", abses[0]);
            R.P.telemetry.addData("abs y", abses[1]);
            R.P.telemetry.addData("t x", targetX);
            R.P.telemetry.addData("t y", targetY);
            R.wb.setMtPower(pw[0], pw[1], pw[2], pw[3]);
        }
        R.wb.setMtZero();
    }

}
