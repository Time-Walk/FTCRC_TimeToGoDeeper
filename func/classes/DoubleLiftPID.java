package org.firstinspires.ftc.teamcode.func.classes;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.func.classes.superclasses.PID;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

@Config
public class DoubleLiftPID { // Синхронный PID-регулятор для двух степеней лифт
    public RobotPortal R;
    public static double lc_kp = .3;
    public static double lc_ki = .03;
    public static double lc_kd = .5;
    public static int lc_possibleDifference = 10;
    public PID pid_lc;
    public PID pid_ll;
    public static double ll_kp = .3;
    public static double ll_ki = .03;
    public static double ll_kd = .5;
    public static int ll_possibleDifference = 10;
    int lc_target = 0;
    int ll_target = 0;
    public DoubleLiftPID(RobotPortal R) {
        this.R = R;
        pid_lc = new PID(lc_kp, lc_ki, lc_kd);
        pid_ll = new PID(ll_kp, ll_ki, ll_kd);
    }
    public void setLcPosition(int ticks) {
        lc_target = ticks;
    }
    public void setLlPosition(int ticks) {
        ll_target = ticks;
    }
    public double[] tick() {
        double Er_lc = lc_target - R.lift.L.getCurrentPosition();
        double Er_ll = ll_target - R.lift.L_L.getCurrentPosition();
        return new double[] {pid_lc.tick(Er_lc), pid_ll.tick(Er_ll)};
    }
    public void getToPositionByLc() {
        while ( Math.abs( lc_target - R.lift.L.getCurrentPosition() ) > lc_possibleDifference ) {
            double[] pw = tick();
            R.lift.L.setPower(pw[0]);
            R.lift.L_L.setPower(pw[1]);
            R.P.telemetry.addData("Erlc", lc_target - R.lift.L.getCurrentPosition());
            R.P.telemetry.addData("Erll", ll_target - R.lift.L_L.getCurrentPosition());
            R.P.telemetry.update();
        }
    }
    public void getToPositionByLl() {
        while ( Math.abs( ll_target - R.lift.L_L.getCurrentPosition() ) > ll_possibleDifference ) {
            double[] pw = tick();
            R.lift.L.setPower(pw[0]);
            R.lift.L_L.setPower(pw[1]);
            R.P.telemetry.addData("Erlc", lc_target - R.lift.L.getCurrentPosition());
            R.P.telemetry.addData("Erll", ll_target - R.lift.L_L.getCurrentPosition());
            R.P.telemetry.update();
        }
    }
}
