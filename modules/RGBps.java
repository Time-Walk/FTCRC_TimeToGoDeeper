package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.modules.superclasses.Module;

@Config
public class RGBps extends Module {
    public boolean state = true;
    public static int speed = 70;
    double R = 0;
    double G = 0;
    double B = 0;
    int stateCol = 0;
    @Override
    public void initModule() {
        R = 1;
        stateCol = 0;
        RGBLOL.start();
    }
    public Thread RGBLOL = new Thread() {
        @Override
        public void run() {
            while ( !P.L.isStopRequested() && state ) {
                switch ( stateCol ) {
                    case 0:
                        G += .01;
                        P.gamepad2.setLedColor(R, G, B, speed+25);
                        delay(speed);
                        if (G > .99) {
                            stateCol++;
                        }
                        break;
                    case 1:
                        R -= .01;
                        P.gamepad2.setLedColor(R, G, B, speed+25);
                        delay(speed);
                        if (R < .01) {
                            stateCol++;
                        }
                        break;
                    case 2:
                        B += .01;
                        P.gamepad2.setLedColor(R, G, B, speed+25);
                        delay(speed);
                        if (B > .99) {
                            stateCol++;
                        }
                        break;
                    case 3:
                        G -= .01;
                        P.gamepad2.setLedColor(R, G, B, speed+25);
                        delay(speed);
                        if (G < .01) {
                            stateCol++;
                        }
                        break;
                    case 4:
                        R += .01;
                        P.gamepad2.setLedColor(R, G, B, speed+25);
                        delay(speed);
                        if (R > .99) {
                            stateCol++;
                        }
                        break;
                    case 5:
                        B -= .01;
                        P.gamepad2.setLedColor(R, G, B, speed+25);
                        delay(speed);
                        if (B < .01) {
                            stateCol = 0;
                        }
                        break;
                }
            }
        }
    };
}
