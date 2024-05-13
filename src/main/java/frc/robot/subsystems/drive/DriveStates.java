package frc.robot.subsystems.drive;

import frc.robot.subsystems.SubsystemStates;

public enum DriveStates implements SubsystemStates {
    OFF(
        "Off"
    ),
    STANDARD(
        "Standard Drive"
    ),
    EVALUATE(
        "Evaluating Results"
    ),
    ACCEPT(
        "Accepted"
    ),
    REJECT(
        "Rejected"
    );

    DriveStates(String stateString) {
        this.stateString = stateString;
    }

    private String stateString;

    public String getStateString() {
        return stateString;
    }
}
