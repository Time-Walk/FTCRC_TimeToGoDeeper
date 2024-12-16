package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.modules.superclasses.Module;
@Config
public class Lift extends Module {
    public DcMotor L;
    public static double ticke = -100;
    public static double kl = 0.001;
    public static double ap = 0;
    @Override
    public void init() {
    L = hwmp.get(DcMotor.class, "L");
    L.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void tele() {
        L.setPower((gamepad2.left_stick_y*gamepad2.left_stick_y)*Math.signum(gamepad2.left_stick_y));
        L.setPower((gamepad2.right_stick_y*gamepad2.right_stick_y)*Math.signum(gamepad2.right_stick_y)*0.5);
    }
    public void regulate(){
        while (gamepad2.left_stick_y!=0) {
            ap = Math.abs(ticke) - Math.abs(L.getCurrentPosition()) * kl;
            if (Math.abs(ap)>1) {
                L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            L.setPower(gamepad2.left_stick_y + ap);
        }

    }
}
