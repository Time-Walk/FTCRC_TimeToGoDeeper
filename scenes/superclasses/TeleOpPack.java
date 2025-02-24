package org.firstinspires.ftc.teamcode.scenes.superclasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

abstract public class TeleOpPack extends LinearOpMode {
    public RobotPortal R;
    @Override
    public void runOpMode() throws InterruptedException {
        R = new RobotPortal(telemetry, this, hardwareMap, gamepad1, gamepad2);
        doSetup();
        waitForStart();
        doSetupMovings();
        while (!isStopRequested()) {
            doActions();
        }
    }
    public void doSetup() {

    }
    public void doSetupMovings() {

    }
    abstract public void doActions();
}
