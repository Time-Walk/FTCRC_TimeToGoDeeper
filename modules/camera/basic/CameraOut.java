
package org.firstinspires.ftc.teamcode.modules.camera.basic;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.modules.superclasses.Module;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

public class CameraOut extends Module { // Камера, которая используется для определения AprilTag
    public OpenCvCamera camera;
    public Servo cameraServo;
    int state = -1;
    @Override
    public void initModule() {
        int cameraMonitorViewId = P.hwmp.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", P.hwmp.appContext.getPackageName());
        // получение Id монитора камеры
        WebcamName webcamName = P.hwmp.get(WebcamName.class, "Webcam"); // получение имени камеры из конфига
        camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId); // получение экземпляра камеры
        cameraServo = P.hwmp.get(Servo.class, "camera_servo"); // Получение сервомотора
    }

    public int getRotationState() { return state; } // Получение состояния вращения сервомотора

    public void setRotationState(int state) { // Установка состояния вращения сервомотора
        this.state = state;
        switch ( state ) {
            case -1:
                cameraServo.setPosition(0);
                break;
            case 0:
                cameraServo.setPosition(.5);
                break;
            case 1:
                cameraServo.setPosition(1);
                break;
        }
    }

    public void openWithPipeline(OpenCvPipeline pipe, boolean isStreamToDash) { // Открыть камера с Pipeline, стримить ли в Ftc Dashboard
        P.telemetry.addData("camera", "started open");
        P.telemetry.update();
        camera.setPipeline(pipe);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() { // Открытие камеры с нужным Pipeline
            @Override
            public void onOpened() {
                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
                if ( isStreamToDash ) {
                    FtcDashboard dash = FtcDashboard.getInstance();
                    dash.startCameraStream(camera, 30); // Стрим в FtcDashboard
                }
                P.telemetry.addData("camera", "opened");
                P.telemetry.update();
            }

            @Override
            public void onError(int errorCode) {

            }
        });
    }
    /*public Mat getrtvec(String n) {
        AprilTagDetectionPipeline April = new AprilTagDetectionPipeline();
        April.April(0.1016,0,0,0,0,telemetry);
        Mat a = new Mat();
        if (n == "tvec") {
            a =  AprilTagDetectionPipeline.tvec;
        } else if (n == "rvec") {
            a = AprilTagDetectionPipeline.rvec;
        }
        return a;
        }*/

    }
