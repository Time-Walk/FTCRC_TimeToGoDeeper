package org.firstinspires.ftc.teamcode.scenes.tele.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.func.classes.LiftPID;
import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpPack;

@Config
@TeleOp
public class liftsetup extends TeleOpPack {
    LiftPID lpd;
    public static int pos1 = 200;
    public static int pos2 = 500;
    @Override
    public void doSetup() {
        telemetry = FtcDashboard.getInstance().getTelemetry();
        R.P.telemetry = telemetry;
        lpd = new LiftPID(R);
        R.lift.L_L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        R.lift.L_L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("Er", 0);
        telemetry.update();
    }
    @Override
    public void doActions() {
        lpd.setPosition(pos1);
        lpd.getToPosition();
        telemetry.addData("Got Pos", pos1);
        telemetry.update();
        long timeout = System.currentTimeMillis();
        while ( System.currentTimeMillis() - timeout < 6000 && opModeIsActive() ) {
            R.lift.L_L.setPower(lpd.tick());
            telemetry.addData("Er", lpd.getTarget()-R.lift.L_L.getCurrentPosition());
            telemetry.addData("pos", R.lift.L_L.getCurrentPosition());
            telemetry.update();
        }
        lpd.setPosition(pos2);
        lpd.getToPosition();
        telemetry.addData("Got Pos", pos2);
        telemetry.update();
        timeout = System.currentTimeMillis();
        while ( System.currentTimeMillis() - timeout < 6000 && opModeIsActive() ) {
            R.lift.L_L.setPower(lpd.tick());
            telemetry.addData("Er", lpd.getTarget()-R.lift.L_L.getCurrentPosition());
            telemetry.addData("pos", R.lift.L_L.getCurrentPosition());
            telemetry.update();
        }
        R.lift.L_L.setPower(0);
    }
}
