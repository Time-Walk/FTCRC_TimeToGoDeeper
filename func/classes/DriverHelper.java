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
    DHLift dhlift;
    LiftPID lpid;
    VerySync verySync;
    int curTicks = 0;
    public static double liftSpeed = 10;
    public static int liftLowerPoint = 100;
    public static int liftMiddlePoint = 400;
    public static int liftHighPoint = 700;

    public DriverHelper(RobotPortal R) {
        this.R = R;
        dhwb = new DHWheelBase();
        dhwb.init(R.P);
        R.wb = dhwb;
        dhlift = new DHLift();
        dhlift.init(R.P);
        R.lift = dhlift;
        lpid = new LiftPID(R);
        verySync = new VerySync(R);
    }

    class DHWheelBase extends Wheelbase {
        @Override
        public void tele() {
            verySync.tick();
            double lf = -P.gamepad1.left_stick_y + P.gamepad1.left_stick_x + (P.gamepad1.right_stick_x * 0.6);
            double lb = -P.gamepad1.left_stick_y - P.gamepad1.left_stick_x + (P.gamepad1.right_stick_x * 0.6);
            double rf = P.gamepad1.left_stick_y + P.gamepad1.left_stick_x + (P.gamepad1.right_stick_x * 0.6);
            double rb = P.gamepad1.left_stick_y - P.gamepad1.left_stick_x + (P.gamepad1.right_stick_x * 0.6);
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
}
