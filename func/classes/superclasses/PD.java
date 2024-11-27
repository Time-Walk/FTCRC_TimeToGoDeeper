package org.firstinspires.ftc.teamcode.func.classes.superclasses;

public class PD {
    public double kp;
    public double kd;
    public double ErLast;
    public double P;
    public double D;
    public void init(double kp, double kd) {
        ErLast = 0;
        this.kp = kp; this.kd = kd;
    }
    public double tick(double Er) {
        P = Er * kp;

        D = kd * (Er - ErLast);
        ErLast = Er;
        if ( D > P ) { D = P; }

        return  P + D;
    }
}