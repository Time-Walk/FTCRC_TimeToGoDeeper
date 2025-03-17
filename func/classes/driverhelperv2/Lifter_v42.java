package org.firstinspires.ftc.teamcode.func.classes.driverhelperv2;

import org.firstinspires.ftc.teamcode.func.classes.DoubleLiftPID;
import org.firstinspires.ftc.teamcode.modules.Lift;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

public class Lifter_v42 extends Lift {
    public DoubleLiftPID DLPID;
    public void initForDH(RobotPortal R) {
        DLPID = new DoubleLiftPID(R);
    }
    @Override
    public void tele() {
        double[] pw = DLPID.tick();
        L.setPower(pw[0]);
        L_L.setPower(pw[1]);
    }
}
