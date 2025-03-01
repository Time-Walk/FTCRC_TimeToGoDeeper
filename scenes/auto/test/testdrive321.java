package org.firstinspires.ftc.teamcode.scenes.auto.test;

import static org.firstinspires.ftc.teamcode.func.classes.DrivePD_1ENC_M.pwf;
import static org.firstinspires.ftc.teamcode.func.classes.DrivePD_1ENC_M.tickp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.func.classes.DrivePD_1ENC_M;
import org.firstinspires.ftc.teamcode.func.classes.LiftPD;
import org.firstinspires.ftc.teamcode.func.classes.RotatePD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;
import org.firstinspires.ftc.teamcode.scenes.superclasses.AutonomousPack;
@Disabled
@Config
@Autonomous(name="шедевр адын энкоде", group="")
public class testdrive321 extends AutonomousPack {
    public static double a1 = 1200;
    public static double a2 = 1200;
    public static double a3 = 1200;
    public DrivePD_1ENC_M dpd;
    @Override
    public void doSetup() {
        DrivePD_1ENC_M dpd = new DrivePD_1ENC_M();
        dpd.init(R, this);
        LiftPD lpd = new LiftPD();
        lpd.init(R,this);
    }
    @Override
    public void doActions() {

        dpd.forward(a1);
        rpd.rotate(45);
      /*  lpd.go(1000);
        R.gb.gzv.setPosition(0.45);
        lpd.go(-800); */
        R.wb.delay(2000);
        rpd.rotate(90);
       // dpd.forward(a2);
       // rpd.rotate(45);

        telemetry.addData("LFT", R.wb.LF.getCurrentPosition());
        telemetry.addData("tl", tickp);
        telemetry.addData("pr", pwf);
        telemetry.update();
        R.lift.delay(10000);

    }

}
//
