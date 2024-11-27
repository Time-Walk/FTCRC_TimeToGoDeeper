package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.modules.superclasses.Module;
@Config
public class Grab extends Module {
    public CRServo gr, gl;
    public static double power = 1.0;
    @Override
    public void init() {
    //    gr = hwmp.get(CRServo.class, "gr");
    //    gl = hwmp.get(CRServo.class, "gl");
    }
    public void tele() {
        if (gamepad2.y) {
    //        gl.setPower(-power);
    //        gr.setPower(power);

        } else if (gamepad2.x) {
    //        gl.setPower(power);
    //        gr.setPower(-power);
        } else {
    //        gl.setPower(0);
    //        gr.setPower(0);
        }

    }
}
