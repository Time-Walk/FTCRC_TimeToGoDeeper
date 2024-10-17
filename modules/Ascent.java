package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.superclasses.Module;
@Config
public class Ascent extends Module {
    public DcMotor AR, AL;
    public Servo ar, al;
    public static double power = 1.0;
    public static double openL = 0;
    public static double openR = 0;
    @Override
    public void init() {
        AL = hwmp.get(DcMotor.class, "AL");
        AR = hwmp.get(DcMotor.class, "AR");
        AL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        AR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        al = hwmp.get(Servo.class, "al");
        ar = hwmp.get(Servo.class, "ar");
    }
    public void on() {
        AL.setPower(power);
        AR.setPower(-power);
    }
    public void off() {
        AL.setPower(0);
        AR.setPower(0);
    }
    public void tele() {
        if (gamepad1.dpad_up) {
            al.setPosition(openL);
            ar.setPosition(openR);
        } else if (gamepad1.dpad_down) {
            on();
        } else {
            off();
        }
    }
}
