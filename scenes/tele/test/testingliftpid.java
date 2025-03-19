package org.firstinspires.ftc.teamcode.scenes.tele.test;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.func.classes.DoubleLiftPID;
import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpPack;

@Config
@TeleOp
public class testingliftpid extends TeleOpPack {
    public DoubleLiftPID lpid;
    public static int speed_lc = 5;
    public static int speed_ll = 5;
    @Override
    public void doSetup() {
        lpid = new DoubleLiftPID(R);
        R.lift.L_L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        R.lift.L_L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        R.lift.L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        R.lift.L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    @Override
    public void doActions() {
        if ( gamepad2.share ) {
            R.lift.L_L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            R.lift.L_L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            R.lift.L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            R.lift.L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lpid.setLlPosition(0);
            lpid.setLcPosition(0);
        }
        lpid.setLcPosition(lpid.getTargetLc()+Math.round(gamepad2.left_stick_y*speed_lc));
        lpid.setLlPosition(lpid.getTargetLl()+Math.round(gamepad2.right_stick_y*speed_ll));
        double[] pw = lpid.tick();
        R.lift.L.setPower(pw[0]);
        R.lift.L_L.setPower(pw[1]);
        telemetry.addData("target lc", lpid.getTargetLc());
        telemetry.addData("target ll", lpid.getTargetLl());
        telemetry.addData("cur lc", R.lift.L.getCurrentPosition());
        telemetry.addData("cur ll", R.lift.L_L.getCurrentPosition());
        telemetry.update();
    }
}
