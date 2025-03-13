package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.modules.superclasses.Module;

@Config
public class Wheelbase extends Module {
    public static double speedChangeCoef = .3333;
    boolean flag = false;
    double curSpeedCoef = 1;
    public DcMotor LF, RF, LB, RB;
    public void initModule() {
        LF = P.hwmp.get(DcMotor.class, "LF");
        RF = P.hwmp.get(DcMotor.class, "RF");
        LB = P.hwmp.get(DcMotor.class, "LB");
        RB = P.hwmp.get(DcMotor.class, "RB");

        LF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void initEncoderTele(){
      //  LB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      //  RB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void initEncoderAuto() {
        LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    //    LB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    //    LB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    //    RB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    //    RB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    public void initEncoderLF() {
        LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setMtPower(double lf, double lb, double rf, double rb) {
        LF.setPower(lf);
        LB.setPower(lb);
        RF.setPower(rf);
        RB.setPower(rb);
    }

    public void setMtZero() { setMtPower(0, 0, 0, 0); }

    public void tele() {
        if ( P.gamepad1.right_bumper ) {
            if ( !flag ) {
                flag = true;
                if ( curSpeedCoef > .99 ) {
                    curSpeedCoef = speedChangeCoef;
                }
                else {
                    curSpeedCoef = 1;
                }
            }
        }
        else {
            if ( flag ) { flag = false; }
        }
        double lf = -P.gamepad1.left_stick_y + P.gamepad1.left_stick_x + (P.gamepad1.right_stick_x * 0.6);
        lf *= curSpeedCoef;
        double lb = -P.gamepad1.left_stick_y - P.gamepad1.left_stick_x + (P.gamepad1.right_stick_x * 0.6);
        lb *= curSpeedCoef;
        double rf = P.gamepad1.left_stick_y + P.gamepad1.left_stick_x + (P.gamepad1.right_stick_x * 0.6);
        rf *= curSpeedCoef;
        double rb = P.gamepad1.left_stick_y - P.gamepad1.left_stick_x + (P.gamepad1.right_stick_x * 0.6);
        rb *= curSpeedCoef;
        setMtPower(lf, lb, rf, rb);
    }
    public void zov() {
        double z = 0;
        double v =0;
        if (P.gamepad1.dpad_up) {z=-1;} else if (P.gamepad1.dpad_down) {z=1;}
        if (P.gamepad1.dpad_left) {v=1;} else if (P.gamepad1.dpad_right) {v=-1;}
        double lf = -P.gamepad1.left_stick_y + P.gamepad1.right_stick_x - z - v;
        double lb = -P.gamepad1.left_stick_y + P.gamepad1.right_stick_x - z + v;
        double rf =  P.gamepad1.left_stick_y + P.gamepad1.right_stick_x + z - v;
        double rb =  P.gamepad1.left_stick_y + P.gamepad1.right_stick_x + z + v;
        setMtPower(lf,lb,rf,rb);

    }

    public void timer(double lf, double lb, double rf, double rb, int millis) {
        setMtPower(lf, lb, rf, rb);
        delay(millis);
        setMtZero();
    }
}
