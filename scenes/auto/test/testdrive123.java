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
import org.firstinspires.ftc.teamcode.func.classes.DrivePD_OLD;
import org.firstinspires.ftc.teamcode.func.classes.RotatePD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;
import org.firstinspires.ftc.teamcode.scenes.superclasses.AutonomousPack;
@Config
@Autonomous(name="шедевр сво", group="")
public class testdrive123 extends AutonomousPack {
    public static double x = 1000;
    public static double y =0;
    public DrivePD_OLD dpdo;
    @Override
    public void doSetup() {
        DrivePD_OLD dpdo = new DrivePD_OLD();
        dpdo = this.dpdo;
        dpdo.init(R,this);
    }
    @Override
    public void doActions() {
        dpdo.go(x,y);
        R.imuv2.delay(2500);
    }

}
//
