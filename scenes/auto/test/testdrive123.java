package org.firstinspires.ftc.teamcode.scenes.auto.test;

import static org.firstinspires.ftc.teamcode.func.classes.DrivePD.pwfl;
import static org.firstinspires.ftc.teamcode.func.classes.DrivePD.pwfr;
import static org.firstinspires.ftc.teamcode.func.classes.DrivePD.tickl;
import static org.firstinspires.ftc.teamcode.func.classes.DrivePD.tickr;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.func.classes.DrivePD;
import org.firstinspires.ftc.teamcode.func.classes.RotatePD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;
import org.firstinspires.ftc.teamcode.scenes.superclasses.AutonomousPack;

@Config
@Autonomous(name="шедевр синий", group="")
public class testdrive123 extends AutonomousPack {
    public static double x = 1000;
    @Override
    public void doActions() {

        //okay, let's go!


        drp.go(x);
        telemetry.addData("LFT", R.wb.LF.getCurrentPosition());
        telemetry.addData("RFT", R.wb.RF.getCurrentPosition());
        telemetry.addData("tl", tickl);
        telemetry.addData("tr", tickr);
        telemetry.addData("pl", pwfl);
        telemetry.addData("pr", pwfr);
        telemetry.update();
        R.lift.delay(10000);

    }

}
//
