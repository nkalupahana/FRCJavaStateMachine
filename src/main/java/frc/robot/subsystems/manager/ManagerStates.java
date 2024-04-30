package frc.robot.subsystems.manager;

import frc.robot.subsystems.SubsystemStates;
import frc.robot.subsystems.intake.IntakeStates;

public enum ManagerStates implements SubsystemStates {
    IDLE(
        "Idle",
        IntakeStates.OFF
    ),
    INTAKING(
        "Intaking",
        IntakeStates.INTAKING
    ),
    OUTTAKING(
        "Outtaking",
        IntakeStates.OUTTAKING
    );

    String stateString;
    IntakeStates intakeState;
    ManagerStates(String stateString, IntakeStates intakeState) {
        this.stateString = stateString;
        this.intakeState = intakeState;
    }

    @Override
    public String getStateString() {
        return stateString;
    }

    public IntakeStates getIntakeState() {
        return intakeState;
    }
}
