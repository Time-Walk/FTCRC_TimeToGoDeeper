package org.firstinspires.ftc.teamcode.func.classes.driverhelperv2;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.func.DHStates;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

@Config
public class DriverHelperWithStates {
    RobotPortal R;

    public int DHState = DHStates.DEFAULT;

    int LL_target = 0;
    int LC_target = 0;
    int d_x_target = 90;
    int d_y_target = 90;

    public DriverHelperWithStates(RobotPortal R) {
        this.R = R;
    }

    public void setDHState(int state) {
        DHState = state;
        switch ( state ) {
            case DHStates.DEFAULT:
                LC_target = 0;
                d_x_target = 90;
                d_y_target = 90;
                break;
            case DHStates.TAKING_SAMPLE:
                d_y_target = 0;
                break;
            case DHStates.TRIED_SAMPLE:
                d_x_target = 90;
                d_y_target = 90;
                break;
            case DHStates.SAMPLE:
                d_y_target = 90;
                d_x_target = 90;
                LL_target = 0;
                break;
            case DHStates.BASKET:
                d_x_target = 0;
                d_y_target = 135;
                LL_target =
        }
    }

    public void tick() {
        if ( R.P.gamepad2.options ) { setDHState(DHStates.DEFAULT); }

    }

}
