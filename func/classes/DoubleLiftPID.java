package org.firstinspires.ftc.teamcode.func.classes;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.func.classes.superclasses.PID;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

@Config
public class DoubleLiftPID { // Синхронный PID-регулятор для двух степеней лифт
    public RobotPortal R;
    public static double lc_kp = .001;
    public static double lc_ki = 0.00000;
    public static double lc_kd = .001;
    public static int lc_possibleDifference = 7;
    public PID pid_lc;
    public PID pid_ll;
    public static double ll_kp = .0009;
    public static double ll_ki = 0.00000;
    public static double ll_kd = .005;
    public static int ll_possibleDifference = 7;
    public static int lc_software_limit = 750;
    public static int ll_software_limit = 1500;
    int lc_target = 0;
    int ll_target = 0;
    public DoubleLiftPID(RobotPortal R) {
        this.R = R;
        pid_lc = new PID(lc_kp, lc_ki, lc_kd);
        pid_ll = new PID(ll_kp, ll_ki, ll_kd);
    }
    public void setLcPosition(int ticks) {
        if ( ticks < 0 ) { ticks = 0; }
        else if ( ticks > lc_software_limit ) { ticks = lc_software_limit; }
        lc_target = ticks;
        pid_lc.Ir = 0;
    }
    public void setLlPosition(int ticks) {
        if ( ticks < 0 ) { ticks = 0; }
        else if ( ticks > ll_software_limit ) { ticks = ll_software_limit; }
        ll_target = ticks;
        pid_ll.Ir = 0;
    }
    public int getTargetLc() {
        return lc_target;
    }
    public int getTargetLl() {
        return ll_target;
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
