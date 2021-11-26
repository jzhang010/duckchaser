package org.firstinspires.ftc.teamcode.operation;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystem.DriveTrainMecanum;
import org.firstinspires.ftc.teamcode.subsystem.EyeOpenCV;
import org.firstinspires.ftc.teamcode.subsystem.Waver;

@TeleOp(name="Auto Actions", group="operation")
//@Disabled
public class AutoActions extends OpMode
{
    private org.firstinspires.ftc.teamcode.subsystem.DriveTrainMecanum drive;
    private org.firstinspires.ftc.teamcode.subsystem.Waver waver;
    private org.firstinspires.ftc.teamcode.subsystem.EyeOpenCV eye;
    private int pos;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        drive = new org.firstinspires.ftc.teamcode.subsystem.DriveTrainMecanum(hardwareMap);
        waver = new org.firstinspires.ftc.teamcode.subsystem.Waver(hardwareMap);
        eye = new org.firstinspires.ftc.teamcode.subsystem.EyeOpenCV(0, 105, 205);
        eye.init(hardwareMap, telemetry);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        if (eye.getAnalysis() == org.firstinspires.ftc.teamcode.subsystem.EyeOpenCV.EyePipeline.DuckPosition.RIGHT) {
            waver.right();
            pos = 1;
        }
        else if (eye.getAnalysis() == org.firstinspires.ftc.teamcode.subsystem.EyeOpenCV.EyePipeline.DuckPosition.LEFT) {
            waver.left();
            pos = -1;
        }
        else {
            waver.center();
            pos = 0;
        }
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
