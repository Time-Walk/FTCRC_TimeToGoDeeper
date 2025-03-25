package org.firstinspires.ftc.teamcode.modules.camera.concepts;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.func.Alliance;
import org.firstinspires.ftc.teamcode.modules.camera.basic.CameraOut;
import org.firstinspires.ftc.teamcode.modules.camera.pipelines.AprilTagDetectionPipeline;
import org.opencv.core.Mat;
import org.opencv.core.Point;

@Config
public class CamLocalization { // Локализация по камере
    public CameraOut cam;
    public AprilTagDetectionPipeline pipe;

    public double absX = 0;
    public double absY = 0;

    public static double beta = .5;
    public static double height = 27.5;

    public static double r_x_cam_0 = 5.5;
    public static double r_y_cam_0 = 22.5;
    public static double r_x_cam_1 = -14.5;
    public static double r_y_cam_1 = 9.5;
    public static double r_x_cam__1 = 15.5;
    public static double r_y_cam__1 = 1;
    public static double P = 60;

    public static double hid11 = 9.1;
    public static double hid12 = 9.1;
    public static double hid13 = 9.1;
    public static double hid14 = 9.1;
    public static double hid15 = 9.1;
    public static double hid16 = 9.1;
    double[] hids;

    double fx;
    double fy;
    double cx;
    double cy;

    public int alliance;

    public CamLocalization(CameraOut cam, boolean isStreamToDash, int alliance) { // Конструктор класса, принимает камеру, булеву нужно ли стримить в FtcDashboard и цвет альянса
        this.cam = cam;
        pipe = new AprilTagDetectionPipeline(cam.P);
        cam.openWithPipeline(pipe, isStreamToDash); // Открывает камеру с Pipeline на обнаружение AprilTag
        this.alliance = alliance;
        hids = new double[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, hid11, hid12, hid13, hid14, hid15, hid16};
        fx = AprilTagDetectionPipeline.fx;
        fy = AprilTagDetectionPipeline.fy;
        cx = AprilTagDetectionPipeline.cx;
        cy = AprilTagDetectionPipeline.cy; // Сохраняет цвет альянса, высоты AprilTag и параметры камеры
    }

    public double[] pic2r(double alpha, double beta, double x, double y, double h) {
        /*
        Подсчет расстояния в см по двум осям до точки на земле, получаемую из точки соприкасновения воображаемого луча,
        пущенного из оптического центра камеры в пиксель (x, y) и земли.
        alpha - угол поворота камеры
        beta - угол наклона камеры
        x - координата пикселя x
        y - координата пикселя y
        h - высота оптического центра камеры
         */
        double fx = AprilTagDetectionPipeline.fx;
        double fy = AprilTagDetectionPipeline.fy;
        double cx = AprilTagDetectionPipeline.cx;
        double cy = AprilTagDetectionPipeline.cy;

        double y_s_shapochkoy = h * ( 1 / Math.tan( Math.atan( (y-cy) / fy ) + beta ));
        double x_s_shapochkoy = ( y_s_shapochkoy * (x-cx) ) / fx;

        double x_x0 = y_s_shapochkoy * Math.sin(alpha);
        double y_x0 = y_s_shapochkoy * Math.cos(alpha);

        double x_y0 = x_s_shapochkoy * Math.sin(alpha+(Math.PI/2));
        double y_y0 = x_s_shapochkoy * Math.cos(alpha+(Math.PI/2));

        return new double[] {x_x0+x_y0, y_x0+y_y0};
    }

    public double[] pic2rTrapezoid(double alpha, double beta, double x, double y, double h_r, int tag_id) {
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
        double h_at = hids[tag_id]; // Нахождение высоты AprilTag по id

        double y_s_shapochkoy = ( h_r - h_at ) * ( Math.tan( (Math.PI/2) - Math.atan((y-cy)/fy) - beta ) );
        double x_s_shapochkoy = ( y_s_shapochkoy * (x-cx) ) / fx;

        double x_x0 = y_s_shapochkoy * Math.sin(alpha);
        double y_x0 = y_s_shapochkoy * Math.cos(alpha);

        double x_y0 = x_s_shapochkoy * Math.sin(alpha+(Math.PI/2));
        double y_y0 = x_s_shapochkoy * Math.cos(alpha+(Math.PI/2));

        return new double[] {x_x0+x_y0, y_x0+y_y0};
    }

    public double[] planB(double alpha, double x0, double x1, double y0, double y1) {
        double pic2pic = Math.sqrt(((x1-x0)*(x1-x0)) + ((y1-y0)*(y1-y0)));
        double toCmKoef = 10.16 / pic2pic;
        double midle = (x1+x0)/2;
        double distmid = midle - cx;
        double x_s_shapochkoy = distmid * toCmKoef;
        double that_angle = Math.atan((distmid) / fx);
        double y_s_shapochkoy = x_s_shapochkoy / Math.tan(that_angle);

        double x_x0 = y_s_shapochkoy * Math.sin(alpha);
        double y_x0 = y_s_shapochkoy * Math.cos(alpha);

        double x_y0 = x_s_shapochkoy * Math.sin(alpha+(Math.PI/2));
        double y_y0 = x_s_shapochkoy * Math.cos(alpha+(Math.PI/2));

        return new double[] {x_x0+x_y0, y_x0+y_y0};
    }

    public double[] planC() {
        return new double[] {pipe.detection.pose.x, pipe.detection.pose.y};
    }

    double[] camToCenterOfRobot(double alpha, double x_cam, double y_cam, double rx, double ry) {
        /*
        Перевод из относительных координат от AprilTag до оптического центра камеры, в относительные
        координаты от AprilTag до центра робота.
        alpha - угол поворота камеры
        x_cam - расстояние от камеры до центра робота по оси X
        y_cam - расстояние от камеры до центра робота по оси Y
        rx - расстояние от AprilTag до камеры по оси X
        ry - расстояние от AprilTag до камеры по оси Y
         */
        double x_x0 = ry * Math.sin(alpha);
        double y_x0 = ry * Math.cos(alpha);

        double x_y0 = rx * Math.sin(alpha+(Math.PI/2));
        double y_y0 = rx * Math.cos(alpha+(Math.PI/2));

        return new double[] {x_cam+x_x0+x_y0, y_cam+y_x0+y_y0};
    }

    double[] relativeToAbsolute(int aprilTagId, double x_, double y_) {
        /*
        Перевод из относительных координат от AprilTag до центра робота в абсолютные
        координаты от левого нижнего угла поля до центра робота.
        aprilTagId - id обнаруженного AprilTag
        x_ - расстояние от AprilTag до центра робота по оси X
        y_ - расстояние от AprilTag до центра робота по оси Y
         */
        switch ( aprilTagId ) { // Перевод. P = длина плитки поля в см
            case 11:
                return new double[] {P + x_, y_};
            case 12:
                return new double[] {y_, -x_ + (3*P)};
            case 13:
                return new double[] {-x_ + P, (6*P) - y_};
            case 14:
                return new double[] {-x_ + (5*P), (6*P) - y_};
            case 15:
                return new double[] {(6*P) - y_, (3*P) + x_};
            case 16:
                return new double[] {(5*P) + x_, y_};
        }
        return new double[] {0, 0};
    }

    public void tick(double robotAngle) { // Синхронный тик
        if ( !pipe.isAprilTagDetected ) { return; } // Если ни один AprilTag еще не был обнаружен
        double alpha = robotAngle;
        if ( alliance == Alliance.BLUE ) { // Если мы синий альянс
            switch ( pipe.detection.id ){ // Изменение угла поворота камеры взависимости от обнаруженного AprilTag
                case 13:
                case 14: // Если мы видим 13 или 14 AprilTag
                    alpha += 90;
                    break;
                case 11:
                case 16: // Если мы видим 11 или 16 AprilTag
                    alpha -= 90;
                    break;
                case 12:
                    if ( robotAngle > 0 ) { // 12 AprilTag расположен сзади от робота, поэтому нужно проверить ошибку 180 градусов
                        alpha -= 180;
                    }
                    else {
                        alpha += 180;
                    }
                    break;
            }
        }
        else { // Если мы красный альянс
            switch ( pipe.detection.id ) { // Изменение угла поворота камеры взависимости от обнаруженного AprilTag
                case 11:
                case 16: // Если мы видим 11 или 16 AprilTag
                    alpha += 90;
                    break;
                case 13:
                case 14: // Если мы видим 13 или 14 AprilTag
                    alpha -= 90;
                    break;
                case 15:
                    if ( robotAngle > 0 ) { // 15 AprilTag расположен сзади от робота, поэтому нужно проверить ошибку 180 градусов
                        alpha -= 180;
                    }
                    else {
                        alpha += 180;
                    }
                    break;
            }
        }
        double rx = r_x_cam_1;
        double ry = r_y_cam_1;
        switch ( cam.getRotationState() ) { // Делаем поправку угла поворота камеры если повернут сервомотор, на котором установлена камера, и сохраняем расстояние от камеры до центра робота
            case -1:
                //alpha -= 90;
                if ( alpha > 0 ) { alpha -= 180; }
                else { alpha += 180; }
                rx = r_x_cam__1;
                ry = r_y_cam__1;
                break;
            case 0:
                alpha -= 90;
                rx = r_x_cam_0;
                ry = r_y_cam_0;
        }
        if ( alpha > 180 ) {
            alpha = - ( 180 - ( alpha - 180 ));
        }
        else if ( alpha <= -180) {
            alpha = 180 - ( alpha + 180 );
        }
        cam.P.telemetry.addData("rot", robotAngle);
        cam.P.telemetry.addData("alpha", alpha);
        Point pleft = pipe.detection.corners[0]; // Нижняя левая точка AprilTag
        Point pright = pipe.detection.corners[1]; // Нижняя правая точка AprilTag
        //double[] cam_xy = pic2rTrapezoid(Math.toRadians(alpha), Math.toRadians(beta), (pleft.x+pright.x)/2, (pleft.y+pright.y)/2, height, pipe.detection.id);
        double[] cam_xy = planB(Math.toRadians(alpha), pleft.x, pright.x, pleft.y, pright.y);
        cam.P.telemetry.addData("x", cam_xy[0]);
        cam.P.telemetry.addData("y", cam_xy[1]);
        double[] relative_xy = camToCenterOfRobot(Math.toRadians(alpha), cam_xy[0], cam_xy[1], rx, ry);
        cam.P.telemetry.addData("rel x", relative_xy[0]);
        cam.P.telemetry.addData("rel y", relative_xy[1]);
        double[] abs_xy = relativeToAbsolute(pipe.detection.id, relative_xy[0], relative_xy[1]);
        cam.P.telemetry.addData("abs x", abs_xy[0]);
        cam.P.telemetry.addData("abs y", abs_xy[1]);
        cam.P.telemetry.update();
        absX = abs_xy[0];
        absY = abs_xy[1];
    }
}
