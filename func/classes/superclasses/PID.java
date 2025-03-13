package org.firstinspires.ftc.teamcode.func.classes.superclasses;

public class PID { // Класс для быстрого развертывания PID-регулятора
    public double kp;
    public double ki;
    public double kd;
    public double ErLast;
    public double Ir;
    public double P;
    public double D;
    public double I;
    public PID(double kp, double ki, double kd) { // Конструктор принимает коэффициенты
        ErLast = 0;
        Ir = 0;
        this.kp = kp; this.kd = kd; this.ki = ki;
    }
    public double tick(double Er) { // Основная функция, принимает ошибку и возвращает управляющее воздействие
        P = Er * kp;

        D = kd * (Er - ErLast);
        ErLast = Er;
        //if ( D > P ) { D = P; }

        Ir += Er;
        I = ki * Ir;

        return  P + I + D;
    }
}
