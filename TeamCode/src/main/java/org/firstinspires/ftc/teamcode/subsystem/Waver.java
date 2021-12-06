package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Waver {
    public Servo waver    = null;
    private ElapsedTime runtime;

    public Waver(HardwareMap hardwareMap) {
        waver = hardwareMap.get(Servo.class, "waver");
        runtime = new ElapsedTime();
    }

    public void center()
    {
        waver.setPosition(0.65);
    }

    public void left()
    {
        waver.setPosition(0.52);
    }

    public void right()
    {
        waver.setPosition(0.76);
    }

    public void sleep(int second)
    {
        runtime.reset();
        while (runtime.seconds() < second) {}
    }

    public void wave()
    {
        left();
        sleep(1);
        right();
        sleep(1);
    }
}
