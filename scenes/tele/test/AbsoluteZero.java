package org.firstinspires.ftc.teamcode.scenes.tele.test;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.func.Alliance;
import org.firstinspires.ftc.teamcode.func.classes.AbsoluteRider;
import org.firstinspires.ftc.teamcode.func.classes.CamLocalizationTicker;
import org.firstinspires.ftc.teamcode.modules.camera.concepts.CamLocalization;
import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpPack;

@Config
@TeleOp(name = "0 IQ")
public class AbsoluteZero extends TeleOpPack {
    public static int ss = 0;
    public static double targetX = 60;
    public static double targetY = 60;
    public static int alliance = 0;
    CamLocalization loc;
    CamLocalizationTicker clticker;
    AbsoluteRider absoluteRider;
    @Override
    public void doSetup() {
        loc = new CamLocalization(R.cam, true, alliance);
        loc.tick(R.imuv2.getAngle());
        clticker = new CamLocalizationTicker(loc, R);
        absoluteRider = new AbsoluteRider(R);
        absoluteRider.setGoal(targetX, targetY);
        absoluteRider.notRot();
        absoluteRider.getToPos(clticker);
    }
    @Override
    public void doActions() {
        telemetry.addData("ready", "got to pos krch");
        telemetry.update();
        while ( opModeIsActive() ) {

        }
    }
}
