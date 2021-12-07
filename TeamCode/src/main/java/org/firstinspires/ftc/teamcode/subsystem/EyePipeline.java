/*
 * This class defines the Open CV pipeline used to determine where the duck is
 * Library used is from https://github.com/OpenFTC/EasyOpenCV
 * This class is largely written by looking at the sample code included in the library.
 * The general logic is that the code looks at values in 3 predetermined regions in the camera
 * images, which correspond to the left, center, or right of the robot. The values are used determine
 * if the duck is in any of those regions, which will inform which position the duck is at.
 */

package org.firstinspires.ftc.teamcode.subsystem;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class EyePipeline extends OpenCvPipeline {
    // Color constants
    static final Scalar RED = new Scalar(255,0,0);
    static final Scalar BLUE = new Scalar(0, 0, 255);
    static final Scalar GREEN = new Scalar(0, 255, 0);

    // The core values which define the location and size of the sample regions
    Point REGION1_TOPLEFT_ANCHOR_POINT;
    Point REGION2_TOPLEFT_ANCHOR_POINT;
    Point REGION3_TOPLEFT_ANCHOR_POINT;
    int REGION_WIDTH;
    int REGION_HEIGHT;

    /*
     * Points which actually define the sample region rectangles, derived from above values
     *
     * Example of how points A and B work to define a rectangle
     *
     *   ------------------------------------
     *   | (0,0) Point A                    |
     *   |                                  |
     *   |                                  |
     *   |                                  |
     *   |                                  |
     *   |                                  |
     *   |                                  |
     *   |                  Point B (70,50) |
     *   ------------------------------------
     *
     */
    Point region1_pointA;
    Point region1_pointB;
    Point region2_pointA;
    Point region2_pointB;
    Point region3_pointA;
    Point region3_pointB;

    // Working variables
    Mat region1_Cb, region2_Cb, region3_Cb;
    Mat YCrCb = new Mat();
    Mat Cb = new Mat();
    int avg1, avg2, avg3;

    // Sets initial duck position to be NONE
    private volatile EyeOpenCV.DuckPosition position = EyeOpenCV.DuckPosition.NONE;

    public EyePipeline(int leftDuckX, int centerDuckX, int rightDuckX) {
        // Initializes anchor points for each region and the region size
        REGION1_TOPLEFT_ANCHOR_POINT = new Point(leftDuckX,100);
        REGION2_TOPLEFT_ANCHOR_POINT = new Point(centerDuckX,100);
        REGION3_TOPLEFT_ANCHOR_POINT = new Point(rightDuckX,100);
        REGION_WIDTH = 40;
        REGION_HEIGHT = 40;

        // Initializes opposite corner points of each region based on their anchor points
        region1_pointA = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x,
                REGION1_TOPLEFT_ANCHOR_POINT.y);
        region1_pointB = new Point(
                REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
        region2_pointA = new Point(
                REGION2_TOPLEFT_ANCHOR_POINT.x,
                REGION2_TOPLEFT_ANCHOR_POINT.y);
        region2_pointB = new Point(
                REGION2_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION2_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
        region3_pointA = new Point(
                REGION3_TOPLEFT_ANCHOR_POINT.x,
                REGION3_TOPLEFT_ANCHOR_POINT.y);
        region3_pointB = new Point(
                REGION3_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
                REGION3_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
    }

    // This function takes the RGB frame, converts to YCrCb, and extracts the Cb channel to the 'Cb' variable
    // Y is the luma component and CB and CR are the blue-difference and red-difference chroma components
    void inputToCb(Mat input)
    {
        Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(YCrCb, Cb, 2);
    }

    // Initializes the first frame in the pipeline
    @Override
    public void init(Mat firstFrame) {
        inputToCb(firstFrame);

        region1_Cb = Cb.submat(new Rect(region1_pointA, region1_pointB));
        region2_Cb = Cb.submat(new Rect(region2_pointA, region2_pointB));
        region3_Cb = Cb.submat(new Rect(region3_pointA, region3_pointB));
    }

    // Proccesses each frame in the pipeline
    @Override
    public Mat processFrame(Mat input) {
        // Get the Cb channel of the input frame after conversion to YCrCb
        inputToCb(input);

        // Compute the average pixel value of each region
        avg1 = (int) Core.mean(region1_Cb).val[0];
        avg2 = (int) Core.mean(region2_Cb).val[0];
        avg3 = (int) Core.mean(region3_Cb).val[0];

        // Draw a rectangle showing the sample regions on the screen as a visual aid
        Imgproc.rectangle(input, region1_pointA, region1_pointB, BLUE, 2);
        Imgproc.rectangle(input, region2_pointA, region2_pointB, BLUE, 2);
        Imgproc.rectangle(input, region3_pointA, region3_pointB, BLUE, 2);

        // Find the min of the 3 averages
        int min = Math.min(Math.min(avg1, avg2), avg3);

        // If the min is above a certain threshold, there is probably no duck in the vision of the robot
        if (min >= 110 )
        {
            position = EyeOpenCV.DuckPosition.NONE;
        }
        // Records where the duck is based on the min value
        // Draws a red solid rectangle on top of the chosen region, green rectangle if confident
        else if (min == avg1)
        {
            position = EyeOpenCV.DuckPosition.LEFT;
            Imgproc.rectangle(input, region1_pointA, region1_pointB, min < 100 ? GREEN : RED, -1);
        }
        else if (min == avg2)
        {
            position = EyeOpenCV.DuckPosition.CENTER;
            Imgproc.rectangle(input, region2_pointA, region2_pointB, min < 100 ? GREEN : RED, -1);
        }
        else if (min == avg3)
        {
            position = EyeOpenCV.DuckPosition.RIGHT;
            Imgproc.rectangle(input, region3_pointA, region3_pointB, min < 100 ? GREEN : RED, -1);
        }
        Imgproc.putText(input, Integer.toString(avg1), region1_pointB, Imgproc.FONT_HERSHEY_DUPLEX, 0.75, avg1 < 100 ? GREEN : RED);
        Imgproc.putText(input, Integer.toString(avg2), region2_pointB, Imgproc.FONT_HERSHEY_DUPLEX, 0.75, avg2 < 100 ? GREEN : RED);
        Imgproc.putText(input, Integer.toString(avg3), region3_pointB, Imgproc.FONT_HERSHEY_DUPLEX, 0.75, avg3 < 100 ? GREEN : RED);
        Imgproc.putText(input, "Detection: "+position, new Point(0,25), Imgproc.FONT_HERSHEY_DUPLEX, 0.75, min < 100 ? GREEN : RED);

        return input;
    }

    // Returns current position of the duck
    public EyeOpenCV.DuckPosition getAnalysis()
    {
        return position;
    }
}
