package org.firstinspires.ftc.teamcode.operation;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.*;

@TeleOp(name="Duck Following", group="operation")
//@Disabled
public class DemoML extends OpMode
{
    private DriveTrainMecanum drive;
    private Waver waver;
    private EyeTensorFlow eye;
    private boolean start = false;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        drive = new DriveTrainMecanum(hardwareMap, telemetry);
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
        if (gamepad1.a) {
            if (eye.getPos() == 0) {
                drive.drive(.2, 0, .3);
            } else if (eye.getPos() == 1) {
                drive.drive(.2, 0, 0);
            } else if (eye.getPos() == 2) {
                drive.drive(.2, 0, -.3);
            } else {
                drive.stop();
            }
        }
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
