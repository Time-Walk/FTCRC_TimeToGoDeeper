package org.firstinspires.ftc.teamcode.modules.camera.concepts;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.modules.camera.pipelines.AprilTagDetectionPipeline;

@Config
public class CalibrationForCamLocalization { // In development...
     public static double x = 10;
     public static double y = 10;
     double fx = 0;
     double fy = 0;
     double cx = 0;
     double cy = 0;

     public CalibrationForCamLocalization() {
         fx = AprilTagDetectionPipeline.fx;
         fy = AprilTagDetectionPipeline.fy;
         cx = AprilTagDetectionPipeline.cx;
         cy = AprilTagDetectionPipeline.cy;
     }

     public double[] calibrateBasicValues(double x, double y) {
        return new double[] {4, 4};
     }
}
