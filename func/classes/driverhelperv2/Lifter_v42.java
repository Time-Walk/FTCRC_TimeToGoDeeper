package org.firstinspires.ftc.teamcode.func.classes.driverhelperv2;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.func.classes.DoubleLiftPID;
import org.firstinspires.ftc.teamcode.modules.Lift;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

@Config
public class Lifter_v42 extends Lift {
    public DoubleLiftPID DLPID;
    public static double lc_speed = 25;
    public static double ll_speed = 40;
    public DebugedLol dbg;
    public void initForDH(RobotPortal R, DebugedLol dbg) {
        DLPID = new DoubleLiftPID(R);
        this.dbg = dbg;
    }
    @Override
    public void tele() {
        if ( !dbg.isDebugMode ) {
            double[] pw = DLPID.tick();
            L.setPower(pw[0]);
            L_L.setPower(pw[1]);
        }
        else {
            L.setPower(-P.gamepad2.left_stick_y*.75);
            L_L.setPower(-P.gamepad2.right_stick_y);
        }
    }
}
