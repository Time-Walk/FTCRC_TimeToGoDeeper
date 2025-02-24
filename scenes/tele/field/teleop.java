package org.firstinspires.ftc.teamcode.scenes.tele.field;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;
import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpPack;

@TeleOp(name = "ТЕЛЕОП_ВКЛЮЧИТЬ_БЕСЛПАТНО")
public class teleop extends TeleOpPack {
    @Override
    public void doActions() {
        R.wb.tele();
        R.gb.tele();
        R.ac.tele();
        R.lift.tele();
    }
}
