package org.firstinspires.ftc.teamcode.modules.superclasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;


    abstract public class Module { // Родитель для классов модулей робота
        public RobotPack P; // Пак, хранящий в себе LinearOpMode, Telemetry, HardwareMap и Gamepad'ы
        public void init(RobotPack P) {
            this.P = P; // Сохранение этого пака
            initModule(); // Вызов абстракного метода для инициализации модуля робота
        }
        abstract public void initModule();
        public void delay(long millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        public void advancedDelay(long millis, int nanos) {
            try {
                Thread.sleep(millis, nanos);
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

