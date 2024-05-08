package frc.robot.subsystems.intake;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import frc.robot.subsystems.Subsystem;

public class Intake extends Subsystem<IntakeStates> {
    TalonFX pivot = new TalonFX(0);
    TalonFX wheels = new TalonFX(1);

    DCMotorSim pivotSimModel = new DCMotorSim(DCMotor.getFalcon500(1), 1, 0.005);

    public Intake() {
        super("Intake", IntakeStates.OFF);

        // Configure onboard position PID for pivot
        var pid = new Slot0Configs();
        pid.kP = 0.5;
        pid.kI = 0;
        pid.kD = 0.05;

        pivot.getConfigurator().apply(pid);
    }

    @Override
    protected void runState() {
        // Set pivot position
        PositionVoltage anglePositionRequest = new PositionVoltage(getState().getAngle()).withSlot(0);
        pivot.setControl(anglePositionRequest);

        // Set wheel speed
        wheels.set(getState().getSpeed());
    }

    @Override
    protected void runSimulation() {
        var pivotSim = pivot.getSimState();

        pivotSimModel.setInputVoltage(pivotSim.getMotorVoltage());
        pivotSimModel.update(0.020);

        pivotSim.setRawRotorPosition(pivotSimModel.getAngularPositionRotations());
        pivotSim.setRotorVelocity(
            Units.radiansToRotations(pivotSimModel.getAngularVelocityRadPerSec())
        );
    }
}
