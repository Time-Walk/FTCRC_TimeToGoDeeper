package org.firstinspires.ftc.teamcode.func.classes;

import org.firstinspires.ftc.teamcode.func.classes.driverhelperv2.AngleSponsor;
import org.firstinspires.ftc.teamcode.func.classes.superclasses.Gettable;
import org.firstinspires.ftc.teamcode.modules.camera.concepts.CamLocalization;
import org.firstinspires.ftc.teamcode.modules.superclasses.RobotPortal;

public class CamLocalizationTicker extends Gettable {
    CamLocalization cl;
    RobotPortal R;
    AngleSponsor as;
    boolean isAs = false;
    public CamLocalizationTicker(CamLocalization cl, RobotPortal R) {
        this.cl = cl; this.R = R;
    }
    public void initWithAngleSponsor(AngleSponsor as) {
        this.as = as;
        isAs = true;
    }
    @Override
    public double[] getNewVals() {
        if ( isAs ) { cl.tick(as.RobotAngle); return new double[] {cl.absX, cl.absY}; }
        cl.tick(R.imuv2.getAngle());
        return new double[] {cl.absX, cl.absY};
    }
}
