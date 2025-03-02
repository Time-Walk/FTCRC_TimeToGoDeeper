package org.firstinspires.ftc.teamcode.func.classes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.func.classes.superclasses.PD;
import org.firstinspires.ftc.teamcode.modules.IMU;
import org.firstinspires.ftc.teamcode.modules.Wheelbase;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

@Config
public class DrivePD_OLD {
    Telemetry telemetry;
    LinearOpMode L;
    HardwareMap hwmp;
    RobotPortal R;
    PD pdX, pdY, pdRot;
    public static double kpXY = .005;
    public static double kdXY = .0005;
    public static double kpRot = -.0075;
    public static double kdRot = -.0384345;
    public static double krAccel = .079876543;
    public static double kpAccel = .00045;
    public static double minTargetErDist = 5;
    public static double minErSpeed = .5;
    public static double maxRotationDelta = .5;
    public double smInTick = .0785375;
    public void init(RobotPortal R, LinearOpMode L) {
        this.R = R; this.L = L;
        pdX = new PD(kpXY, kdXY);
        pdY = new PD(kpXY, kdXY);
        pdRot = new PD(kpRot, kdRot);
        telemetry = FtcDashboard.getInstance().getTelemetry();
        telemetry.addData("Ux", 0);
        telemetry.addData("Uy", 0);
        telemetry.addData("Urot", 0);
        telemetry.addData("lf", R.wb.LF.getPower());
        telemetry.addData("lb", R.wb.LB.getPower());
        telemetry.addData("rf", R.wb.RF.getPower());
        telemetry.addData("rb", R.wb.RB.getPower());
        telemetry.update();
    }
    public double toTicks(double cm) { return cm/smInTick; }
    public void go(double targetX, double targetY) {
        R.imuv2.reset();
        R.wb.initEncoderAuto();
        boolean condSumm = false;
        boolean condX = false;
        boolean condY = false;
        while ( !condSumm ) {

            double Ux = 0;
            double Uy = 0;

            if ( !condX ) {
                double XEr = targetX + R.wb.RF.getCurrentPosition();
                if ( Math.abs(XEr) > Math.abs(targetX) / 2) {
                    telemetry.addData("XEr",XEr);
                    telemetry.update();
                    double Rele = krAccel * Math.signum(XEr);
                    double P = kpAccel * Math.abs(targetX - XEr) * Math.signum(targetX);
                    Ux = Rele + P;
                }
                else {
                    Ux = pdX.tick(XEr);
                }
                condX = Math.abs( XEr ) < minTargetErDist && Math.abs(XEr - pdX.ErLast) < minErSpeed;
            }

            if ( !condY ) {
                double YEr = targetY - R.wb.LF.getCurrentPosition();
                if ( Math.abs(YEr) > Math.abs(targetY) / 2) {
                    telemetry.addData("YEr",YEr);
                    telemetry.update();
                    double Rele = krAccel * Math.signum(YEr);
                    double P = kpAccel * Math.abs(targetY - YEr) * Math.signum(targetY);
                    Uy = Rele + P;
                }
                else {
                    Uy = pdY.tick(YEr);
                }
                condY = Math.abs( YEr ) < minTargetErDist && Math.abs(YEr - pdY.ErLast) < minErSpeed;
            }

            double ErRot = -R.imu.getAngle();
            double URot = pdRot.tick(ErRot);

            condSumm = condX && condY && ( Math.abs(ErRot) < maxRotationDelta );

            if ( Ux > .35 ) { Ux = .35; }
            else if ( Ux < -.35) { Ux = -.35; }
            if ( Uy > .35 ) { Uy = .35; }
            else if ( Uy < -.35 ) { Uy = -.35; }
            if ( URot > .35 ) { URot = .35; }
            else if ( URot < -.35 ) { URot = -.35; }

            R.wb.setMtPower(Ux + URot, Uy+URot, -Uy+URot, -Ux+URot);

            telemetry.addData("Ux", Ux);
            telemetry.addData("Uy", Uy);
            telemetry.addData("Urot", URot);
            telemetry.addData("lf", R.wb.LF.getPower());
            telemetry.addData("lb", R.wb.LB.getPower());
            telemetry.addData("rf", R.wb.RF.getPower());
            telemetry.addData("rb", R.wb.RB.getPower());
            telemetry.addData("RB", -R.wb.RB.getCurrentPosition());
            telemetry.addData("LB", R.wb.LB.getCurrentPosition());
            telemetry.addData("ErX", targetX + R.wb.RB.getCurrentPosition());
            telemetry.addData("ErY", targetY - R.wb.LB.getCurrentPosition());
            telemetry.addData("targetX", targetX);
            telemetry.addData("condX", condX);
            telemetry.addData("condY", condY);
            telemetry.addData("condSumm", condSumm);
            telemetry.addData("forCondXEr", Math.abs(  (targetX+R.wb.RB.getCurrentPosition()) ));
            telemetry.addData("forCondXSpeed", Math.abs((targetX+R.wb.RB.getCurrentPosition()) - pdX.ErLast));
            telemetry.update();
        }
        R.wb.setMtZero();
        R.wb.delay(500);
    }

}