package org.firstinspires.ftc.teamcode.func.classes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.modules.superclasses.RobotConstruct;
@Config
public class DriverHelper {
    RobotConstruct R = new RobotConstruct();
    Gamepad gamepad1, gamepad2;

    public void init(RobotConstruct R, Gamepad gamepad1,Gamepad gamepad2) {
        this.R = R;
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
    }
}
