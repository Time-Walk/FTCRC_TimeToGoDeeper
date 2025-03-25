package org.firstinspires.ftc.teamcode.func.classes.driverhelperv2;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.func.DHStates;
import org.firstinspires.ftc.teamcode.modules.Grab;
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
    public DebugedLol dbg;
    boolean lbtnFlag = false;
    boolean rbtnFlag = false;

    public DriverHelperWithStates(RobotPortal R) {
        this.R = R;
        as = new AngleSponsor(R);
        dbg = new DebugedLol();
        wbDH = new WheelBase_v42();
        wbDH.init(R.P);
        wbDH.initWithAngleSponsor(as);
        wbDH.anotherDhInit(dbg);
        R.wb = wbDH;
        LiftDH = new Lifter_v42();
        LiftDH.init(R.P);
        LiftDH.initForDH(R, dbg);
        R.lift = LiftDH;
        GrabDH = new DifferencialController();
        GrabDH.init(R.P);
        GrabDH.initAgain(dbg);
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
                d_y_target = 30;
                break;
            case DHStates.TRIED_SAMPLE:
                break;
            case DHStates.SAMPLE:
                d_y_target = 90;
                d_x_target = 180;
                LL_target = 0;
                break;
            case DHStates.BASKET:
                d_x_target = 180;
                d_y_target = 155;
                LL_target = Lift_LL.upper_ticks;
                LC_target = Lift_LC.upper_ticks;
                wbDH.stblz.setTarget(45);
                break;
            case DHStates.TAKING_SPECIMEN:
                d_x_target = 180;
                d_y_target = 70;
                LC_target = Lift_LC.spec_ticks;
                break;
            case DHStates.TRIED_SPECIMEN:
                //d_x_target = 180;
                //d_y_target = 180;
                break;
            case DHStates.SPECIMEN:
                d_x_target = 0;
                d_y_target = 180;
                LC_target = Lift_LC.upper_ticks;
                LL_target = Lift_LL.chut_chut_niz;
                specimen_isGoingDown = false;
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
                d_x_target = 180;
                break;
            case DHStates.TAKING_SPECIMAN_TAKED:
                timeout = System.currentTimeMillis();
                break;
        }
    }

    long timeout = System.currentTimeMillis();
    boolean wellTried = false;

    public void tick() { // врум врум самолетики
        as.tick();
        if ( Math.abs(R.P.gamepad2.left_stick_y) > .05 ) {
            LC_target = LC_target + (int)Math.round(-R.P.gamepad2.left_stick_y*Lifter_v42.lc_speed);
        }
        if ( Math.abs(R.P.gamepad2.right_stick_y) > .05 ) {
            LL_target = LL_target + (int)Math.round(-R.P.gamepad2.right_stick_y*Lifter_v42.ll_speed);
        }
        if ( R.P.gamepad2.options && !dbg.isDebugMode ) { setDHState(DHStates.DEFAULT); }
        if ( R.P.gamepad2.share ) {
            /*R.lift.L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            R.lift.L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            R.lift.L_L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            R.lift.L_L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LiftDH.DLPID.setLlPosition(0);
            LiftDH.DLPID.setLcPosition(0);
            LC_target = 0;
            LL_target = 0;
            LiftDH.L.setPower(-R.P.gamepad2.left_stick_y);
            LiftDH.L_L.setPower(-R.P.gamepad2.right_stick_y);*/
            if ( !dbg.flag ) {
                dbg.flag = true;
                dbg.isDebugMode = !dbg.isDebugMode;
                //R.imuv2.initModule();
            }
        }
        else {
            if ( dbg.flag ) { dbg.flag = false; }
        }
        if ( dbg.isDebugMode ) {
            if ( R.P.gamepad2.left_stick_button ) {
                R.lift.L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                R.lift.L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                LiftDH.DLPID.setLcPosition(0);
                LC_target = 0;
            }
            if ( R.P.gamepad2.right_stick_button ) {
                R.lift.L_L.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                R.lift.L_L.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                LiftDH.DLPID.setLlPosition(0);
                LL_target = 0;
            }
            if ( R.P.gamepad2.start ) {
                R.imuv2.initModule();
            }
        }
        if ( d_x_target != GrabDH.ox_target || d_y_target != GrabDH.oy_target ) {
            GrabDH.ox_target = d_x_target;
            GrabDH.oy_target = d_y_target;
            GrabDH.setTimer();
        }
        if ( LC_target != LiftDH.DLPID.getTargetLc() ) { LiftDH.DLPID.setLcPosition(LC_target); }
        if ( LL_target != LiftDH.DLPID.getTargetLl() ) { LiftDH.DLPID.setLlPosition(LL_target); }
        if ( R.P.gamepad2.left_stick_button ) {
            if ( !lbtnFlag ) {
                lbtnFlag = true;
                if (LiftDH.L.getCurrentPosition() > Lift_LC.upper_ticks / 3 ) {
                    LC_target = 0;
                } else {
                    LC_target = Lift_LC.upper_ticks;
                }
            }
        }
        else {
            if ( lbtnFlag ) { lbtnFlag = false; }
        }
        if ( R.P.gamepad2.right_stick_button ) {
            if ( !rbtnFlag ) {
                rbtnFlag = true;
                if ( LiftDH.L_L.getCurrentPosition() > Lift_LL.upper_ticks / 3 ) {
                    LL_target = 0;
                }
                else {
                    LL_target = Lift_LL.upper_ticks;
                }
            }
        }
        else {
            if ( rbtnFlag ) { rbtnFlag = false; }
        }
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
                    //d_x_target = 45; // поменяли на 225 для более быстрого вращения
                    will_dx_target = 225;
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
                if ( R.P.gamepad2.dpad_up ) {
                    d_x_target = 180;
                }
                if ( R.P.gamepad2.dpad_left ) {
                    d_x_target = 135;
                }
                if ( R.P.gamepad2.dpad_down ) {
                    d_x_target = 90;
                }
                if ( R.P.gamepad2.dpad_right ) {
                    d_x_target = 225;
                }
                break;
            case DHStates.TAKING_SAMPLE_TAKING_3:
                if ( System.currentTimeMillis() - timeout > DifferencialController.timeToMoving ) { setDHState(DHStates.TRIED_SAMPLE); }
                break;
            case DHStates.TAKING_SAMPLE_GOUP_4:
                if ( !GrabDH.isTimer ) { LL_target = 10; setDHState(DHStates.SAMPLE); }
                break;
            case DHStates.TRIED_SAMPLE:
                if ( R.P.gamepad2.x ) {
                    setDHState(DHStates.TAKING_SAMPLE_GOUP_4);
                }
                else if ( R.P.gamepad2.b ) {
                    will_dx_target = d_x_target;
                    GrabDH.open();
                    setDHState(DHStates.TAKING_SAMPLE_GODOWN_1);
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
                    GrabDH.open();
                    timeout = System.currentTimeMillis();
                    setDHState(DHStates.BASKET_THROWING);
                }
                break;
            case DHStates.BASKET_THROWING:
                LL_target = 0;
                if ( System.currentTimeMillis() - timeout > DifferencialController.timeToMoving ) { setDHState(DHStates.DEFAULT); }
            case DHStates.TAKING_SPECIMEN:
                if ( R.P.gamepad2.a ) {
                    GrabDH.close();
                    setDHState(DHStates.TAKING_SPECIMAN_TAKED);
                }
                break;
            case DHStates.TAKING_SPECIMAN_TAKED:
                if ( System.currentTimeMillis() - timeout > DifferencialController.timeToMoving ) { setDHState(DHStates.TRIED_SPECIMEN); }
                if ( R.P.gamepad2.x ) { wellTried = true; }
            case DHStates.TRIED_SPECIMEN:
                if ( R.P.gamepad2.x || wellTried ) {
                    setDHState(DHStates.SPECIMEN);
                }
                else if ( R.P.gamepad2.b ) {
                    GrabDH.open();
                    setDHState(DHStates.TAKING_SPECIMEN);
                }
                break;
            case DHStates.SPECIMEN:
                wellTried = false;
                if ( R.P.gamepad2.b ) {
                    specimen_isGoingDown = true;
                    GrabDH.pw_zov_l = .6;
                    GrabDH.pw_zov_r = -.6;
                    LL_target = 0;
                }
                else {
                    if ( specimen_isGoingDown ) {
                        if ( R.P.gamepad2.dpad_left ) {
                            specimen_isGoingDown = false;
                            LL_target = Lift_LL.chut_chut_niz;
                            GrabDH.pw_zov_l = 0;
                            GrabDH.pw_zov_r = 0;
                        }
                        else {
                            GrabDH.open();
                            GrabDH.pw_zov_l = 0;
                            GrabDH.pw_zov_r = 0;
                            setDHState(DHStates.DEFAULT);
                            specimen_isGoingDown = false;
                        }
                    }
                }
        }
    }

}
