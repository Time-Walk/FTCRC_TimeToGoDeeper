package org.firstinspires.ftc.teamcode.modules.superclasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.Ascent;
import org.firstinspires.ftc.teamcode.modules.Grab;
import org.firstinspires.ftc.teamcode.modules.IMU;
import org.firstinspires.ftc.teamcode.modules.IMUv2;
import org.firstinspires.ftc.teamcode.modules.Lift;
import org.firstinspires.ftc.teamcode.modules.RGBps;
import org.firstinspires.ftc.teamcode.modules.Wheelbase;
import org.firstinspires.ftc.teamcode.modules.camera.basic.CameraOut;

public class RobotPortal { // Класс для создания, хранения и инициализации классов модулей робота
    public Wheelbase wb;
    public Grab gb;
    public Ascent ac;
    public Lift lift;
    public IMU imu;
    public IMUv2 imuv2;
    public CameraOut cam;
    public RobotPack P;
    public RGBps rgb;


    public RobotPortal(Telemetry telemetry, LinearOpMode L, HardwareMap hwmp, Gamepad gamepad1, Gamepad gamepad2) {
        P = new RobotPack(L, hwmp, telemetry, gamepad1, gamepad2);

        wb = new Wheelbase();
        wb.init(P);
        //init encoder???

        gb = new Grab();
        gb.init(P);

        ac = new Ascent();
        ac.init(P);

        lift = new Lift();
        lift.init(P);

        imuv2 = new IMUv2();
        imuv2.init(P);

        cam = new CameraOut();
        cam.init(P);

        rgb = new RGBps();
        rgb.init(P);


    }
}
