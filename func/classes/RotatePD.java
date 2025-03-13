package org.firstinspires.ftc.teamcode.func.classes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.func.classes.superclasses.PD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

@Config
public class RotatePD { // PD-регулятор поворота по гироскопу
    PD pd;
    public RobotPortal R;
    Telemetry telemetry;
    public static double kp = -0.0025;
    public static double kd = -0.00005;
    public static double kr = -0.075;
    public static double ErTarget = .1;
    public static double ErSpeedTarget = .5;
    public void init(RobotPortal R, LinearOpMode L) {
        pd = new PD(kp, kd);

        this.R = R;
        this.L = L;

        telemetry = FtcDashboard.getInstance().getTelemetry();
    }
    LinearOpMode L;
    public void rotate(double degrees) {
        R.imuv2.reset();
        while ( (Math.abs(degrees - R.imuv2.getAngle()) > ErTarget || Math.abs((degrees - R.imuv2.getAngle()) - pd.ErLast) > ErSpeedTarget) && L.opModeIsActive() ) {
            double U = pd.tick(degrees - R.imuv2.getAngle());
            double Rele = kr * Math.signum(degrees-R.imuv2.getAngle());
            R.wb.setMtPower(U+Rele, U+Rele, U+Rele, U+Rele);
            telemetry.addData("U", U);
            telemetry.addData("P", pd.P);
            telemetry.addData("D", pd.D);
            telemetry.addData("angle", R.imuv2.getAngle());
            telemetry.addData("Er", degrees-R.imuv2.getAngle());
            telemetry.update();
        }
        telemetry.addData("LB", R.wb.LB.getCurrentPosition());
        telemetry.addData("RB", R.wb.RB.getCurrentPosition());
        telemetry.update();
        R.wb.setMtZero();
        R.wb.delay(500);
    }
}