package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class Subsystem<StateType extends SubsystemStates> {
    private Map<StateType, ArrayList<Trigger<StateType>>> triggerMap = new HashMap<StateType, ArrayList<Trigger<StateType>>>();

    private StateType state = null;
    protected String subsystemName;
    protected Timer stateTimer = new Timer();

    public Subsystem(String subsystemName, StateType defaultState) {
        if (defaultState == null) {
            throw new RuntimeException("Default state cannot be null!");
        }
        this.subsystemName = subsystemName;
        this.state = defaultState;

        stateTimer.start();
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
        checkTriggers();
    }

    protected abstract void runState();

    protected void addTrigger(StateType startType, StateType endType, BooleanSupplier check) {
        triggerMap.get(startType).add(new Trigger<StateType>(check, endType));
    }

    private void checkTriggers() {
        List<Trigger<StateType>> triggers = triggerMap.get(state);
        for (var trigger : triggers) {
            if (trigger.isTriggered()) {
                setState(trigger.getResultState());
                return;
            }
        }
    }

    public StateType getState() {
        return state;
    }

    public void setState(StateType state) {
        this.state = state;
        stateTimer.reset();
    }
}
