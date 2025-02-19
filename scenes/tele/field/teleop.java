package org.firstinspires.ftc.teamcode.scenes.tele.field;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Lift;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotConstruct;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "ТЕЛЕОП_ВКЛЮЧИТЬ_БЕСЛПАТНО")
public class teleop extends LinearOpMode {
    @Override
    public void runOpMode() throws  InterruptedException {
        RobotConstruct R = new RobotConstruct();
        R.init(telemetry,this,hardwareMap);
        R.gamepad_init(gamepad1,gamepad2);
        waitForStart();
        //wheelbase init encoder????
        while(!isStopRequested()) {

            //R.wb.tele();
            R.wb.tele();
            R.gb.tele();
            R.ac.tele();
            R.lift.tele();
            //if (gamepad2.a) {
           //     R.lift.regulate();
            //}


            telemetry.addData("LF", R.wb.LF.getPower());
            telemetry.addData("LB", R.wb.LB.getPower());
            telemetry.addData("RF", R.wb.RF.getPower());
            telemetry.addData("RB", R.wb.RB.getPower());
            //telemetry.addData("tiktok", R.lift.L.getCurrentPosition());
            telemetry.addData("lp", R.lift.L.getPower());
            //telemetry.addData("powah", Lift.ap);
           // telemetry.addData("gl", R.gb.gl.getPower());
           // telemetry.addData("gr", R.gb.gr.getPower());
            telemetry.update();
        }
    }
}
