package org.firstinspires.ftc.teamcode.scenes.tele.test;

import static org.firstinspires.ftc.teamcode.func.classes.driverhelperv2.WheelBase_v42.dpadSpeed;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.scenes.superclasses.TeleOpPack;

@TeleOp
public class Lol extends TeleOpPack {
    @Override
    public void doActions() {
        double addJx = 0;
        double addJy = 0;
        if ( gamepad1.dpad_down )  { addJy = -dpadSpeed; }
        if ( gamepad1.dpad_up )    { addJy = dpadSpeed;  }
        if ( gamepad1.dpad_left )  { addJx = -dpadSpeed; }
        if ( gamepad1.dpad_right ) { addJx = dpadSpeed;  }
        R.wb.setAxisPower(addJx, addJy, 0);
    }
}
