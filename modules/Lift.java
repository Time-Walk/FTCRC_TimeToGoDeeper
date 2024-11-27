package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.modules.superclasses.Module;

public class Lift extends Module {
    public DcMotor L;
    public static double ticke = 0;
    public static double kl = 0.001;
    @Override
    public void init() {
   // L = hwmp.get(DcMotor.class, "L");
   // L.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void tele() {
     //   L.setPower((gamepad2.left_stick_y*gamepad2.left_stick_y)*Math.signum(gamepad2.left_stick_y));
     //   L.setPower((gamepad2.right_stick_y*gamepad2.right_stick_y)*Math.signum(gamepad2.right_stick_y)*0.5);
    }
    public void regulate(){
     //   L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     //   L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double ap = 0;
        if (L.getCurrentPosition()<ticke) {
            ap = ticke - L.getCurrentPosition() * kl;
        } else if (ticke<L.getCurrentPosition()) {
            ap = L.getCurrentPosition() - ticke * kl;
        }
        L.setPower(gamepad2.left_stick_y+ap);


    }
}
