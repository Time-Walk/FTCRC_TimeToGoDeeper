package org.firstinspires.ftc.teamcode.scenes.tele.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.func.classes.driverhelperv2.AngleSponsor;
import org.firstinspires.ftc.teamcode.func.classes.driverhelperv2.WheelBase_v42;
import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpPack;

@TeleOp
public class coscostest extends TeleOpPack {
    WheelBase_v42 wbwb;
    @Override
    public void doSetup() {
        AngleSponsor as = new AngleSponsor(R);
        wbwb = new WheelBase_v42();
        wbwb.init(R.P);
        wbwb.initWithAngleSponsor(as);
        R.wb = wbwb;
    }
    @Override
    public void doActions() {
        R.wb.tele();
        wbwb.as.tick();
    }
}
