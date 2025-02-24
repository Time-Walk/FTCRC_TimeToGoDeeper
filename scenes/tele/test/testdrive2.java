package org.firstinspires.ftc.teamcode.scenes.tele.test;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;
import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpPack;

@Config
@Disabled
@TeleOp(name="шедевр: тест меканум паравозик", group="")
public class testdrive2 extends TeleOpPack {
    @Override
    public void doActions() {
        // порядок паравозика 1) паша ведущий 2) козырное место семен 3) я 4) матвей  5) замыкающий семен(альт)
        R.wb.zov();
        R.gb.tele();
        R.ac.tele();
        R.lift.tele();
    }

}
//
