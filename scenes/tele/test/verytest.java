package org.firstinspires.ftc.teamcode.scenes.tele.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpPack;

@Config
@TeleOp
public class verytest extends TeleOpPack {
    public static double ss = 0;
    @Override
    public void doSetup() {
        //telemetry = FtcDashboard.getInstance().getTelemetry();
        //R.P.telemetry = telemetry;
        R.lift.L_L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        R.lift.L_L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    @Override
    public void doActions() {
        R.cam.cameraServo.setPosition(ss);
        telemetry.addData("L", R.lift.L.getCurrentPosition());
        telemetry.addData("L_L", R.lift.L_L.getCurrentPosition());
        telemetry.update();
    }
}
