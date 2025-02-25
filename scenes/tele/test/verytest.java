package org.firstinspires.ftc.teamcode.scenes.tele.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpPack;

@TeleOp
public class verytest extends TeleOpPack {
    @Override
    public void doSetup() {
        telemetry = FtcDashboard.getInstance().getTelemetry();
        R.P.telemetry = telemetry;
        //R.lift.initencoder();
        R.wb.RB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        R.wb.RB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    @Override
    public void doActions() {
        telemetry.addData("L", R.lift.L.getCurrentPosition());
        telemetry.addData("RB", R.wb.RB.getCurrentPosition());
        telemetry.update();
    }
}
