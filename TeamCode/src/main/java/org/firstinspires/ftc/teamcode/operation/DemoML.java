package org.firstinspires.ftc.teamcode.operation;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.*;

@TeleOp(name="Demo ML", group="operation")
//@Disabled
public class DemoML extends OpMode
{
    private DriveTrainMecanum drive;
    private Waver waver;
    private EyeTensorFlow eye;
    private int pos;
    private boolean start = false;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        drive = new DriveTrainMecanum(hardwareMap);
        waver = new Waver(hardwareMap);
        eye = new EyeTensorFlow(hardwareMap, telemetry);
        eye.init();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        eye.init_loop();
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // Setup a variable for each drive wheel to save power level for telemetry
        drive.humanControl(gamepad1);


        // Show the elapsed game time and wheel power.
        telemetry.addData("Motors", "frontleft (%.2f)", drive.getFL());
        telemetry.addData("Motors", "backleft (%.2f)", drive.getBL());
        telemetry.addData("Motors", "frontright (%.2f)", drive.getFR());
        telemetry.addData("Motors", "backright (%.2f)", drive.getBR());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
