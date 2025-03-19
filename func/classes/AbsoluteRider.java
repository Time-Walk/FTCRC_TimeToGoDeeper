package org.firstinspires.ftc.teamcode.func.classes;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.func.classes.superclasses.Gettable;
import org.firstinspires.ftc.teamcode.func.classes.superclasses.PD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

@Config
public class AbsoluteRider {
    public static double xkp = .05;
    public static double xkd = .1;
    public static double ykp = .05;
    public static double ykd = .1;
    public static double rkp = .3;
    public static double rkd = .5;
    public static double x_maxdiff = 5;
    public static double y_maxdiff = 5;
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
        double Rx = constrain(xPd.tick(targetX-absX), .5);
        double Ry = constrain(yPd.tick(targetY-absY), .5);
        double Rr = constrain(rPd.tick(targetR-R.imuv2.getAngle()), .5);
        return new double[] {Rx, Ry, Rr};
    }

    public void getToPos(Gettable gettable) {
        if ( !resetR ) { targetR = R.imuv2.getAngle(); }
        double[] abses = gettable.getNewVals();
        while ( ( targetX - abses[0] > x_maxdiff || targetY - abses[1] > y_maxdiff || targetR - R.imuv2.getAngle() > r_maxdiff ) && R.P.L.opModeIsActive() ) {
            abses = gettable.getNewVals();
            double[] pw = tick(abses[0], abses[1]);
            R.wb.setAxisPower(pw[0], pw[1], pw[2]);
        }
        R.wb.setMtZero();
    }

}
