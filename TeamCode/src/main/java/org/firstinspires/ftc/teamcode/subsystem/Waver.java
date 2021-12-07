/*
 * This class defines the waver subsystem, which is a servo at the back of the robot
 * that acts like the tail of the robot.
 */
package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Waver {
    // Defines a waver and runtime object
    public Servo waver = null;
    private ElapsedTime runtime;

    // Initializes the waver and runtime object
    public Waver(HardwareMap hardwareMap)
    {
        waver = hardwareMap.get(Servo.class, "waver");
        runtime = new ElapsedTime();
    }

    // Sets the waver to the center position
    public void center()
    {
        waver.setPosition(0.65);
    }

    // Sets the waver to the left position
    public void left()
    {
        waver.setPosition(0.52);
    }

    // Sets the waver to the right position
    public void right()
    {
        waver.setPosition(0.76);
    }

    public void sleep(int second)
    {
        runtime.reset();
        while (runtime.seconds() < second) {}
    }

    // Waves the tail from left to right two times
    public void wave()
    {
        for (int i = 0; i < 2; i++)
        {
            left();
            sleep(1);
            right();
            sleep(1);
        }
    }
}
