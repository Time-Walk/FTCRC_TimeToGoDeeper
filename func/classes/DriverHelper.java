package org.firstinspires.ftc.teamcode.func.classes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.modules.Lift;
import org.firstinspires.ftc.teamcode.modules.Wheelbase;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;
@Config
public class DriverHelper {
    RobotPortal R;
    DHWheelBase dhwb;
    DHLiftClassic dhlift;
    LiftPID lpid;
    VerySync verySync;
    int curTicks = 0;
    public static double liftSpeed = 10;
    public static int liftLowerPoint = 100;
    public static int liftMiddlePoint = 400;
    public static int liftHighPoint = 700;
    public static double speedChangeCoef = .333;
    double curSpeedCoef = 1;
    boolean flag = false;
    public static long clLiftState1Timer = 375;
    public static double clLiftState1Pw = .65;
    public static long clLiftState2Timer = 450;
    public static double clLiftState2Pw = .3;
    public static long clLiftState_1Timer = 500;
    public static double clLiftState_1Pw = -.4;
    public static long clLiftState_2Timer = 500;
    public static double clLiftState_2Pw = .16;
    public static double clLiftHoldPw = .23;
    public static double rotLiftSpeed = .5;
    public static double forwLiftSpeed = -.5;
    public DriverHelper(RobotPortal R) {
        this.R = R;
        dhwb = new DHWheelBase();
        dhwb.init(R.P);
        R.wb = dhwb;
        dhlift = new DHLiftClassic();
        dhlift.init(R.P);
        R.lift = dhlift;
        //lpid = new LiftPID(R);
        verySync = new VerySync(R);
    }

    class DHWheelBase extends Wheelbase {
        @Override
        public void tele() {
            verySync.tick();
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
            setMtPower(lf+verySync.pwFL, lb+verySync.pwBL, rf+verySync.pwFR, rb+verySync.pwBR);
        }
    }

    class DHLift extends Lift {
        @Override
        public void tele() {
            curTicks += (int) Math.round( R.P.gamepad2.left_stick_y*liftSpeed );
            if ( R.P.gamepad2.dpad_down ) { curTicks = liftLowerPoint; }
            if ( R.P.gamepad2.dpad_right ){ curTicks = liftMiddlePoint; }
            if ( R.P.gamepad2.dpad_up )   { curTicks = liftHighPoint; }
            lpid.setPosition(curTicks);
            lpid.tick();
        }
    }

    class DHLiftClassic extends Lift {
        long timeouter = System.currentTimeMillis();
        int state = 0;
        int posState = 0;
        void selfTick() {
            if ( state != 0 ) {
                if ( state == 1 ) {
                    if ( System.currentTimeMillis() - timeouter > clLiftState1Timer ) { state = 2; timeouter = System.currentTimeMillis(); return; }
                    L.setPower(clLiftState1Pw);
                }
                else if ( state == 2 ) {
                    if ( System.currentTimeMillis() - timeouter > clLiftState2Timer ) { state = 0; timeouter = System.currentTimeMillis(); posState = 1; return; }
                    L.setPower(clLiftState2Pw);
                }
                else if ( state == -1 ) {
                    if ( System.currentTimeMillis() - timeouter > clLiftState_1Timer ) { state = -2; timeouter = System.currentTimeMillis(); return; }
                    L.setPower(clLiftState_1Pw);
                }
                else if ( state == -2 ) {
                    if ( System.currentTimeMillis() - timeouter > clLiftState_2Timer ) { state = 0; timeouter = System.currentTimeMillis(); posState = 0; return; }
                    L.setPower(clLiftState_2Pw);
                }
            }
        }
        @Override
        public void tele() {
            if ( posState == 0 ) { P.gamepad2.setLedColor(1, .3, 0,100); }
            else { P.gamepad2.setLedColor(.5, 0, .5, 100); }
            if ( P.gamepad2.dpad_down ) { posState = 0; }
            if ( P.gamepad2.dpad_up ) { posState = 1; }
            if ( P.gamepad2.b && state == 0 ) {
                if ( posState == 0 ) { state = 1; timeouter = System.currentTimeMillis(); }
                else { state = -1; timeouter = System.currentTimeMillis(); }
            }
            selfTick();
            if ( state == 0 ) {
                if ( posState == 0 ) { L.setPower(clLiftHoldPw+(-P.gamepad2.left_stick_y*rotLiftSpeed)); }
                else { L.setPower((-P.gamepad2.left_stick_y*rotLiftSpeed)); }
            }
            L_L.setPower(-P.gamepad2.right_stick_y*forwLiftSpeed);
        }
    }
}
