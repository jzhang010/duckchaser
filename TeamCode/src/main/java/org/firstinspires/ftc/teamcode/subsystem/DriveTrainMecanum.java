package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveTrainMecanum {
    private final DcMotorEx frontLeft;
    private final DcMotorEx backLeft;
    private final DcMotorEx frontRight;
    private final DcMotorEx backRight;

    private static final double TICKS_PER_MOTOR_REV     = 537.7; //Yellow Jacket motor on rev 320 gear box
    private static final double WHEEL_DIAMETER_INCHES   = 1.8898*2;
    public static final double TICKS_PER_INCH = TICKS_PER_MOTOR_REV / (WHEEL_DIAMETER_INCHES * Math.PI);

    private static final int POSITIONING_TOLERANCE = 30; //The amount of ticks the DriveByInch method should be allowed to deviate by

    public DriveTrainMecanum(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontleft");
        backLeft = hardwareMap.get(DcMotorEx.class, "backleft");
        backRight = hardwareMap.get(DcMotorEx.class, "backright");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontright");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
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

        drive(power, 0, 0);

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + targetPos);
        frontRight.setTargetPosition(frontLeft.getCurrentPosition() + targetPos);
        backLeft.setTargetPosition(frontLeft.getCurrentPosition() + targetPos);
        backRight.setTargetPosition(frontLeft.getCurrentPosition() + targetPos);

        frontLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        while (frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backLeft.isBusy()){}
        stop();
    }

    public void strafeByInch (double inches, double power){
        final int targetPos = (int)(inches * TICKS_PER_INCH);
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + targetPos);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - targetPos);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + targetPos);
        backRight.setTargetPosition(backRight.getCurrentPosition());

        frontLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        drive(0, power, 0);

        while (frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backLeft.isBusy()){}
        stop();
    }

}
