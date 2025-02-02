package org.firstinspires.ftc.teamcode.scenes.tele.test;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.superclasses.RobotConstruct;
@Config
@TeleOp(name="шедевр: тест меканум паравозик", group="")
public class testdrive2 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RobotConstruct R = new RobotConstruct();
        R.init(telemetry, this, hardwareMap);
        R.gamepad_init(gamepad1,gamepad2);
        waitForStart();
        while(!isStopRequested()) {
            // порядок паравозика 1) паша ведущий 2) козырное место семен 3) я 4) матвей  5) замыкающий семен(альт)
            R.wb.zov();
            R.gb.tele();
            R.ac.tele();
            R.lift.tele();

        }
    }

}
//
