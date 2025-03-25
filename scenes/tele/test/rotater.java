package org.firstinspires.ftc.teamcode.scenes.tele.test;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.func.classes.driverhelperv2.AngleSponsor;
import org.firstinspires.ftc.teamcode.func.classes.driverhelperv2.Stabilizator_v20000000000000000042;
import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpPack;

@Config
@TeleOp(name = "этононстоп")
public class rotater extends TeleOpPack {
    public static int target = 90;
    Stabilizator_v20000000000000000042 stblz;
    AngleSponsor as;
    @Override
    public void doSetup() {
        as = new AngleSponsor(R);
        stblz = new Stabilizator_v20000000000000000042(R, as);
        stblz.setTarget(target);
    }
    @Override
    public void doActions() {
        as.tick();
        R.wb.setAxisPower(0, 0, stblz.tick());
        telemetry.addData("as", as.RobotAngle);
        telemetry.addData("angle", R.imuv2.getAngle());
        telemetry.update();
    }
}
