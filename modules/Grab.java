package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.superclasses.Module;
@Config
public class Grab extends Module {
    public Servo gzv;
    public CRServo zov_l, zov_r;
    public static double position_open = 0.5;
    public static double position_close = 1;
    @Override
    public void initModule() {
        gzv = P.hwmp.get(Servo.class, "gzv");
        zov_l = P.hwmp.get(CRServo.class,"zov_l");
        zov_r = P.hwmp.get(CRServo.class, "zov_r");
    }
    public void tele() {
        if (P.gamepad2.x) {
            gzv.setPosition(position_open);

        } else if (P.gamepad2.a) {
            gzv.setPosition(position_close);
        }
        if (P.gamepad2.left_trigger > 0.5) {
            zov_l.setPower(0.25);
            zov_r.setPower(-0.25);
        } else if (P.gamepad2.right_trigger > 0.5) {
            zov_l.setPower(-0.25);
            zov_r.setPower(0.25);
        } else {
            zov_l.setPower(0);
            zov_r.setPower(0);
        }
        if (P.gamepad2.left_bumper) {
            zov_l.setPower(-1);
            zov_r.setPower(-1);
        } else if (P.gamepad2.right_bumper) {
            zov_l.setPower(1);
            zov_r.setPower(1);
        }

    }
}
