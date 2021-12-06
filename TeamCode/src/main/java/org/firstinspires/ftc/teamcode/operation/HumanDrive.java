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
    boolean waving = false;
    boolean prev = false;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        drive = new DriveTrainMecanum(hardwareMap, telemetry);
        waver = new Waver(hardwareMap);

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // drives the robot using gamepad inputs
        drive.humanControl(gamepad1);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Motors", "frontleft (%.2f)", drive.getFL());
        telemetry.addData("Motors", "backleft (%.2f)", drive.getBL());
        telemetry.addData("Motors", "frontright (%.2f)", drive.getFR());
        telemetry.addData("Motors", "backright (%.2f)", drive.getBR());

        //waves the tail
        if (gamepad1.a && !prev)
        {
            if (!waving)
            {
                waving = true;
            }
            else
            {
                waving = false;
            }
        }
        prev = gamepad1.a;

        if (waving)
        {
            waver.wave();
        }
        else
        {
            waver.center();
        }
    }

}

