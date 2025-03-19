package org.firstinspires.ftc.teamcode.func.classes.driverhelperv2;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.func.classes.DoubleLiftPID;
import org.firstinspires.ftc.teamcode.modules.Lift;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

@Config
public class Lifter_v42 extends Lift {
    public DoubleLiftPID DLPID;
    public static double lc_speed = 5;
    public static double ll_speed = 10;
    public void initForDH(RobotPortal R) {
        DLPID = new DoubleLiftPID(R);
    }
    @Override
    public void tele() {
        if ( Math.abs(P.gamepad2.left_stick_y) > .05 ) {
            DLPID.setLcPosition(DLPID.getTargetLc() + (int)Math.round(-P.gamepad2.left_stick_y*lc_speed));
        }
        if ( Math.abs(P.gamepad2.right_stick_y) > .05 ) {
            DLPID.setLlPosition(DLPID.getTargetLl() + (int)Math.round(-P.gamepad2.right_stick_y*ll_speed));
        }
        if ( !P.gamepad2.share ) {
            double[] pw = DLPID.tick();
            L.setPower(pw[0]);
            L_L.setPower(pw[1]);
        }
    }
}
