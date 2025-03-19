package org.firstinspires.ftc.teamcode.scenes.tele.test;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.func.classes.driverhelperv2.DifferencialController;
import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpPack;

@TeleOp
@Config
public class difftest extends TeleOpPack {
    public static int oxnow = 180;
    public static int oyhow = 90;
    public static int oxtarget = 180;
    public static int oytarget = 180;
    DifferencialController dc;
    @Override
    public void doSetup() {
        dc = new DifferencialController();
        dc.init(R.P);
        R.gb = dc;
        dc.curPos_ox = oxnow;
        dc.curPos_oy = oyhow;
        dc.oy_target = oytarget;
        dc.ox_target = oxtarget;
    }
    @Override
    public void doActions() {
        if ( gamepad2.a && !dc.isTimer ) {
            dc.setTimer();
        }
        dc.tele();
    }
}
