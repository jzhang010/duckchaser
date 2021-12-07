/*
 * This class is an autonomous program that will drive the robot to the duck
 * based on camera vision input.
 */

package org.firstinspires.ftc.teamcode.operation;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystem.DriveTrainMecanum;
import org.firstinspires.ftc.teamcode.subsystem.EyeOpenCV;
import org.firstinspires.ftc.teamcode.subsystem.Waver;

@Autonomous(name="Auto Drive To Duck", group="operation")

public class AutoDriveToDuck extends LinearOpMode
{
    // Defines hardware subsystems objects that the program will be using
    private DriveTrainMecanum drive;
    private Waver waver;
    private EyeOpenCV eye;

    private int pos;

    // Code that runs upon initialization
    @Override
    public void runOpMode()
    {
        // Initializes the drivetrain according to the hardware map
        telemetry.addData("Status", "Initialized");
        drive = new org.firstinspires.ftc.teamcode.subsystem.DriveTrainMecanum(hardwareMap, telemetry);
        drive.autoInit();

        // Initializes a wave and eye, setting the position of the duck initially to be none
        waver = new org.firstinspires.ftc.teamcode.subsystem.Waver(hardwareMap);
        eye = new org.firstinspires.ftc.teamcode.subsystem.EyeOpenCV(0, 140, 280, 320, 240);
        eye.init(hardwareMap, telemetry);
        pos = -9999;

        // Code the runs repeated during the initialization state
        while (!isStarted())
        {
            // Changes the waver position to reflect where the duck is
            // Logs the current position of the duck
            if (eye.getAnalysis() == org.firstinspires.ftc.teamcode.subsystem.EyeOpenCV.DuckPosition.RIGHT)
            {
                waver.right();
                pos = 1;
                telemetry.addData("right ", "%d", pos);
            }
            else if (eye.getAnalysis() == org.firstinspires.ftc.teamcode.subsystem.EyeOpenCV.DuckPosition.LEFT)
            {
                waver.left();
                pos = -1;
                telemetry.addData("left ", "%d", pos);
            }
            else if (eye.getAnalysis() == org.firstinspires.ftc.teamcode.subsystem.EyeOpenCV.DuckPosition.CENTER)
            {
                waver.center();
                pos = 0;
                telemetry.addData("center ", "%d", pos);
            }
            else
            {
                pos = -9999;
                telemetry.addData("none ", "%d", pos);
            }
            eye.init_loop();
            telemetry.update();
        }

        // Stops the program at any time STOP is pressed
        if (isStopRequested())
        {
            return;
        }

        // ----- Code that runs when the play button is pressed ------

        // Drives the forward forward toward the duck by 12 inches
        drive.forwardByInch(12, .3);
        drive.sleep(1);

        // Drives the robot either left or right depending on the duck location
        if (pos == 1)
        {
            drive.strafeByInch(-6, .3); //right
        }
        else if (pos == -1)
        {
            drive.strafeByInch(6, .3); //left
        }

        // Stops the robot, updating the phone screen with the final position
        drive.stop();
        telemetry.addData("final pos ", "%d", pos);
        telemetry.update();

        // Waves the robot tail to signal the completion of the program!
        waver.wave();
        sleep(5000);
    }

}
