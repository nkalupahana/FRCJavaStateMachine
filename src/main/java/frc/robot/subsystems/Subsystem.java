package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class Subsystem<StateType extends SubsystemStates> {
    protected StateType state = null;
    protected String subsystemName;

    public Subsystem(String subsystemName, StateType defaultState) {
        if (defaultState == null) {
            throw new RuntimeException("Default state cannot be null!");
        }
        this.subsystemName = subsystemName;
        this.state = defaultState;
    }

    protected void putSmartDashboard(String key, String value) {
        SmartDashboard.putString("[" + subsystemName + "] " + key, value);
    }

    protected void putSmartDashboard(String key, double value) {
        SmartDashboard.putNumber("[" + subsystemName + "] " + key, value);
    }

    public void periodic() {
        putSmartDashboard("State", state.getStateString());
        runState();
    }

    protected abstract void runState();
}
