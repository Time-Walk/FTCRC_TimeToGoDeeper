package org.firstinspires.ftc.teamcode.modules;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.modules.superclasses.Module;

public class IMU extends Module {
    BNO055IMU imu;
    public void initModule() { //Инициализация:

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters(); // инициализация Акселерометра
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = P.hwmp.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
       // while (!imu.isGyroCalibrated()) { //Калибровка акселерометра
            delay(30);
            P.telemetry.addData(String.valueOf(imu.isAccelerometerCalibrated()), "ac");
            P.telemetry.addData(String.valueOf(imu.isGyroCalibrated()), "gc");
            P.telemetry.addData(String.valueOf(imu.isMagnetometerCalibrated()), "mc");
            P.telemetry.addData(String.valueOf(imu.isSystemCalibrated()), "sc");
            P.telemetry.addData(String.valueOf(imu.getCalibrationStatus()), "cls");
            P.telemetry.addData(String.valueOf(imu.getSystemStatus()), "ss");
            P.telemetry.addData(String.valueOf(imu.getSystemError()), "se");
            P.telemetry.addData(String.valueOf(imu.getParameters()), "sp");
            P.telemetry.addData("Wait", "Calibration"); //Сообщение о калибровке
            P.telemetry.update();
        }
        //telemetry.addData("Done!", "Calibrated"); //Сообщение об окончании калибровки
        //telemetry.update();

  //  }

    public double getAngle() { //Функция получения данных с акселерометра
        Orientation angles; // переменная в которой будет храниться угол поворота под акселерометр
        Acceleration gravity; // здесь хранится важная информация для акселерометра
        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        gravity  = imu.getGravity();
        return angles.firstAngle;
    }

}
