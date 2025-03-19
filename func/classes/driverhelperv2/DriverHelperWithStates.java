package org.firstinspires.ftc.teamcode.func.classes.driverhelperv2;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.func.DHStates;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

@Config
public class DriverHelperWithStates {
    RobotPortal R;

    public int DHState = DHStates.DEFAULT;

    int LL_target = 0;
    int LC_target = 0;
    int d_x_target = 180;
    int d_y_target = 90;

    int will_dx_target = 180;

    boolean specimen_isGoingDown = false;

    public Lifter_v42 LiftDH;
    public DifferencialController GrabDH;
    public WheelBase_v42 wbDH;
    public AngleSponsor as;

    public DriverHelperWithStates(RobotPortal R) {
        this.R = R;
        as = new AngleSponsor(R);
        wbDH = new WheelBase_v42();
        wbDH.init(R.P);
        wbDH.initWithAngleSponsor(as);
        wbDH.anotherDhInit();
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
        as.tick();
        DHState = state;
        switch ( state ) {
            case DHStates.DEFAULT:
                LC_target = 0;
                d_x_target = 180;
                d_y_target = 90;
                GrabDH.open();
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
                wbDH.timeredMoving(0, -.6, 0, 500);
                break;
            case DHStates.BASKET:
                d_x_target = 0;
                d_y_target = 135;
                LL_target = Lift_LL.upper_ticks;
                LC_target = Lift_LC.upper_ticks;
                wbDH.stblz.setTarget(45);
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
                wbDH.timeredMoving(0, -.6, 0, 500);
                break;
            case DHStates.TAKING_SAMPLE_GODOWN_1:
                break;
            case DHStates.TAKING_SAMPLE_ROTIN_2:
                d_x_target = will_dx_target;
                break;
            case DHStates.TAKING_SAMPLE_TAKING_3:
                GrabDH.close();
                timeout = System.currentTimeMillis();
                break;
            case DHStates.TAKING_SAMPLE_GOUP_4:
                d_y_target = 90;
                d_x_target = 90;
                break;
        }
    }

    long timeout = System.currentTimeMillis();

    public void tick() { // врум врум самолетики
        as.tick();
        if ( R.P.gamepad2.options ) { setDHState(DHStates.DEFAULT); }
        if ( R.P.gamepad2.share ) {
            R.lift.L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            R.lift.L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            R.lift.L_L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            R.lift.L_L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LiftDH.DLPID.setLlPosition(0);
            LiftDH.DLPID.setLcPosition(0);
            LiftDH.L.setPower(-R.P.gamepad2.left_stick_y);
            LiftDH.L_L.setPower(-R.P.gamepad2.right_stick_y);
        }
        if ( d_x_target != GrabDH.ox_target || d_y_target != GrabDH.oy_target ) {
            GrabDH.ox_target = d_x_target;
            GrabDH.oy_target = d_y_target;
            GrabDH.setTimer();
        }
        if ( LC_target != LiftDH.DLPID.getTargetLc() ) { LiftDH.DLPID.setLcPosition(LC_target); }
        if ( LL_target != LiftDH.DLPID.getTargetLl() ) { LiftDH.DLPID.setLlPosition(LL_target); }
        switch ( DHState ) {
            case DHStates.DEFAULT:
                if ( R.P.gamepad2.dpad_up ) {
                    //d_x_target = 180;
                    will_dx_target = 180;
                    setDHState(DHStates.TAKING_SAMPLE);
                }
                else if ( R.P.gamepad2.dpad_left ) {
                    //d_x_target = 135;
                    will_dx_target = 135;
                    setDHState(DHStates.TAKING_SAMPLE);
                }
                else if ( R.P.gamepad2.dpad_down ) {
                    //d_x_target = 90;
                    will_dx_target = 90;
                    setDHState(DHStates.TAKING_SAMPLE);
                }
                else if ( R.P.gamepad2.dpad_right ) {
                    //d_x_target = 45;
                    will_dx_target = 45;
                    setDHState(DHStates.TAKING_SAMPLE);
                }
                else if ( R.P.gamepad2.y ) {
                    setDHState(DHStates.TAKING_SPECIMEN);
                }
                break;
            case DHStates.TAKING_SAMPLE:
                if ( !GrabDH.isTimer ) { setDHState(DHStates.TAKING_SAMPLE_ROTIN_2); }
                break;
            case DHStates.TAKING_SAMPLE_ROTIN_2:
                if ( !GrabDH.isTimer ) { setDHState(DHStates.TAKING_SAMPLE_GODOWN_1); }
                break;
            case DHStates.TAKING_SAMPLE_GODOWN_1:
                if ( R.P.gamepad2.a ) {
                    setDHState(DHStates.TAKING_SAMPLE_TAKING_3);
                }
                break;
            case DHStates.TAKING_SAMPLE_TAKING_3:
                if ( System.currentTimeMillis() - timeout > DifferencialController.timeToMoving ) { setDHState(DHStates.TAKING_SAMPLE_GOUP_4); }
                break;
            case DHStates.TAKING_SAMPLE_GOUP_4:
                if ( !GrabDH.isTimer ) { setDHState(DHStates.TRIED_SAMPLE); }
                if ( R.P.gamepad2.x ) {
                    setDHState(DHStates.SAMPLE);
                }
                break;
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
                    GrabDH.pw_zov_l = .6;
                    GrabDH.pw_zov_r = -.6;
                    LL_target = 0;
                }
                else {
                    if ( specimen_isGoingDown ) {
                        GrabDH.pw_zov_l = 0;
                        GrabDH.pw_zov_r = 0;
                        setDHState(DHStates.DEFAULT);
                        specimen_isGoingDown = false;
                    }
                }
        }
    }

}
