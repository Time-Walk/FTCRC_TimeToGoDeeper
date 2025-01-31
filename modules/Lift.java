package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.modules.superclasses.Module;
@Config
public class Lift extends Module {
    public DcMotor L;
    public static double ticke = 1000;
    public static double kl = 0.001;
    public static double ap = 0;
    @Override
    public void init() {
    L = hwmp.get(DcMotor.class, "L");
    L.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void tele() {
        //L.setPower((gamepad2.left_stick_y*gamepad2.left_stick_y)*Math.signum(gamepad2.left_stick_y));
        //L.setPower((gamepad2.right_stick_y*gamepad2.right_stick_y)*Math.signum(gamepad2.right_stick_y)*0.5);
        //L.setPower(gamepad1.right_trigger - (gamepad1.left_trigger*0.24556));
        if (gamepad2.left_stick_y>0) {L.setPower(gamepad2.left_stick_y);}
        if (gamepad2.left_stick_y<0) {L.setPower(gamepad2.left_stick_y*0.2);
        }

    }
    public void regulate(){
        if (ticke > L.getCurrentPosition()) {
            ap = L.getCurrentPosition()/ticke;
            ap = ap*kl;
        } else if (L.getCurrentPosition()>ticke) {
            ap = L.getCurrentPosition()/ticke;
            ap = ap*kl;
        }
        telemetry.addData("ap", ap);
        telemetry.addData("ticke",Math.abs(ticke));
        //telemetry.addData("pos",Math.abs(L.getCurrentPosition()));
        if (gamepad1.left_bumper) {
        //    L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //    L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            telemetry.addData("reset encoders", "!");
            telemetry.update();
        }
        L.setPower((-gamepad2.left_stick_y+(ap*0.6)*0.65));
        telemetry.update();

    }
}
