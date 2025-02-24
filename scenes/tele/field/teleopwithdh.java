package org.firstinspires.ftc.teamcode.scenes.tele.field;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.func.classes.DriverHelper;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;
import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpPack;

@TeleOp(name = "Телеоп с драйвер хелпером платный - 1566р за каждое использование мне на карту тбанка")
public class teleopwithdh extends TeleOpPack {
    public DriverHelper dh;
    @Override
    public void doSetup() {
        dh = new DriverHelper(R);
    }
    @Override
    public void doSetupMovings() {
        R.lift.execute();
        R.lift.initencoder();
    }
    @Override
    public void doActions() {
        R.wb.tele();
        R.gb.tele();
        R.ac.tele();
        R.lift.tele();
    }
}
