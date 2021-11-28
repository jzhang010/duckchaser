package org.firstinspires.ftc.teamcode.operation;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.subsystem.DriveTrainMecanum;
import org.firstinspires.ftc.teamcode.subsystem.EyeOpenCV;
import org.firstinspires.ftc.teamcode.subsystem.Waver;

@Autonomous(name="Auto Drive To Duck", group="operation")
//@Disabled
public class AutoDriveToDuck extends OpMode
{
    private org.firstinspires.ftc.teamcode.subsystem.DriveTrainMecanum drive;
    private org.firstinspires.ftc.teamcode.subsystem.Waver waver;
    private org.firstinspires.ftc.teamcode.subsystem.EyeOpenCV eye;
    private int pos;
    private boolean start = false;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        drive = new org.firstinspires.ftc.teamcode.subsystem.DriveTrainMecanum(hardwareMap);
        waver = new org.firstinspires.ftc.teamcode.subsystem.Waver(hardwareMap);
        eye = new org.firstinspires.ftc.teamcode.subsystem.EyeOpenCV(0, 140, 280, 320, 240);
        eye.init(hardwareMap, telemetry);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        start = false;
        if (eye.getAnalysis() == org.firstinspires.ftc.teamcode.subsystem.EyeOpenCV.DuckPosition.RIGHT) {
            waver.right();
            pos = 1;
        }
        else if (eye.getAnalysis() == org.firstinspires.ftc.teamcode.subsystem.EyeOpenCV.DuckPosition.LEFT) {
            waver.left();
            pos = -1;
        }
        else if (eye.getAnalysis() == org.firstinspires.ftc.teamcode.subsystem.EyeOpenCV.DuckPosition.CENTER) {
            waver.center();
            pos = 0;
        }
        else {
            // do nothing
        }
        eye.init_loop();
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        drive.forwardByInch(10, .5);
        if (pos == 1)
        {
            drive.strafeByInch(10, .5);
        }
        else if (pos == 2)
        {
            drive.strafeByInch(-10, .5);
        }
    }

    @Override
    public void loop() {

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
