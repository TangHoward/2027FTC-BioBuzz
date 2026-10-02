package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "可以讓第0顆馬達動的程式")
public class Teach extends OpMode {
    private DcMotorEx m0;
    private double m0_power =0.5;

    @Override
    public void init_loop() {
        super.init_loop();
    }

    @Override
    public void init() {
        m0 = hardwareMap.get(DcMotorEx.class,"m0");
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        if(gamepad1.dpadUpWasPressed()){
            m0_power += 0.1;
        } else if (gamepad1.dpadDownWasPressed()) {
            m0_power -= 0.1;
        }
        m0.setPower(m0_power);
        telemetry.addData("變數數值",m0_power);
        telemetry.addData("第0顆馬達的力量",m0.getPower());
        telemetry.update();
    }
}
