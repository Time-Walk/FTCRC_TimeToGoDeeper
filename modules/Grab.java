package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.modules.superclasses.Module;
@Config
public class Grab extends Module {
    public CRServo ar, al;
    public static double power = 1.0;
    @Override
    public void init() {
        ar = hwmp.get(CRServo.class, "ar");
        al = hwmp.get(CRServo.class, "al");
    }
    public void grab() {
        if (gamepad2.y) {
            ar.setPower(power);
            al.setPower(-power);

        } else if (gamepad2.x) {
            ar.setPower(-power);
            al.setPower(power);
        }

    }
}
