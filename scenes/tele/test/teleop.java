package org.firstinspires.ftc.teamcode.scenes.tele.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.superclasses.RobotConstruct;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Telek221")

public class teleop extends LinearOpMode {

    @Override
    public void runOpMode() throws  InterruptedException {
        RobotConstruct R = new RobotConstruct();
        R.init(telemetry,this,hardwareMap);
        R.gamepad_init(gamepad1,gamepad2);
        waitForStart();
        //wheelbase init encoder????
        while(!isStopRequested()) {

            R.wb.tele();
            R.gb.grab();
            R.ac.tele();
            R.lift.tele();


            telemetry.addData("LF", R.wb.LF.getPower());
            telemetry.addData("LB", R.wb.LB.getPower());
            telemetry.addData("RF", R.wb.RF.getPower());
            telemetry.addData("RB", R.wb.RB.getPower());
            telemetry.update();
        }
    }
}
