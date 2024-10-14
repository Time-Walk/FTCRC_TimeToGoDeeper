package org.firstinspires.ftc.teamcode.modules.superclasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.wheelbase;

public class RobotConstruct {
    public Telemetry telemetry;
    public LinearOpMode L;
    public HardwareMap hwpm;
    public wheelbase wb;


    public void init(Telemetry telemetry, LinearOpMode L, HardwareMap hwmp) { // init fields here
        this.telemetry = telemetry;
        this.L = L;
        this.hwpm = hwmp;

        wb = new wheelbase();
        wb.initFields(telemetry,L,hwmp);
        wb.init();
        //init encoder???



    }

public void gamepad_init(Gamepad gamepad1, Gamepad gamepad2){ //init gamepad here
        //
}
}
