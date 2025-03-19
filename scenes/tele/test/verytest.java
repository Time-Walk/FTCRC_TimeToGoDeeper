package org.firstinspires.ftc.teamcode.scenes.tele.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpPack;

@Config
@TeleOp
public class verytest extends TeleOpPack {
    public static double ss = 0;
    @Override
    public void doSetup() {
        //telemetry = FtcDashboard.getInstance().getTelemetry();
        //R.P.telemetry = telemetry;
        R.lift.L_L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        R.lift.L_L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        R.lift.L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        R.lift.L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    @Override
    public void doActions() {
        if ( gamepad2.left_stick_y != 0 ) {
            R.lift.L.setPower(gamepad2.left_stick_y);
        } else { R.lift.L.setPower(0); }
        if ( gamepad2.right_stick_y != 0 ) {
            R.lift.L_L.setPower(gamepad2.right_stick_y);
        } else { R.lift.L_L.setPower(0); }
        if ( gamepad2.dpad_up ) { R.lift.L.setPower(0); }
        if ( gamepad2.dpad_down ) { R.lift.L_L.setPower(0); }
        telemetry.addData("L", R.lift.L.getCurrentPosition());
        telemetry.addData("L_L", R.lift.L_L.getCurrentPosition());
        telemetry.addData("g2 ls y", gamepad2.left_stick_y);
        telemetry.addData("g2 rs y", gamepad2.right_stick_y);
        telemetry.update();
    }
}
