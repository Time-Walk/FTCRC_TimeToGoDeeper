package org.firstinspires.ftc.teamcode.func.classes.driverhelperv2;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.modules.IMUv2;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

@Config
public class AngleSponsor {
    public static double willUpAngle = 0;
    public RobotPortal R;
    IMUv2 imu;
    public double RobotAngle;
    public double upAngle = 0;
    public AngleSponsor(RobotPortal R) {
        this.R = R;
        imu = R.imuv2;
        upAngle = willUpAngle;
    }
    public void tick() {
        if ( R.P.gamepad1.start ) { upAngle = imu.getAngle(); }
        double angle = imu.getAngle() + upAngle;
        if ( angle > 180 ) {
            angle = - ( 180 - ( angle - 180 ));
        }
        else if ( angle <= -180) {
            angle = 180 - ( angle + 180 );
        }
        RobotAngle = angle;
    }
}
