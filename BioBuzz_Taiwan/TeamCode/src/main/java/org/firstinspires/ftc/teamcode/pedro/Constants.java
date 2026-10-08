package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("mFL");
        c.frontRightName.set("mFR");
        c.backLeftName.set("mBL");
        c.backRightName.set("mBR");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });
    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(2.199251069797306);
        c.yPodOffset.set(2.2559494859590306);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.18197518836727702);
                Controller secondaryTranslationalForward = Controller.proportional(0.06723496963512235);
                Controller primaryTranslationalLateral = Controller.proportional(0.22837178929456745);
                Controller secondaryTranslationalLateral = Controller.proportional(0.08437727393774677);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.011688661862944589));
                c.brake.set(Controller.proportionalFeedforward(0.0099353625835029));

                c.headingFeedback.set(Controller.proportional(2.4547308542549846));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.05004891388349354, 0.0028527489723293113));

                c.linearBrakeCoefficients.set(Matrix.diag(0.06292559942945758, 0.0640687549440471));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0013265443512698349, 0.0012352831566973435));

                c.maxAchievableForwardVelocity.set(86.53829214180698);
                c.maxAchievableStrafeVelocity.set(74.74563908832855);
                c.naturalForwardDeceleration.set(35.342204457441674);
                c.naturalStrafeDeceleration.set(50.60438786801798);
            }
    );

}