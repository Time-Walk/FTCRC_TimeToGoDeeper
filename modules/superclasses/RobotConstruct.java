package org.firstinspires.ftc.teamcode.modules.superclasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.func.classes.DriverHelper;
import org.firstinspires.ftc.teamcode.modules.Ascent;
import org.firstinspires.ftc.teamcode.modules.Grab;
import org.firstinspires.ftc.teamcode.modules.Lift;
import org.firstinspires.ftc.teamcode.modules.wheelbase;

public class RobotConstruct {
    public Telemetry telemetry;
    public LinearOpMode L;
    public HardwareMap hwpm;
    public wheelbase wb;
    public Grab gb;
    public Ascent ac;
    public Lift lift;


    public void init(Telemetry telemetry, LinearOpMode L, HardwareMap hwmp) { // init fields here
        this.telemetry = telemetry;
        this.L = L;
        this.hwpm = hwmp;

        wb = new wheelbase();
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



    }

public void gamepad_init(Gamepad gamepad1, Gamepad gamepad2){ //init gamepad here
        //
    wb.init_gamepad(gamepad1,gamepad2);
    gb.init_gamepad(gamepad1,gamepad2);
    ac.init_gamepad(gamepad1,gamepad2);
    lift.init_gamepad(gamepad1,gamepad2);
}
}
