package org.firstinspires.ftc.teamcode.scenes.superclasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.func.classes.DrivePD;
import org.firstinspires.ftc.teamcode.func.classes.RotatePD;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

abstract public class AutonomousPack extends LinearOpMode { // Абстрактных класс для быстрого создания автономов
    public RobotPortal R;
    public DrivePD drp;
    public RotatePD rpd;
    @Override
    public void runOpMode() throws InterruptedException { // Основная функция OpMod'а
        R = new RobotPortal(telemetry, this, hardwareMap, gamepad1, gamepad2);
        DrivePD drp = new DrivePD();
        drp.init(R, this);
        RotatePD rpd = new RotatePD();
        rpd.init(R, this); // Создания объектов необходимых классов
        doSetup(); // Функция, которая вызывается до начала автонома
        waitForStart(); // Ждем начало автонома
        doSetupMovings();
        doActions(); // Делаем необходимые действия
    }
    public void doSetup() {

    }
    public void doSetupMovings() {

    }
    abstract public void doActions();
}
