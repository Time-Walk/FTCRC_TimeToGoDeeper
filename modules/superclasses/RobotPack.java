package org.firstinspires.ftc.teamcode.modules.superclasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotPack {
    public LinearOpMode L;
    public HardwareMap hwmp;
    public Telemetry telemetry;
    public Gamepad gamepad1;
    public Gamepad gamepad2;
    RobotPack(LinearOpMode L, HardwareMap hwmp, Telemetry telemetry, Gamepad gamepad1, Gamepad gamepad2) {
        this.L = L; this.hwmp = hwmp; this.telemetry = telemetry;
        this.gamepad1 = gamepad1; this.gamepad2 = gamepad2;
    }
}
