package org.firstinspires.ftc.teamcode.scenes.tele.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpPack;

@TeleOp(name="ахаха семен")
public class semen extends TeleOpPack {
    public void doActions() {
        telemetry.addData("semen ry", gamepad1.right_stick_y);
        telemetry.addData("semen rx", gamepad1.right_stick_x);
        telemetry.addData("semen ly", gamepad1.left_stick_y);
        telemetry.addData("semen lx", gamepad1.left_stick_x);
        telemetry.update();
    }
}
