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
    public static double tickp = 0;
    public static double pwf = 0;
    public static double counterpower = 0;
    public void init(RobotConstruct R, LinearOpMode L) {
        this.R = R;
        this.L = L;
        pdm = new PD();
        pdm.init(kp, kd);
    }
    public void go(double tick) {
        tickp = tick;
        R.lift.initencoder();
        while ( (Math.abs(tickp)-Math.abs(R.lift.L.getCurrentPosition()) > 10*Math.signum(tickp)) && L.opModeIsActive()) {
            pwf = pdm.tick(tickp - R.lift.L.getCurrentPosition());
            R.lift.L.setPower(pwf);
        }
        R.lift.L.setPower(counterpower);
    }
}
