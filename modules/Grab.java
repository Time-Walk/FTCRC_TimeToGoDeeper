package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.superclasses.Module;
@Config
public class Grab extends Module {
    public Servo gzv;
    public CRServo zov_l, zov_r;
    public static double position_open = 0.4;
    public static double position_close = 0.675;
    @Override
    public void init() {
        gzv = hwmp.get(Servo.class, "gzv");
        zov_l = hwmp.get(CRServo.class,"zov_l");
        zov_r = hwmp.get(CRServo.class, "zov_r");
    }
    public void tele() {
        if (gamepad2.x) {
            gzv.setPosition(position_open);

        } else if (gamepad2.a) {
            gzv.setPosition(position_close);
        } else if (gamepad2.dpad_up) {
            gzv.setPosition(0);
        }
        if (gamepad2.left_trigger > 0.5) {
            zov_l.setPower(0.25);
            zov_r.setPower(-0.25);
        } else if (gamepad2.right_trigger > 0.5) {
            zov_l.setPower(-0.25);
            zov_r.setPower(0.25);
        } else {
            zov_l.setPower(0);
            zov_r.setPower(0);
        }
        if (gamepad2.left_bumper) {
            zov_l.setPower(0.25);
            zov_r.setPower(0.25);
        } else if (gamepad2.right_bumper) {
            zov_l.setPower(-0.25);
            zov_r.setPower(-0.25);
        }

    }
}
