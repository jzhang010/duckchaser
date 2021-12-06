package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriveTrainMecanum {
    // Defines 4 motors that make up the mecanum drive train
    private final DcMotorEx frontLeft;
    private final DcMotorEx backLeft;
    private final DcMotorEx frontRight;
    private final DcMotorEx backRight;

    // Calculates ticks per inch basic on robot constants
    private static final double TICKS_PER_MOTOR_REV = 537.7;
    private static final double WHEEL_DIAMETER_INCHES = 4;
    public static final double TICKS_PER_INCH = TICKS_PER_MOTOR_REV / (WHEEL_DIAMETER_INCHES * Math.PI);

    // The amount of ticks the autonomous driving methods are allowed to deviate by
    private static final int POSITIONING_TOLERANCE = 10;

    // Defines a telemetry object for logging and an runtime object to keep track of time
    private Telemetry telemetry;
    private ElapsedTime runtime;

    public DriveTrainMecanum(HardwareMap hardwareMap, Telemetry telemetry)
    {
        // Initializes motors based on where there are in the hardware map
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontleft");
        backLeft = hardwareMap.get(DcMotorEx.class, "backleft");
        backRight = hardwareMap.get(DcMotorEx.class, "backright");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontright");

        // Reverse the left side motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Sets all of the motors to have the positioning tolerance defined above
        frontLeft.setTargetPositionTolerance(POSITIONING_TOLERANCE);
        frontRight.setTargetPositionTolerance(POSITIONING_TOLERANCE);
        backLeft.setTargetPositionTolerance(POSITIONING_TOLERANCE);
        backRight.setTargetPositionTolerance(POSITIONING_TOLERANCE);

        // Initializes the telemetry and runtime object
        this.telemetry = telemetry;
        runtime = new ElapsedTime();
    }

    public void humanControl(Gamepad gamepad) {
        double forward = -gamepad.left_stick_y;
        double strafe = gamepad.left_stick_x;
        double turn = gamepad.right_stick_x;

        drive(forward, strafe, turn);
    }

    public double getFL() {return frontLeft.getPower();}
    public double getBL() {return backLeft.getPower();}
    public double getFR() {return frontRight.getPower();}
    public double getBR() {return backRight.getPower();}

    public void stop() {
        setDrivePowers(0.0, 0.0, 0.0, 0.0);
    }

    public void setDrivePowers(double fl, double bl, double br, double fr) {
        frontLeft.setPower(fl);
        backLeft.setPower(bl);
        backRight.setPower(br);
        frontRight.setPower(fr);
    }

    public void drive (double forward, double strafe, double turn)
    {
        double fl = forward + strafe + turn;
        double bl = forward - strafe + turn;
        double fr = forward - strafe - turn;
        double br = forward + strafe - turn;

        setDrivePowers(fl, bl, br, fr);
    }

    public void forwardByInch (double inches, double power)
    {
        int targetPos = (int)(inches * TICKS_PER_INCH);

        setDrivePowers(power, power, power, power);

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + targetPos);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + targetPos);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + targetPos);
        backRight.setTargetPosition(backRight.getCurrentPosition() + targetPos);

        setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        while (frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backLeft.isBusy()){
            positionReport();
        }
        stop();
    }

    public void strafeByInch (double inches, double power){
        final int targetPos = (int)(inches * TICKS_PER_INCH);
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - targetPos);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + targetPos);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + targetPos);
        backRight.setTargetPosition(backRight.getCurrentPosition() - targetPos);

        setDrivePowers(power, power, power, power);

        setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        while (frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backLeft.isBusy()){
            positionReport();
        }
        stop();
    }

    public void positionReport() {
        telemetry.addData("frontleft ", "%d %d", frontLeft.getTargetPosition(), frontLeft.getCurrentPosition());
        telemetry.addData("frontright ", "%d %d", frontRight.getTargetPosition(), frontRight.getCurrentPosition());
        telemetry.addData("backleft ", "%d %d", backLeft.getTargetPosition(), backLeft.getCurrentPosition());
        telemetry.addData("backright ", "%d %d", backRight.getTargetPosition(), backRight.getCurrentPosition());
        telemetry.update();
    }

    public void setMode(DcMotorEx.RunMode runMode) {
        frontLeft.setMode(runMode);
        frontRight.setMode(runMode);
        backLeft.setMode(runMode);
        backRight.setMode(runMode);
    }

    public void sleep(int second) {
        runtime.reset();
        while (runtime.seconds() < second) {}
    }

    public void autoInit() {
        setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        positionReport();
    }


}
