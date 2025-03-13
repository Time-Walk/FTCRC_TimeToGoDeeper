package org.firstinspires.ftc.teamcode.func.classes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.modules.Lift;
import org.firstinspires.ftc.teamcode.modules.Wheelbase;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPack;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;
@Config // Конфигурируется в Ftc Dashboard
public class DriverHelper { // Класс с функциями, облегчающими работу операторов
    RobotPortal R;
    DHWheelBase dhwb;
    DHLiftClassic dhlift;
    LiftPID lpid;
    VerySync verySync;
    int curTicks = 0;
    public static double liftSpeed = 10;
    public static int liftLowerPoint = 100;
    public static int liftMiddlePoint = 400;
    public static int liftHighPoint = 700;
    public static double speedChangeCoef = .333;
    double curSpeedCoef = 1;
    boolean flag = false;
    public static long clLiftState1Timer = 600;
    public static double clLiftState1Pw = .75;
    public static long clLiftState2Timer = 450;
    public static double clLiftState2Pw = .3;
    public static long clLiftState_1Timer = 500;
    public static double clLiftState_1Pw = -.4;
    public static long clLiftState_2Timer = 500;
    public static double clLiftState_2Pw = .16;
    public static double clLiftHoldPw = .23;
    public static double rotLiftSpeed = .75;
    public static double forwLiftSpeed = -.5;
    public DriverHelper(RobotPortal R) {
        this.R = R;
        dhwb = new DHWheelBase();
        dhwb.init(R.P);
        R.wb = dhwb;
        dhlift = new DHLiftClassic();
        dhlift.init(R.P);
        R.lift = dhlift; // Заменяем оригинальные классы модулей робота на модифицированные
        //lpid = new LiftPID(R);
        verySync = new VerySync(R);
    }

    class DHWheelBase extends Wheelbase { // Модифицирование колесной базы
        @Override
        public void tele() {
            verySync.tick(); // Тикаем синхронный регулятор поворота
            if ( P.gamepad1.right_bumper ) { // Включение замедленного режима по кнопке
                if ( !flag ) {
                    flag = true;
                    if ( curSpeedCoef > .99 ) {
                        curSpeedCoef = speedChangeCoef;
                    }
                    else {
                        curSpeedCoef = 1;
                    }
                }
            }
            else {
                if ( flag ) { flag = false; }
            }
            double z = 0;
            double v =0;
            if (P.gamepad1.dpad_up) {z=-.7;} else if (P.gamepad1.dpad_down) {z=.7;}
            if (P.gamepad1.dpad_left) {v=.7;} else if (P.gamepad1.dpad_right) {v=-.7;} // Управление с помощью dpad
            double lf = -P.gamepad1.left_stick_y + P.gamepad1.left_stick_x + (P.gamepad1.right_stick_x * 0.6) - z - v;
            lf *= curSpeedCoef;
            double lb = -P.gamepad1.left_stick_y - P.gamepad1.left_stick_x + (P.gamepad1.right_stick_x * 0.6) - z + v;
            lb *= curSpeedCoef;
            double rf = P.gamepad1.left_stick_y + P.gamepad1.left_stick_x + (P.gamepad1.right_stick_x * 0.6) + z - v;
            rf *= curSpeedCoef;
            double rb = P.gamepad1.left_stick_y - P.gamepad1.left_stick_x + (P.gamepad1.right_stick_x * 0.6) + z + v;
            rb *= curSpeedCoef;
            setMtPower(lf+verySync.pwFL, lb+verySync.pwBL, rf+verySync.pwFR, rb+verySync.pwBR); // Подсчет и установка мощности на моторы, учитывая синхронный регулятор поворота
        }
    }

    class DHWheelBaseNew extends Wheelbase { // In development...
        @Override
        public void tele() {
            double lf_start = 135;
            double lf_angle = lf_start+R.imuv2.getAngle();
            double lb_start = 225;
            double lb_angle = lb_start+R.imuv2.getAngle();
            double rf_start = 45;
            double rf_angle = rf_start+R.imuv2.getAngle();
            double rb_start = 315;
            double rb_angle = rb_start+R.imuv2.getAngle();

            double lf = -1*Math.cos(lf_angle)*P.gamepad1.left_stick_x;
            double lb = -1*Math.cos(lb_angle)*P.gamepad1.left_stick_x;
            double rf = -1*Math.cos(rf_angle)*P.gamepad1.left_stick_x;
            double rb = -1*Math.cos(rb_angle)*P.gamepad1.left_stick_x;
            setMtPower(lf,rf,lb,rb);
        }
    }

    class DHLift extends Lift { // Модифицирование лифта: PID-регулятор
        @Override
        public void tele() {
            curTicks += (int) Math.round( R.P.gamepad2.left_stick_y*liftSpeed ); // Стик управляет не мощностью лифта, а изменяет таргет тиков для PID-регулятора
            if ( R.P.gamepad2.dpad_down ) { curTicks = liftLowerPoint; }
            if ( R.P.gamepad2.dpad_right ){ curTicks = liftMiddlePoint; }
            if ( R.P.gamepad2.dpad_up )   { curTicks = liftHighPoint; } // И немного заготовленных значений
            lpid.setPosition(curTicks);
            lpid.tick();
        }
    }

    class DHLiftClassic extends Lift { // Модифицирование лифта: таймеры
        long timeouter = System.currentTimeMillis(); // Для синхронного таймера
        int state = 0; // Переменная состояния, которая использует для переключения таймеров и мощностей
        int posState = 0; // Переменная состояния, которая отображает, в каком состоянии (внизу или вверху) лифт программно
        void selfTick() { // Управление лифтом по таймерам
            if ( state != 0 ) {
                if ( state == 1 ) {
                    if ( System.currentTimeMillis() - timeouter > clLiftState1Timer ) { state = 2; timeouter = System.currentTimeMillis(); return; }
                    L.setPower(clLiftState1Pw);
                }
                else if ( state == 2 ) {
                    if ( System.currentTimeMillis() - timeouter > clLiftState2Timer ) { state = 0; timeouter = System.currentTimeMillis(); posState = 1; return; }
                    L.setPower(clLiftState2Pw);
                }
                else if ( state == -1 ) {
                    if ( System.currentTimeMillis() - timeouter > clLiftState_1Timer ) { state = -2; timeouter = System.currentTimeMillis(); return; }
                    L.setPower(clLiftState_1Pw);
                }
                else if ( state == -2 ) {
                    if ( System.currentTimeMillis() - timeouter > clLiftState_2Timer ) { state = 0; timeouter = System.currentTimeMillis(); posState = 0; return; }
                    L.setPower(clLiftState_2Pw);
                }
            }
        }
        @Override
        public void tele() {
            if ( posState == 0 ) { P.gamepad2.setLedColor(1, .3, 0,100); }
            else { P.gamepad2.setLedColor(.5, 0, .5, 100); } // Изменение подсветки на геймпаде в зависимости от программного состояния
            if ( P.gamepad2.dpad_down ) { posState = 0; }
            if ( P.gamepad2.dpad_up ) { posState = 1; } // Жесткая перезапись этого состояния
            if ( P.gamepad2.b && state == 0 ) {
                if ( posState == 0 ) { state = 1; timeouter = System.currentTimeMillis(); }
                else { state = -1; timeouter = System.currentTimeMillis(); } // Подъем и опускание лифта
            }
            selfTick(); // Тик управления лифта по таймерам
            if ( state == 0 ) {
                if ( posState == 0 ) { L.setPower(clLiftHoldPw+(-P.gamepad2.left_stick_y*rotLiftSpeed)); }
                else { L.setPower((-P.gamepad2.left_stick_y*rotLiftSpeed)); } // Управление со стика + постоянная мощность при нахождении лифта внизу
            }
            L_L.setPower(-P.gamepad2.right_stick_y*forwLiftSpeed); // Управление рейкой со стика
        }
    }
}
