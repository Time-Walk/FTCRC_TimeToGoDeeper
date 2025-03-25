package org.firstinspires.ftc.teamcode.scenes.auto.field;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.func.Alliance;
import org.firstinspires.ftc.teamcode.func.classes.AbsoluteRider;
import org.firstinspires.ftc.teamcode.func.classes.CamLocalizationTicker;
import org.firstinspires.ftc.teamcode.func.classes.DoubleLiftPID;
import org.firstinspires.ftc.teamcode.func.classes.driverhelperv2.AngleSponsor;
import org.firstinspires.ftc.teamcode.func.classes.driverhelperv2.DebugedLol;
import org.firstinspires.ftc.teamcode.func.classes.driverhelperv2.DifferencialController;
import org.firstinspires.ftc.teamcode.func.classes.driverhelperv2.Lift_LC;
import org.firstinspires.ftc.teamcode.func.classes.driverhelperv2.Lift_LL;
import org.firstinspires.ftc.teamcode.func.classes.driverhelperv2.Stabilizator_v20000000000000000042;
import org.firstinspires.ftc.teamcode.modules.camera.concepts.CalibrationForCamLocalization;
import org.firstinspires.ftc.teamcode.modules.camera.concepts.CamLocalization;
import org.firstinspires.ftc.teamcode.scenes.superclasses.AutonomousPack;
import org.opencv.core.Point;

@Config
@Autonomous(name="корпорация морского приборостроения")
public class imgkms extends AutonomousPack {
    public static double startyw = 90.5;
    CamLocalization loc;
    CamLocalizationTicker clticker;
    AbsoluteRider absoluteRider;
    CalibrationForCamLocalization calib;
    AngleSponsor as;
    Stabilizator_v20000000000000000042 stblz;
    DoubleLiftPID dpid;
    DifferencialController df;
    DebugedLol dbg;
    @Override
    public void doSetup() {
        R.cam.setRotationState(0);
        loc = new CamLocalization(R.cam, true, Alliance.BLUE);
        loc.tick(R.imuv2.getAngle());
        clticker = new CamLocalizationTicker(loc, R);
        clticker.getNewVals();
        calib = new CalibrationForCamLocalization(R.P);
        absoluteRider = new AbsoluteRider(R);
        as = new AngleSponsor(R);
        stblz = new Stabilizator_v20000000000000000042(R, as);
        dpid = new DoubleLiftPID(R);
        df = new DifferencialController();
        df.init(R.P);
        dbg = new DebugedLol();
        df.initAgain(dbg);
    }
    @Override
    public void doSetupMovings() {
        df.close();
        CalibrationForCamLocalization.yw = startyw;
        Point p1 = loc.pipe.detection.corners[0];
        Point p2 = loc.pipe.detection.corners[1];
        CamLocalization.beta = calib.calibrateBeta((p1.y+p2.y)/2);
        df.curPos_oy = -45;
        df.curPos_ox = 180;
        df.oy_target = 90;
        df.ox_target = 180;
        dpid.setLcPosition(300);
        dpid.getToPositionByLc();
        df.setTimer();
        while ( df.isTimer ) { df.tele(); dpid.tick(); }
    }
    @Override
    public void doActions() {

        absoluteRider.setGoal(90, 180);
        absoluteRider.andRot(0);
        clticker.getNewVals();
        absoluteRider.getToPos(clticker);
        stblz.setTarget(90);
        R.cam.setRotationState(1);
        while ( Math.abs(stblz.getTarget() - as.RobotAngle ) > 2 ) {
            R.wb.setAxisPower(0, 0, stblz.tick());
            as.tick();
        }
        as.upAngle = as.RobotAngle;
        while ( Math.abs(stblz.getTarget() - as.RobotAngle ) > 2 ) {
            R.wb.setAxisPower(0, 0, stblz.tick());
            as.tick();
        }
        //absoluteRider.setGoal(90, 180);
        //absoluteRider.andRot(0);
        //clticker.getNewVals();
        //absoluteRider.getToPos(clticker);
        /*dpid.setLcPosition(Lift_LC.upper_ticks);
        dpid.setLlPosition(Lift_LL.upper_ticks);
        dpid.getToPositionByLl();
        dpid.getToPositionByLc();
        df.pw_zov_l = .6;
        df.pw_zov_r = -.6;
        long timeout = System.currentTimeMillis();
        while ( System.currentTimeMillis() - timeout < 700 ) {
            dpid.tick();
            df.tele();
        }
        df.open();*/
    }
}
