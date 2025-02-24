package org.firstinspires.ftc.teamcode.func.classes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.func.classes.superclasses.PD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;
@Config
public class DrivePD {
    Telemetry telemetry;
    LinearOpMode L;
    HardwareMap hwmp;
    RobotPortal R;
    PD pdl, pdr;
    public static double kp = 0.005;
    public static double kd = 0.0005;
    public static double speed = 1;
    public static double tickl = 0;
    public static double tickr = 0;
    public static double pwfl = 0;
    public static double pwfr = 0;
    public void init(RobotPortal R, LinearOpMode L) {
        this.R = R;
        this.L = L;
        pdl = new PD(kp,kd);
        pdr = new PD(kp,kd);
        telemetry = FtcDashboard.getInstance().getTelemetry();
    }
    public void go(double tick) {
        tickl = tick;
        tickr = tick;
        R.wb.initEncoderAuto();
        while ( (Math.abs(tickl)-Math.abs(R.wb.LF.getCurrentPosition()) > 10*Math.signum(tickl)) && (Math.abs(tickr)-Math.abs(R.wb.RF.getCurrentPosition()) > 10*Math.signum(tickr)) && L.opModeIsActive()) {
            pwfl = speed*pdl.tick(tickl - R.wb.LF.getCurrentPosition());
            pwfr = speed*pdr.tick(tickr + R.wb.RF.getCurrentPosition());
            pwfl = pwfl*Math.abs((100+tickl)/R.wb.LF.getCurrentPosition());
            pwfr = pwfr*Math.abs((100+tickr)/R.wb.RF.getCurrentPosition());
            R.wb.setMtPower(pwfr, pwfr, -pwfl, -pwfl);
            telemetry.addData("left",tickl - R.wb.LF.getCurrentPosition());
            telemetry.addData("right",tickr + R.wb.RF.getCurrentPosition());
            telemetry.update();
        }
        R.wb.setMtZero();
    }
}
