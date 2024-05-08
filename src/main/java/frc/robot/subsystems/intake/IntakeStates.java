package frc.robot.subsystems.intake;

import frc.robot.Constants;
import frc.robot.subsystems.SubsystemStates;

public enum IntakeStates implements SubsystemStates {
    OFF(
        "Off", 
        Constants.Intake.IN_ROTS, 
        0
    ),
    INTAKING(
        "Intaking", 
        Constants.Intake.OUT_ROTS, 
        Constants.Intake.INTAKE_SPEED
    ),
    OUTTAKING(
        "Outtaking",
        Constants.Intake.OUT_ROTS,
        -Constants.Intake.INTAKE_SPEED
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
