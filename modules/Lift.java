package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.modules.superclasses.Module;
@Config
public class Lift extends Module {
    public DcMotor L, L_L;
    @Override
    public void initModule() {
        L = P.hwmp.get(DcMotor.class, "LC");
        L_L = P.hwmp.get(DcMotor.class, "LL");
        L.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void tele() {
        //L.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //L.setPower((gamepad2.left_stick_y*gamepad2.left_stick_y)*Math.signum(gamepad2.left_stick_y));
        //L.setPower((gamepad2.right_stick_y*gamepad2.right_stick_y)*Math.signum(gamepad2.right_stick_y)*0.5);
        //L.setPower(gamepad1.right_trigger - (gamepad1.left_trigger*0.24556));
        L.setPower(-P.gamepad2.left_stick_y);
        L_L.setPower(-P.gamepad2.right_stick_y*0.34);
    }
    public void initencoder() {
        L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void execute() {
        L.setPower(-1);
        delay(700);
        L.setPower(.2);
        delay(400);
        L.setPower(0);
    }

}

