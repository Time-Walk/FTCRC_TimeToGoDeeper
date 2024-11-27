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
import org.firstinspires.ftc.teamcode.modules.Wheelbase;
import org.firstinspires.ftc.teamcode.modules.camera.CameraOut;

public class RobotConstruct {
    public Telemetry telemetry;
    public LinearOpMode L;
    public HardwareMap hwpm;
    public Wheelbase wb;
    public Grab gb;
    public Ascent ac;
    public Lift lift;
    public IMU imu;
    public IMUv2 imuv2;
    public CameraOut cam;


    public void init(Telemetry telemetry, LinearOpMode L, HardwareMap hwmp) { // init fields here
        this.telemetry = telemetry;
        this.L = L;
        this.hwpm = hwmp;

        wb = new Wheelbase();
        wb.initFields(telemetry,L,hwmp);
        wb.init();
        //init encoder???

        gb = new Grab();
        gb.initFields(telemetry,L,hwmp);
        gb.init();

        ac = new Ascent();
        ac.initFields(telemetry,L,hwmp);
        ac.init();

        lift = new Lift();
        lift.initFields(telemetry,L,hwmp);
        lift.init();

        imuv2 = new IMUv2();
        imuv2.initFields(telemetry,L,hwmp);
        imuv2.init();

        cam = new CameraOut();
        cam.initFields(telemetry,L,hwmp);
        cam.init();



    }

public void gamepad_init(Gamepad gamepad1, Gamepad gamepad2){ //init gamepad here
        //
    wb.init_gamepad(gamepad1,gamepad2);
    gb.init_gamepad(gamepad1,gamepad2);
    ac.init_gamepad(gamepad1,gamepad2);
    lift.init_gamepad(gamepad1,gamepad2);
}
}
