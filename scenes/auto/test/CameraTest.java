package org.firstinspires.ftc.teamcode.scenes.auto.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;
import org.firstinspires.ftc.teamcode.modules.camera.AprilTagDetectionPipeline;
import org.firstinspires.ftc.teamcode.scenes.superclasses.AutonomousPack;

@Autonomous(name="CameraTest", group="")
public class CameraTest extends AutonomousPack {
    @Override
    public void doSetup() {
        Telemetry ftcTele = FtcDashboard.getInstance().getTelemetry();
        AprilTagDetectionPipeline pipe = new AprilTagDetectionPipeline(0.1016,640,480,0,0,telemetry);
        pipe.setDecimation(2);
        pipe.constructMatrix();
        ftcTele.addData("m", pipe.cameraMatrix);
        ftcTele.update();

        //Pipeline pipe = new Pipeline();
        R.cam.openWithPipeline(pipe, true);
    }
    @Override
    public void doActions() {

        while (!isStarted() && !isStopRequested()) {
            //telemetry.addData("tvec", R.cam.getrtvec("tvec"));
            //telemetry.addData("rvec", R.cam.getrtvec("rvec"));
            //telemetry.update();
           // R.wb.delay(500);
        }


        //okay, let's go!

    }

}
