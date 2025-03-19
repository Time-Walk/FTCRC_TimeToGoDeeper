package org.firstinspires.ftc.teamcode.modules.camera.concepts;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.modules.camera.pipelines.AprilTagDetectionPipeline;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

@Config
public class CalibrationForCamLocalization { // In development...
     public static double yw = 10;
     public static double hr = 27.5;
     public static double hat = 9.1;
     double fx = 0;
     double fy = 0;
     double cx = 0;
     double cy = 0;
     RobotPack P;

     public CalibrationForCamLocalization(RobotPack P) {
         fx = AprilTagDetectionPipeline.fx;
         fy = AprilTagDetectionPipeline.fy;
         cx = AprilTagDetectionPipeline.cx;
         cy = AprilTagDetectionPipeline.cy;
         this.P = P;
     }

     public double calibrateBeta(double y) {
        double alpha = Math.atan((y-cy)/fy);
        double phi = Math.atan(yw/(hr-hat));
        P.telemetry.addData("alpha", alpha);
        P.telemetry.addData("phi", phi);
        return Math.toDegrees((Math.PI/2) - alpha - phi);
     }
}
