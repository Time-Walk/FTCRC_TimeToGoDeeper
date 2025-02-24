package org.firstinspires.ftc.teamcode.scenes.tele.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;
import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpPack;

@Disabled
@TeleOp(name = "Telekop321")

public class testteleop123 extends TeleOpPack {
    @Override
    public void doSetup() {
        telemetry = FtcDashboard.getInstance().getTelemetry();
        R.wb.initEncoderAuto();
    }
    @Override
    public void doActions() {
        telemetry.addData("rfpos", R.wb.RF.getCurrentPosition());
        telemetry.addData("lfpos", R.wb.LF.getCurrentPosition());
        telemetry.update();
    }
}
