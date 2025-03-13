package org.firstinspires.ftc.teamcode.scenes.auto.field;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.func.classes.DrivePD;
import org.firstinspires.ftc.teamcode.func.classes.RotatePD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;
import org.firstinspires.ftc.teamcode.scenes.superclasses.AutonomousPack;

@Config
@Autonomous(name="парковка красный", group="")
public class parkautobluetimer extends AutonomousPack {
    public static double x = 20000;
    @Override
    public void doActions() {

        R.wb.timer(0.5,-0.5,0.5,-0.5, 2000);
        R.wb.timer(-0.5,-0.5,0.5,0.5,777);
    }
}
//
