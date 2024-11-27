package org.firstinspires.ftc.teamcode.scenes.auto.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.superclasses.RobotConstruct;
import org.firstinspires.ftc.teamcode.modules.camera.AprilTagDetectionPipeline;

@Autonomous(name="CameraTest", group="")
public class CameraTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RobotConstruct R = new RobotConstruct();
        R.init(telemetry, this, hardwareMap);
        AprilTagDetectionPipeline pipe = new AprilTagDetectionPipeline();

        R.cam.openWithPipeline(pipe, true);

        while (!isStarted() && !isStopRequested()) {
            telemetry.addData("tvec", R.cam.getrtvec("tvec"));
            telemetry.addData("rvec", R.cam.getrtvec("rvec"));
            telemetry.update();
            R.wb.delay(500);
        }


        //okay, let's go!

    }

}
