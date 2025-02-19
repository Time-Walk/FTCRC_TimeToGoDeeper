package org.firstinspires.ftc.teamcode.scenes.tele.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.IMUv2;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotConstruct;
@Disabled
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Telekop321")

public class testteleop123 extends LinearOpMode {

    @Override
    public void runOpMode() throws  InterruptedException {
        RobotConstruct R = new RobotConstruct();
        R.init(telemetry,this,hardwareMap);
        R.gamepad_init(gamepad1,gamepad2);
        telemetry = FtcDashboard.getInstance().getTelemetry();
        R.wb.initEncoderAuto();
        waitForStart();
        //wheelbase init encoder????
        while(!isStopRequested()) {
            telemetry.addData("rfpos", R.wb.RF.getCurrentPosition());
            telemetry.addData("lfpos", R.wb.LF.getCurrentPosition());
            telemetry.update();
        }
    }
}
