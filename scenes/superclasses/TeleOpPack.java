package org.firstinspires.ftc.teamcode.scenes.superclasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

abstract public class TeleOpPack extends LinearOpMode { // Абстрактный класс для быстрого развертывания телеопов
    public RobotPortal R;
    @Override
    public void runOpMode() throws InterruptedException {
        R = new RobotPortal(telemetry, this, hardwareMap, gamepad1, gamepad2); // Создание объекта класса RobotPortal
        doSetup(); // Единожды выполняет действия до начала OpMode
        waitForStart(); // Ждем начала OpMode
        doSetupMovings(); // Единожды делаем действия
        while (!isStopRequested()) {
            doActions(); // Делаем действие до конца OpMode
        }
    }
    public void doSetup() { // Функция для перезаписи для действий, произовдимых до начала OpMode

    }
    public void doSetupMovings() { // Функция для перезаписи для действий, производимых единожды после начала OpMode

    }
    abstract public void doActions(); // Абстрактная функция для действий, произовдимых постоянно до конца OpMode
}
