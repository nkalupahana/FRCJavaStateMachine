package frc.robot.subsystems.manager;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.Subsystem;
import frc.robot.subsystems.intake.Intake;

public class Manager extends Subsystem<ManagerStates> {
    Intake intake = new Intake();
    XboxController controller = new XboxController(0);

    public Manager() {
        super("Manager", ManagerStates.IDLE);
        addTrigger(ManagerStates.IDLE, ManagerStates.INTAKING, () -> controller.getAButtonPressed());
        addTrigger(ManagerStates.INTAKING, ManagerStates.IDLE, () -> controller.getAButtonPressed());

        addTrigger(ManagerStates.IDLE, ManagerStates.OUTTAKING, () -> controller.getBButtonPressed());
        addTrigger(ManagerStates.OUTTAKING, ManagerStates.IDLE, () -> stateTimer.get() > 2);
    }

    @Override
    protected void runState() {
        intake.setState(getState().getIntakeState());
        intake.periodic();
    }
}
