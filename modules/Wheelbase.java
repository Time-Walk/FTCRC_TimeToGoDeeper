package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.modules.superclasses.Module;

public class Wheelbase extends Module {
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
        double lf = -P.gamepad1.left_stick_y + P.gamepad1.left_stick_x + (P.gamepad1.right_stick_x * 0.6);
        double lb = -P.gamepad1.left_stick_y - P.gamepad1.left_stick_x + (P.gamepad1.right_stick_x * 0.6);
        double rf = P.gamepad1.left_stick_y + P.gamepad1.left_stick_x + (P.gamepad1.right_stick_x * 0.6);
        double rb = P.gamepad1.left_stick_y - P.gamepad1.left_stick_x + (P.gamepad1.right_stick_x * 0.6);
        setMtPower(lf, lb, rf, rb);
    }
    public void zov() {
        double lf = -P.gamepad1.left_stick_y + P.gamepad1.right_stick_x;
        double lb = -P.gamepad1.left_stick_y + P.gamepad1.right_stick_x;
        double rf =  P.gamepad1.left_stick_y + P.gamepad1.right_stick_x;
        double rb =  P.gamepad1.left_stick_y + P.gamepad1.right_stick_x;
        setMtPower(lf,lb,rf,rb);

    }

    public void timer(double lf, double lb, double rf, double rb, int millis) {
        setMtPower(lf, lb, rf, rb);
        delay(millis);
        setMtZero();
    }
}
