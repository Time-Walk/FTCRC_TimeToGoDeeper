package org.firstinspires.ftc.teamcode.scenes.auto.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.func.Alliance;
import org.firstinspires.ftc.teamcode.modules.camera.concepts.CamLocalization;
import org.firstinspires.ftc.teamcode.modules.camera.pipelines.AprilTagDetectionPipeline;
import org.firstinspires.ftc.teamcode.scenes.superclasses.AutonomousPack;
import org.opencv.core.Point;

@Autonomous(name = "ахаха никита")
public class CameraTest123 extends AutonomousPack {
    @Override
    public void doSetup() {
        Telemetry dashTele = FtcDashboard.getInstance().getTelemetry();
        CamLocalization loc = new CamLocalization(R.cam, true, Alliance.BLUE);

        while (!isStopRequested()) {
            dashTele.addData("писюн", "пенис");
            if ( loc.pipe.isAprilTagDetected ) {
                Point pleft = loc.pipe.detection.corners[0];
                Point pright = loc.pipe.detection.corners[1];
                double[] cam_xy = loc.pic2r(Math.toRadians(R.imuv2.getAngle()), Math.toRadians(CamLocalization.beta), (pleft.x+pright.x)/2, (pleft.y+pright.y)/2, CamLocalization.height);
                dashTele.addData("x", cam_xy[0]);
                dashTele.addData("y", cam_xy[1]);
                dashTele.addData("rot", R.imuv2.getAngle());
            }
            dashTele.update();
        }
        R.cam.camera.closeCameraDevice();
    }
    @Override
    public void doActions() {

    }
}
