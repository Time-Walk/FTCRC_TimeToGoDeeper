package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.modules.superclasses.Module;

public class Lift extends Module {
    public DcMotor L;
    @Override
    public void init() {
    //L = hwmp.get(DcMotor.class, "L");
    //L.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void tele() {
        //L.setPower(gamepad2.left_stick_y);
    }
}
