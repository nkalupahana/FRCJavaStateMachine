package frc.robot.subsystems.manager;

import frc.robot.subsystems.Subsystem;
import frc.robot.subsystems.intake.Intake;
import frc.robot.Robot;

public class Manager extends Subsystem<ManagerStates> {
    Intake intake = new Intake();

    public Manager() {
        super("Manager", ManagerStates.IDLE);
        addTrigger(ManagerStates.IDLE, ManagerStates.INTAKING, () -> Robot.driverController.getAButtonPressed());
        addTrigger(ManagerStates.INTAKING, ManagerStates.IDLE, () -> Robot.driverController.getAButtonPressed());

        addTrigger(ManagerStates.IDLE, ManagerStates.OUTTAKING, () -> Robot.driverController.getBButtonPressed());
        addTrigger(ManagerStates.OUTTAKING, ManagerStates.IDLE, () -> getStateTime() > 2);
    }

    @Override
    protected void runState() {
        intake.setState(getState().getIntakeState());
        intake.periodic();
    }

    @Override
    protected void runSimulation() {
        return;
    }
}
