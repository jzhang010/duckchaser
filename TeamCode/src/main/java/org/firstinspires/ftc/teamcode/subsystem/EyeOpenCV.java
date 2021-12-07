/*
 * This class defines the camera subsystem or "eye" of the robot, which use Open CV to find the duck.
 * Library used is from https://github.com/OpenFTC/EasyOpenCV
 * This class is largely written by looking at the sample code included in the library.
 */

package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class EyeOpenCV {
    // Defines camera and pipeline objects
    protected OpenCvCamera camera;
    private EyePipeline eyePipeline;

    private boolean open = false;

    // X-coordinates of where the duck should be
    private int leftDuckX;
    private int centerDuckX;
    private int rightDuckX;

    // Camera attributes
    private int streamWidth;
    private int streamHeight;
    Telemetry telemetry;

    // Constructor, initializes duck location and camera attributes based on parameters
    public EyeOpenCV(int leftDuckX, int centerDuckX, int rightDuckX, int streamWidth, int streamHeight)
    {
        this.leftDuckX = leftDuckX;
        this.centerDuckX = centerDuckX;
        this.rightDuckX = rightDuckX;
        this.streamWidth = streamWidth;
        this.streamHeight = streamHeight;
    }

    // Initializes the camera and image pipeline and starts streaming the camera
    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"), cameraMonitorViewId);
        eyePipeline = new EyePipeline(leftDuckX, centerDuckX, rightDuckX);
        camera.setPipeline(eyePipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(streamWidth, streamHeight, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {
                telemetry.log().add(" camera acquisition error: " + errorCode);
            }
        });
        open = true;
    }

    // Logs the current values of each pipeline and the minimum value
    public void init_loop()
    {
        telemetry.addData("avg", "%d %d %d", eyePipeline.avg1, eyePipeline.avg2, eyePipeline.avg3);
        telemetry.addData("minVal", Math.min(Math.min(eyePipeline.avg1, eyePipeline.avg2), eyePipeline.avg3));
    }

    // Defines the possible positions the duck could be at
    public enum DuckPosition
    {
        LEFT,
        CENTER,
        RIGHT,
        NONE
    }

    // Returns the current position of the duck
    public DuckPosition getAnalysis()
    {
        return eyePipeline.getAnalysis();
    }

}

