package org.firstinspires.ftc.teamcode.scenes.auto.field;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.func.classes.DrivePD;
import org.firstinspires.ftc.teamcode.func.classes.RotatePD;
import org.firstinspires.ftc.teamcode.func.classes.driverhelperv2.AngleSponsor;
import org.firstinspires.ftc.teamcode.func.classes.driverhelperv2.Stabilizator_v20000000000000000042;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;
import org.firstinspires.ftc.teamcode.scenes.superclasses.AutonomousPack;

import java.security.SignatureSpi;

@Config
@Autonomous(name="парковка красный", group="")
public class parkautobluetimer extends AutonomousPack {
    public static double x = 700;
    AngleSponsor as;
    Stabilizator_v20000000000000000042 stblz;
    @Override
    public void doSetup() {
        as = new AngleSponsor(R);
        stblz = new Stabilizator_v20000000000000000042(R, as);
    }
    @Override
    public void doActions() {

        //R.wb.timer(0.5,-0.5,0.5,-0.5, 2000);
        //R.wb.timer(-0.5,-0.5,0.5,0.5,777);

        long timeout = System.currentTimeMillis();
        while ( System.currentTimeMillis() - timeout < x ) {
            R.wb.setAxisPower(.5, 0, stblz.tick());
        }
        R.wb.setMtZero();
    }
}
//
