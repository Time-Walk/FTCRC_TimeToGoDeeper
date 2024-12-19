package org.firstinspires.ftc.teamcode.scenes.auto.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.camera.Pipeline;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotConstruct;
import org.firstinspires.ftc.teamcode.modules.camera.AprilTagDetectionPipeline;

@Autonomous(name="CameraTest", group="")
public class CameraTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RobotConstruct R = new RobotConstruct();
        R.init(telemetry, this, hardwareMap);
        Telemetry ftcTele = FtcDashboard.getInstance().getTelemetry();
        AprilTagDetectionPipeline pipe = new AprilTagDetectionPipeline(0.1016,640,480,0,0,telemetry);
        pipe.setDecimation(2);
        pipe.constructMatrix();
        ftcTele.addData("m", pipe.cameraMatrix);
        ftcTele.update();

        //Pipeline pipe = new Pipeline();
        R.cam.openWithPipeline(pipe, true);

        while (!isStarted() && !isStopRequested()) {
            //telemetry.addData("tvec", R.cam.getrtvec("tvec"));
            //telemetry.addData("rvec", R.cam.getrtvec("rvec"));
            //telemetry.update();
           // R.wb.delay(500);
        }


        //okay, let's go!

    }

}
