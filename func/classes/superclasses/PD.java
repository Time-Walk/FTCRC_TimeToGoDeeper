package org.firstinspires.ftc.teamcode.func.classes.superclasses;

public class PD { // Класс для быстрого развертывания PD-регуляторов
    public double kp;
    public double kd;
    public double ErLast;
    public double P;
    public double D;
    public PD(double kp, double kd) { // Конструктор принимает коэффициенты
        ErLast = 0;
        this.kp = kp; this.kd = kd;
    }
    public double tick(double Er) { // Основная функция, принимает ошибку, возвращает управляющее воздействие
        P = Er * kp;

        D = kd * (Er - ErLast);
        ErLast = Er;
        //if ( D > P ) { D = P; }

        return  P + D;
    }
}