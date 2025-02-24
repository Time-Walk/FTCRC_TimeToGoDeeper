package org.firstinspires.ftc.teamcode.scenes.auto.test;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.func.classes.DrivePD;
import org.firstinspires.ftc.teamcode.func.classes.RotatePD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;
import org.firstinspires.ftc.teamcode.scenes.superclasses.AutonomousPack;

@Config
@Autonomous(name="шедевр красный", group="")
public class testrtate123 extends AutonomousPack {
    public static double x = 500;
    @Override
    public void doActions() {

        //okay, let's go!


        rpd.rotate(90);
        telemetry.addData("Angle: " ,R.imuv2.getAngle());
        telemetry.update();
        R.lift.delay(10000);

    }

}
//
