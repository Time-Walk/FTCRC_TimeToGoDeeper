package org.firstinspires.ftc.teamcode.func.classes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.func.classes.superclasses.PD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotConstruct;

@Config
public class DrivePD_1ENC_M {
    Telemetry telemetry;
    LinearOpMode L;
    HardwareMap hwmp;
    RobotConstruct R;
    PD pdm;
    public static double kp = 0.0025;
    public static double kd = 0.00025;
    public static double speed = 0.01;
    public static double tickp = 0;
    public static double pwf = 0;
    public void init(RobotConstruct R, LinearOpMode L) {
        this.R = R;
        this.L = L;
        pdm = new PD();
        pdm.init(kp, kd);
    }
    public void forward(double tick) {
        tickp = tick;
        R.wb.initEncoderLF();
        while ( (Math.abs(tickp)-Math.abs(R.wb.LF.getCurrentPosition()) > 10*Math.signum(tickp)) && L.opModeIsActive()) {
            pwf = speed*pdm.tick(tickp - R.wb.LF.getCurrentPosition());
            R.wb.setMtPower(pwf, 0, 0, -pwf);
        }
        R.wb.setMtZero();
    }
    public void backward(double tick) {
        tickp = tick;
        R.wb.initEncoderLF();
        while ( (Math.abs(tickp)-Math.abs(R.wb.LF.getCurrentPosition()) > 10*Math.signum(tickp)) && L.opModeIsActive()) {
            pwf = speed*pdm.tick(tickp - R.wb.LF.getCurrentPosition());
            R.wb.setMtPower(-pwf, 0, 0, pwf);
        }
        R.wb.setMtZero();
    }
  /*  public void left(double tick) {
        tickp = tick;
        R.wb.initEncoderLF();
        while ( (Math.abs(tickp)-Math.abs(R.wb.LF.getCurrentPosition()) > 10*Math.signum(tickp)) && L.opModeIsActive()) {
            pwf = pdm.tick(tickp - R.wb.LF.getCurrentPosition());
            R.wb.setMtPower(-pwf, pwf, -pwf, pwf);
        }
        R.wb.setMtZero();
    }
    public void right(double tick) {
        tickp = tick;
        R.wb.initEncoderLF();
        while ( (Math.abs(tickp)-Math.abs(R.wb.LF.getCurrentPosition()) > 10*Math.signum(tickp)) && L.opModeIsActive()) {
            pwf = pdm.tick(tickp - R.wb.LF.getCurrentPosition());
            R.wb.setMtPower(pwf, -pwf, pwf, -pwf);
        }
        R.wb.setMtZero();
    } */
}
