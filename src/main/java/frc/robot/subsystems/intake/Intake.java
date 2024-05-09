package frc.robot.subsystems.intake;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Subsystem;

public class Intake extends Subsystem<IntakeStates> {
    TalonFX pivot = new TalonFX(0);
    TalonFX wheels = new TalonFX(1);

    SingleJointedArmSim pivotSimModel = new SingleJointedArmSim(
        DCMotor.getKrakenX60(1), 
        67.5, // gearing
        0.192383865, // MOI
        0.3, // arm length
        Units.degreesToRadians(0), // min angle -- hard stop 
        Units.degreesToRadians(180), // max angle -- floor
        false, 
        Units.degreesToRadians(0)
    );

    PIDController pivotPID = new PIDController(10, 0, 0);

    public Intake() {
        super("Intake", IntakeStates.OFF);

        // Configure onboard position PID for pivot
        var pid = new Slot0Configs();
        pid.kP = 70;
        pid.kI = 0;
        pid.kD = 0;

        pivot.getConfigurator().apply(pid);
    }

    @Override
    protected void runState() {
        // Set pivot position
        //pivot.set(pivotPID.calculate(Units.radiansToRotations(pivotSimModel.getAngleRads()), Units.degreesToRotations(getState().getAngle())));
        PositionVoltage command = new PositionVoltage(Units.degreesToRotations(getState().getAngle())).withSlot(0);
        pivot.setControl(command);

        // Set wheel speed
        wheels.set(getState().getSpeed());

        putSmartDashboard("Position (deg)", Units.rotationsToDegrees(pivot.getPosition().getValueAsDouble()));
    }

    @Override
    protected void runSimulation() {
        var pivotSim = pivot.getSimState();

        pivotSimModel.setInputVoltage(pivotSim.getMotorVoltage());
        pivotSimModel.update(0.020);

        pivotSim.setRawRotorPosition(Units.radiansToRotations(pivotSimModel.getAngleRads()));
        pivotSim.setRotorVelocity(
            Units.radiansToRotations(pivotSimModel.getVelocityRadPerSec())
        );
    }
}
