package org.firstinspires.ftc.teamcode.scenes.tele.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.func.classes.driverhelperv2.AngleSponsor;
import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpPack;

@TeleOp(name = "больше переходов через 180 для бога иму")
public class frickinimu extends TeleOpPack {
    AngleSponsor as;
    @Override
    public void doSetup() {
        as = new AngleSponsor(R);
    }
    @Override
    public void doActions() {
        as.tick();
        telemetry.addData("angle", as.RobotAngle);
        telemetry.addData("real angle", R.imuv2.getAngle());
        telemetry.addData("up angle", as.upAngle);
        telemetry.update();
    }
}
