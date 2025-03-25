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
        R.cam.setRotationState(ss);
        loc = new CamLocalization(R.cam, true, alliance);
        loc.tick(R.imuv2.getAngle());
        clticker = new CamLocalizationTicker(loc, R);
        clticker.getNewVals();
        absoluteRider = new AbsoluteRider(R);
        absoluteRider.setGoal(targetX, targetY);
        absoluteRider.notRot();
    }
    @Override
    public void doActions() {
        loc.tick(R.imuv2.getAngle());
        absoluteRider.getToPos(clticker);
        R.P.telemetry.addData("abs x", loc.absX);
        R.P.telemetry.addData("abs y", loc.absY);
        R.P.telemetry.addData("t x", targetX);
        R.P.telemetry.addData("t y", targetY);
        telemetry.addData("ready", "got to pos krch");
        telemetry.update();
        while ( opModeIsActive() ) {

        }
    }
}
