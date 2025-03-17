package org.firstinspires.ftc.teamcode.func.classes.driverhelperv2;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.func.DHStates;
import org.firstinspires.ftc.teamcode.modules.Wheelbase;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

@Config
public class DriverHelperWithStates {
    RobotPortal R;

    public int DHState = DHStates.DEFAULT;

    int LL_target = 0;
    int LC_target = 0;
    int d_x_target = 180;
    int d_y_target = 90;

    boolean specimen_isGoingDown = false;

    public Lifter_v42 LiftDH;
    public DifferencialController GrabDH;
    public WheelBase_v42 wbDH;

    public DriverHelperWithStates(RobotPortal R) {
        this.R = R;
        wbDH = new WheelBase_v42();
        wbDH.init(R.P);
        R.wb = wbDH;
        LiftDH = new Lifter_v42();
        LiftDH.init(R.P);
        LiftDH.initForDH(R);
        R.lift = LiftDH;
        GrabDH = new DifferencialController();
        GrabDH.init(R.P);
        R.gb = GrabDH;
    }

    public void setDHState(int state) {
        DHState = state;
        switch ( state ) {
            case DHStates.DEFAULT:
                LC_target = 0;
                d_x_target = 180;
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
                LL_target = Lift_LL.upper_ticks;
                break;
            case DHStates.TAKING_SPECIMEN:
                d_x_target = 180;
                d_y_target = 60;
                LC_target = Lift_LC.spec_ticks;
                break;
            case DHStates.TRIED_SPECIMEN:
                d_x_target = 180;
                d_y_target = 180;
                break;
            case DHStates.SPECIMEN:
                d_x_target = 0;
                d_y_target = 180;
                LC_target = Lift_LC.upper_ticks;
                LL_target = Lift_LL.upper_ticks;
                specimen_isGoingDown = false;
                break;
        }
    }

    public void tick() { // врум врум самолетики
        if ( R.P.gamepad2.options ) { setDHState(DHStates.DEFAULT); }
        switch ( DHState ) {
            case DHStates.DEFAULT:
                if ( R.P.gamepad2.dpad_up ) {
                    d_x_target = 180;
                    setDHState(DHStates.TAKING_SAMPLE_GODOWN_1);
                }
                else if ( R.P.gamepad2.dpad_left ) {
                    d_x_target = 135;
                    setDHState(DHStates.TAKING_SAMPLE_GODOWN_1);
                }
                else if ( R.P.gamepad2.dpad_down ) {
                    d_x_target = 90;
                    setDHState(DHStates.TAKING_SAMPLE_GODOWN_1);
                }
                else if ( R.P.gamepad2.dpad_right ) {
                    d_x_target = 45;
                    setDHState(DHStates.TAKING_SAMPLE_GODOWN_1);
                }
                else if ( R.P.gamepad2.y ) {
                    setDHState(DHStates.TAKING_SPECIMEN);
                }
                break;
            case DHStates.TAKING_SAMPLE_GOUP_4:
            case DHStates.TRIED_SAMPLE:
                if ( R.P.gamepad2.x ) {
                    setDHState(DHStates.SAMPLE);
                }
                else if ( R.P.gamepad2.b ) {
                    setDHState(DHStates.DEFAULT);
                }
                break;
            case DHStates.SAMPLE:
                if ( R.P.gamepad2.b ) {
                    setDHState(DHStates.DEFAULT);
                }
                else if ( R.P.gamepad1.right_trigger > .5 ) {
                    setDHState(DHStates.BASKET);
                }
                break;
            case DHStates.BASKET:
                if ( R.P.gamepad2.b ) {
                    setDHState(DHStates.DEFAULT);
                }
                break;
            case DHStates.TAKING_SPECIMEN:
                if ( R.P.gamepad2.a ) {
                    setDHState(DHStates.TRIED_SPECIMEN);
                }
                break;
            case DHStates.TRIED_SPECIMEN:
                if ( R.P.gamepad2.x ) {
                    setDHState(DHStates.SPECIMEN);
                }
                else if ( R.P.gamepad2.b ) {
                    setDHState(DHStates.TAKING_SPECIMEN);
                }
                break;
            case DHStates.SPECIMEN:
                if ( R.P.gamepad2.b ) {
                    specimen_isGoingDown = true;
                }
                else {
                    if ( specimen_isGoingDown ) {
                        setDHState(DHStates.DEFAULT);
                        specimen_isGoingDown = false;
                    }
                }
        }
    }

}
