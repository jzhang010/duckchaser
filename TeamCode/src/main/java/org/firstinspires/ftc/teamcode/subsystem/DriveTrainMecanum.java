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
        double drive = -gamepad.left_stick_y;
        double strafe = gamepad.left_stick_x;
        double turn = gamepad.right_stick_x;

        double fl = drive + strafe + turn;
        double bl = drive - strafe + turn;
        double fr = drive - strafe - turn;
        double br = drive + strafe - turn;

        setDrivePowers(fl, bl, br, fr);

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

}
