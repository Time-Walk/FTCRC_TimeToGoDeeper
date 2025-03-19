package org.firstinspires.ftc.teamcode.scenes.auto.test;

import static org.firstinspires.ftc.teamcode.modules.camera.pipelines.AprilTagDetectionPipeline.cx;
import static org.firstinspires.ftc.teamcode.modules.camera.pipelines.AprilTagDetectionPipeline.cy;
import static org.firstinspires.ftc.teamcode.modules.camera.pipelines.AprilTagDetectionPipeline.fx;
import static org.firstinspires.ftc.teamcode.modules.camera.pipelines.AprilTagDetectionPipeline.fy;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.modules.camera.concepts.CalibrationForCamLocalization;
import org.firstinspires.ftc.teamcode.modules.camera.concepts.CamLocalization;
import org.firstinspires.ftc.teamcode.modules.camera.pipelines.AprilTagDetectionPipeline;
import org.firstinspires.ftc.teamcode.scenes.superclasses.AutonomousPack;
import org.opencv.core.Point;

@Autonomous(name="ааааааааааааааа крокадилы бегемоты")
public class CameraCalibration extends AutonomousPack {
    public double[] pic2rTrapezoid(double alpha, double beta, double x, double y, double h_r) {
        /*
        Подсчет расстояния в см по двум осям до точки на стенке, получаемую из точки соприкасновения воображаемого луча,
        пущенного из оптического центра камеры в пиксель (x, y) и точки на стене, находящиеся на данной высоте.
        alpha - угол поворота камеры
        beta - угол наклона камеры
        x - координата пикселя x
        y - координата пикселя y
        h_r - высота оптического центра камеры
        tag_id - id найденного AprilTag
         */
        double h_at = CalibrationForCamLocalization.hat;

        double y_s_shapochkoy = ( h_r - h_at ) * ( Math.tan( (Math.PI/2) - Math.atan((y-cy)/fy) - beta ) );
        double x_s_shapochkoy = ( y_s_shapochkoy * (x-cx) ) / fx;

        double x_x0 = y_s_shapochkoy * Math.sin(alpha);
        double y_x0 = y_s_shapochkoy * Math.cos(alpha);

        double x_y0 = x_s_shapochkoy * Math.sin(alpha+(Math.PI/2));
        double y_y0 = x_s_shapochkoy * Math.cos(alpha+(Math.PI/2));

        return new double[] {x_x0+x_y0, y_x0+y_y0};
    }
    @Override
    public void doSetup() {
        AprilTagDetectionPipeline pipe = new AprilTagDetectionPipeline(R.P);
        CalibrationForCamLocalization calib = new CalibrationForCamLocalization(R.P);
        R.cam.openWithPipeline(pipe, true);
        while ( !isStopRequested() ) {
            if ( pipe.isAprilTagDetected ) {
                telemetry.addData("found april tag", pipe.detection.id);
                Point p1 = pipe.detection.corners[0];
                Point p2 = pipe.detection.corners[1];
                double beta = calib.calibrateBeta((p1.y+p2.y)/2);
                double[] xysold = pic2rTrapezoid(0, Math.toRadians(CamLocalization.beta), (p1.x+p2.x)/2, (p1.y+p2.y)/2, CalibrationForCamLocalization.hr);
                double[] xynew = pic2rTrapezoid(0, Math.toRadians(beta), (p1.x+p2.x)/2, (p1.y+p2.y)/2, CalibrationForCamLocalization.hr);
                telemetry.addData("beta", beta);
                telemetry.addData("old y", xysold[1]);
                telemetry.addData("old x", xysold[0]);
                telemetry.addData("new y", xynew[1]);
                telemetry.addData("new x", xynew[0]);
                telemetry.update();
            }
        }
    }
    @Override
    public void doActions() {

    }
}
