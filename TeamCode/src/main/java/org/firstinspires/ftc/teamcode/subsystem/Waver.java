package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Waver {
    public Servo waver    = null;

    public Waver(HardwareMap hardwareMap) {
        waver = hardwareMap.get(Servo.class, "waver");

    }

    public void center() {
        waver.setPosition(0.65);
    }

    public void left() {
        waver.setPosition(0.52);
    }

    public void right() {
        waver.setPosition(0.76);
    }
}
