package frc.robot.subsystems.intake;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.subsystems.Subsystem;

public class Intake extends Subsystem<IntakeStates> {
    TalonFX pivot = new TalonFX(0);
    TalonFX wheels = new TalonFX(1);

    public Intake() {
        super("Intake", IntakeStates.OFF);

        // Configure onboard position PID for pivot
        var pid = new Slot0Configs();
        pid.kP = 1;
        pid.kI = 0;
        pid.kD = 0.1;

        pivot.getConfigurator().apply(pid);
    }

    @Override
    public void runState() {
        // Set pivot position
        PositionVoltage anglePositionRequest = new PositionVoltage(0).withSlot(0);
        pivot.setControl(anglePositionRequest.withPosition(state.getAngle()));

        // Set wheel speed
        wheels.set(state.getSpeed());
    }
}
