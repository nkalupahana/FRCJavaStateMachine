package frc.robot.subsystems.intake;

import frc.robot.Constants;
import frc.robot.subsystems.SubsystemStates;

public enum IntakeStates implements SubsystemStates {
    OFF(
        "Off", 
        Constants.Intake.IN_ANGLE, 
        0
    ),
    INTAKING(
        "Intaking", 
        Constants.Intake.OUT_ANGLE, 
        Constants.Intake.INTAKE_SPEED
    ),
    EVALUATE(
        "Evaluating Results",
        Constants.Intake.OUT_ANGLE,
        Constants.Intake.INTAKE_SPEED
    ),
    ACCEPT(
        "Accepted",
        Constants.Intake.OUT_ANGLE, 
        Constants.Intake.INTAKE_SPEED
    ),
    REJECT(
        "Rejected",
        Constants.Intake.OUT_ANGLE, 
        Constants.Intake.INTAKE_SPEED
    );

    IntakeStates(String stateString, double angle, double speed) {
        this.stateString = stateString;
        this.angle = angle;
        this.speed = speed;
    }

    private String stateString;
    private double angle;
    private double speed;

    public String getStateString() {
        return stateString;
    }

    public double getAngle() {
        return angle;
    }

    public double getSpeed() {
        return speed;
    }
}
