package org.firstinspires.ftc.teamcode.func.classes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.func.classes.superclasses.PD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotConstruct;

@Config
public class LiftPD {
    Telemetry telemetry;
    LinearOpMode L;
    HardwareMap hwmp;
    RobotConstruct R;
    PD pdm;
    public static double kp = 0.005;
    public static double kd = 0;
    public static double tickl = 0;
    public static double pwfl = 0;
    public static double counterpower = 0;
    public void init(RobotConstruct R, LinearOpMode L) {
        this.R = R;
        this.L = L;
        pdm = new PD();
        pdm.init(kp, kd);
    }
    public void go(double tick) {
        tickl = tick;
        R.lift.initencoder();
        while ( (Math.abs(tickl)-Math.abs(R.lift.L.getCurrentPosition()) > 10*Math.signum(tickl)) && L.opModeIsActive()) {
            pwfl = pdm.tick(tickl - R.lift.L.getCurrentPosition());
            R.lift.L.setPower(pwfl);
        }
        R.lift.L.setPower(counterpower);
    }
}
