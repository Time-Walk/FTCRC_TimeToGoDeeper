package org.firstinspires.ftc.teamcode.modules.camera.pipelines;

import android.sax.StartElementListener;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseRaw;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.apriltag.AprilTagDetectorJNI;
import org.openftc.apriltag.ApriltagDetectionJNI;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;

@Config
public class AprilTagDetectionPipeline extends OpenCvPipeline { // Pipeline для обнаружения AprilTag

    long detectorPtr;

    public boolean isAprilTagDetected = false;
    public AprilTagDetection detection = null;

    public static double fx = 601.32512335;
    public static double fy = 605.96041909;
    public static double cx = 379.76580887;
    public static double cy = 186.47226305;

    static final double TAGSIZE = .1016;
    public RobotPack P;

    public AprilTagDetectionPipeline(RobotPack P) {
        this.P = P;
        detectorPtr = AprilTagDetectorJNI.createApriltagDetector(AprilTagDetectorJNI.TagFamily.TAG_36h11.string, 3, 3);
    }

    @Override
    public Mat processFrame(Mat frame) {
        Mat frameGray = new Mat();
        Imgproc.cvtColor(frame, frameGray, Imgproc.COLOR_RGBA2GRAY);

        ArrayList<AprilTagDetection> detections = AprilTagDetectorJNI.runAprilTagDetectorSimple(detectorPtr, frameGray, TAGSIZE, fx, fy, cx, cy); // Пробуем обнаружить AprilTAg

        if (!detections.isEmpty()) { // Если AprilTag обнаружен
            detection = (AprilTagDetection) detections.toArray()[0]; // Сохраняем первый AprilTag (наша задача - локализация, по этому можно сохранить любой увиденный AprilTag)
            Imgproc.rectangle(frame, new Point(detection.corners[2].x, detection.corners[2].y), new Point(detection.corners[0].x, detection.corners[0].y), new Scalar(213, 0, 255), 3); // Рисуем прямогольник
            isAprilTagDetected = true;
        }

        return frame; // Возвращаем снимок с нарисованным прямоугольником

    }
}
