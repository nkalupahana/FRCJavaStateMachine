package frc.robot.subsystems.drive;

import frc.robot.subsystems.SubsystemStates;

public enum DriveStates implements SubsystemStates {
    STANDARD(
        "Standard Drive"
    );

    DriveStates(String stateString) {
        this.stateString = stateString;
    }

    private String stateString;

    public String getStateString() {
        return stateString;
    }
}
