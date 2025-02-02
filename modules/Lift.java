package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.modules.superclasses.Module;
@Config
public class Lift extends Module {
    public DcMotor L;
    @Override
    public void init() {
        L = hwmp.get(DcMotor.class, "L");
        L.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void tele() {
        //L.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //L.setPower((gamepad2.left_stick_y*gamepad2.left_stick_y)*Math.signum(gamepad2.left_stick_y));
        //L.setPower((gamepad2.right_stick_y*gamepad2.right_stick_y)*Math.signum(gamepad2.right_stick_y)*0.5);
        //L.setPower(gamepad1.right_trigger - (gamepad1.left_trigger*0.24556));
        L.setPower(-gamepad2.left_stick_y);
    }
    public void initencoder() {
        L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}

