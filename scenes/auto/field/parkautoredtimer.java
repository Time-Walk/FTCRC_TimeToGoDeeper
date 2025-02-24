package org.firstinspires.ftc.teamcode.scenes.auto.field;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.scenes.superclasses.AutonomousPack;

@Config
@Autonomous(name="парковка синий", group="")
public class parkautoredtimer extends AutonomousPack {
    public static double x = 20000;
    @Override
    public void doActions() {

        R.wb.timer(-0.5,0.5,-0.5,0.5, 2000);

    }

}
//
