package org.firstinspires.ftc.teamcode.scenes.auto.field;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.func.classes.DrivePD;
import org.firstinspires.ftc.teamcode.func.classes.RotatePD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;
import org.firstinspires.ftc.teamcode.scenes.superclasses.AutonomousPack;

@Config
@Disabled
@Autonomous(name="парковка версия паравозик", group="")
public class parkunitimerv2 extends AutonomousPack {
    @Override
    public void doActions() {
        R.wb.timer(0.25,0.25,-0.25,-0.25, 4000);
    }

}

