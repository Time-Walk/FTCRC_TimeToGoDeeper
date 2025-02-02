package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.superclasses.Module;
@Config
public class Grab extends Module {
    public Servo gzv;
    public static double position_open = 0.2;
    public static double position_close = 0.4;
    @Override
    public void init() {
        gzv = hwmp.get(Servo.class, "gzv");
    }
    public void tele() {
        if (gamepad2.x) {
            gzv.setPosition(position_open);

        } else if (gamepad2.a) {
            gzv.setPosition(position_close);
        } else if (gamepad2.dpad_up) {
            gzv.setPosition(0);
        }

    }
}
