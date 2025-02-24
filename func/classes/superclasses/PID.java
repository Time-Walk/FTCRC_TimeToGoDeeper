package org.firstinspires.ftc.teamcode.func.classes.superclasses;

public class PID {
    public double kp;
    public double ki;
    public double kd;
    public double ErLast;
    public double Ir;
    public double P;
    public double D;
    public double I;
    public PID(double kp, double ki, double kd) {
        ErLast = 0;
        Ir = 0;
        this.kp = kp; this.kd = kd; this.ki = ki;
    }
    public double tick(double Er) {
        P = Er * kp;

        D = kd * (Er - ErLast);
        ErLast = Er;
        //if ( D > P ) { D = P; }

        Ir += Er;
        I = ki * Ir;

        return  P + I + D;
    }
}
