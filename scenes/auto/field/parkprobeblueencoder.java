package org.firstinspires.ftc.teamcode.scenes.auto.field;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.func.classes.DrivePD;
import org.firstinspires.ftc.teamcode.func.classes.RotatePD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;
import org.firstinspires.ftc.teamcode.scenes.superclasses.AutonomousPack;

@Config
@Autonomous(name="парковка+проба", group="")
public class parkprobeblueencoder extends AutonomousPack {
    public static double forward = 3500;
    public static double backward = -1250;
    public static long delay = 2000;
    @Override
    public void doActions() {/*
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
*/
        R.wb.timer(-0.2,-0.2,0.2,0.2,500);
        R.wb.delay(delay/2);
        drp.go(backward);
        R.wb.delay(delay/2);
        R.wb.timer(-0.2,-0.2,0.2,0.2,750);
        R.wb.delay(delay);
        drp.go(forward);



    }

}
//
