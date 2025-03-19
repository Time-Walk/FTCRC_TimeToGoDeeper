package org.firstinspires.ftc.teamcode.func.classes.driverhelperv2;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.modules.Grab;

@Config
public class DifferencialController extends Grab {
    public static double millisPerDegree = 3.75;
    public static long timeToMoving = 350;
    public int ox_target = 180;
    public int oy_target = 90;

    public int curPos_ox = 180;
    public int curPos_oy = 90;

    long timeout = System.currentTimeMillis();
    int zl_time = 0;
    int zr_time = 0;
    public boolean isTimer = true;

    public double pw_zov_l = 0;
    public double pw_zov_r = 0;

    double controlled_zov_l = 0;
    double controlled_zov_r = 0;

    double timered_zov_l = 0;
    double timered_zov_r = 0;

    public void setTimer() {
        zl_time = (int)Math.round((-(curPos_ox-ox_target)-(curPos_oy-oy_target))*millisPerDegree);
        zr_time = (int)Math.round((-(curPos_ox-ox_target)+(curPos_oy-oy_target))*millisPerDegree);
        timeout = System.currentTimeMillis();
        isTimer = true;
        curPos_ox = ox_target;
        curPos_oy = oy_target;
    }

    @Override
    public void tele() {
        timerTick();
        if ( P.gamepad2.left_stick_button && P.gamepad2.right_stick_button && P.gamepad2.x) {
            open();

        } else if (P.gamepad2.left_stick_button && P.gamepad2.right_stick_button && P.gamepad2.a) {
            close();
        }
        if (P.gamepad2.right_trigger > 0.5) {
            controlled_zov_l = 0.25;
            controlled_zov_r = -0.25;
        } else if (P.gamepad2.left_trigger > 0.5) {
            controlled_zov_l = -0.25;
            controlled_zov_r = 0.25;
        } else if (P.gamepad2.left_bumper) {
            controlled_zov_l = -1;
            controlled_zov_r = -1;
        } else if (P.gamepad2.right_bumper) {
            controlled_zov_l = 1;
            controlled_zov_r = 1;
        } else {
            controlled_zov_l = 0;
            controlled_zov_r = 0;
        }
        zov_l.setPower(pw_zov_l+controlled_zov_l+timered_zov_l);
        zov_r.setPower(pw_zov_r+controlled_zov_r+timered_zov_r);
    }

    void timerTick() {
        if ( isTimer ) {
            boolean s = true;
            long time_go = System.currentTimeMillis() - timeout;
            if ( zl_time != 0 ) {
                if ( time_go > Math.abs(zl_time) ) {
                    zl_time = 0;
                } else { s = false; }
                timered_zov_l = Math.signum(zl_time);
            }
            if ( zr_time != 0 ) {
                if ( time_go > Math.abs(zr_time)) {
                    zr_time = 0;
                } else { s = false; }
                timered_zov_r = Math.signum(zr_time);
            }
            if ( s ) {
                zov_l.setPower(0);
                zov_r.setPower(0);
                isTimer = false;
                zl_time = 0;
                zr_time = 0;
            }
        }
    }

    public void open() {
        gzv.setPosition(position_open);
    }
    public void close() {
        gzv.setPosition(position_close);
    }

}
