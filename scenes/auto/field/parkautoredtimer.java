package org.firstinspires.ftc.teamcode.scenes.auto.field;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.func.classes.DrivePD;
import org.firstinspires.ftc.teamcode.func.classes.RotatePD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotConstruct;
@Config
@Autonomous(name="парковка синий", group="")
public class parkautoredtimer extends LinearOpMode {
    public static double x = 20000;
    @Override
    public void runOpMode() throws InterruptedException {
        RobotConstruct R = new RobotConstruct();
        R.init(telemetry, this, hardwareMap);
        waitForStart();
        DrivePD drp = new DrivePD();
        drp.init(R, this);
        RotatePD rpd = new RotatePD();
        rpd.init(R, this);
        //okay, let's go!


        R.wb.timer(-0.5,0.5,-0.5,0.5, 2000);

    }

}
//
