package org.firstinspires.ftc.teamcode.scenes.auto.test;

import static org.firstinspires.ftc.teamcode.func.classes.DrivePD.pwfl;
import static org.firstinspires.ftc.teamcode.func.classes.DrivePD.pwfr;
import static org.firstinspires.ftc.teamcode.func.classes.DrivePD.tickl;
import static org.firstinspires.ftc.teamcode.func.classes.DrivePD.tickr;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.func.classes.DrivePD;
import org.firstinspires.ftc.teamcode.func.classes.RotatePD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotConstruct;
@Config
@Autonomous(name="шедевр красный", group="")
public class testrtate123 extends LinearOpMode {
    public static double x = 500;
    @Override
    public void runOpMode() throws InterruptedException {
        RobotConstruct R = new RobotConstruct();
        R.init(telemetry, this, hardwareMap);
        DrivePD drp = new DrivePD();
        drp.init(R, this);
        RotatePD rpd = new RotatePD();
        rpd.init(R, this);
        waitForStart();

        //okay, let's go!


        rpd.rotate(90);
        telemetry.addData("Angle: " ,R.imuv2.getAngle());
        telemetry.update();
        R.lift.delay(10000);

    }

}
//
