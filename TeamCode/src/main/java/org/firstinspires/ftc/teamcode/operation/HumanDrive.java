/*
 * This class is a program that allows a human to drive the robot using a gamepad
 */
package org.firstinspires.ftc.teamcode.operation;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.DriveTrainMecanum;
import org.firstinspires.ftc.teamcode.subsystem.Waver;

@TeleOp(name="Human Drive", group="operation")
public class HumanDrive extends OpMode
{
    DriveTrainMecanum drive;
    Waver waver;

    // Code that runs with the driver hits INIT
    @Override
    public void init()
    {
        telemetry.addData("Status", "Initialized");
        drive = new DriveTrainMecanum(hardwareMap, telemetry);
        waver = new Waver(hardwareMap);

    }

    // Code that runs repeatedly after the driver hits PLAY but before they hit STOP
    @Override
    public void loop()
    {
        // Drives the robot using gamepad inputs
        drive.humanControl(gamepad1);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Motors", "frontleft (%.2f)", drive.getFL());
        telemetry.addData("Motors", "backleft (%.2f)", drive.getBL());
        telemetry.addData("Motors", "frontright (%.2f)", drive.getFR());
        telemetry.addData("Motors", "backright (%.2f)", drive.getBR());

        // Waves the tail left, right, or center depending on gamepad inputs
        if (gamepad1.x)
        {
            waver.left();
        }
        else if (gamepad1.b)
        {
            waver.right();
        }
        else if (gamepad1.a)
        {
            waver.center();
        }
    }

}

