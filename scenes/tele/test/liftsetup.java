package org.firstinspires.ftc.teamcode.scenes.tele.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.func.classes.LiftPID;
import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpPack;

@Config
@TeleOp
public class liftsetup extends TeleOpPack {
    LiftPID lpd;
    public static int pos1 = 200;
    public static int pos2 = 500;
    @Override
    public void doSetup() {
        telemetry = FtcDashboard.getInstance().getTelemetry();
        R.P.telemetry = telemetry;
        lpd = new LiftPID(R);
        telemetry.addData("Er", 0);
        telemetry.update();
    }
    @Override
    public void doActions() {
        lpd.setPosition(pos1);
        lpd.getToPosition();
        telemetry.addData("Got", "Position");
        telemetry.update();
        long timeout = System.currentTimeMillis();
        while ( System.currentTimeMillis() - timeout < 3000 ) {
            lpd.tick();
        }
        lpd.setPosition(pos2);
        lpd.getToPosition();
        telemetry.addData("Got","Position");
        telemetry.update();
        timeout = System.currentTimeMillis();
        while ( System.currentTimeMillis() - timeout < 3000 ) {
            lpd.tick();
        }
        R.lift.L.setPower(0);
    }
}
