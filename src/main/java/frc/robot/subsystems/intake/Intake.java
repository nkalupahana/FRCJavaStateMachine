package frc.robot.subsystems.intake;

import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import frc.robot.Constants;
import frc.robot.subsystems.Subsystem;

public class Intake extends Subsystem<IntakeStates> {
    TalonFX pivot = new TalonFX(0);
    TalonFX wheels = new TalonFX(1);

    SingleJointedArmSim pivotSimModel = new SingleJointedArmSim(
        DCMotor.getKrakenX60(1), 
        Constants.Intake.GEARING, // gearing
        0.192383865, // MOI
        0.3, // arm length
        Units.degreesToRadians(0), // min angle -- hard stop 
        Units.degreesToRadians(180), // max angle -- floor
        false, 
        Units.degreesToRadians(0)
    );

    public Intake() {
        super("Intake", IntakeStates.OFF);

        // Configure onboard position PID for pivot
        var pid = new Slot0Configs();
        // TODO: tune PID
        // Other than optional logging,
        // THIS IS ALL YOU SHOULD MODIFY
        //pid.kP = ?;
        //pid.kI = ?;
        //pid.kD = ?;

        pivot.getConfigurator().apply(pid);

        var feedback = new FeedbackConfigs();
        feedback.SensorToMechanismRatio = Constants.Intake.GEARING;
        
        pivot.getConfigurator().apply(feedback);

        /// Configure triggers for state transitions in testing
        addTrigger(IntakeStates.OFF, IntakeStates.INTAKING, () -> true);
        // Intake -> Evaluate/Reject
        addTrigger(IntakeStates.INTAKING, IntakeStates.EVALUATE, () -> {
            return nearSetpoint() && getStateTime() < Constants.Intake.MAX_TIME_TO_SETPOINT;
        });
        
        addTrigger(IntakeStates.INTAKING, IntakeStates.REJECT, () -> {
            if (getStateTime() > Constants.Intake.MAX_TIME_TO_SETPOINT) {
                System.out.println("Intake reject: took too long to reach setpoint.");
                return true;
            };

            return false;
        });

        // Evalute -> Accept/Reject
        addTrigger(IntakeStates.EVALUATE, IntakeStates.ACCEPT, () -> getStateTime() > Constants.EVALUATION_TIME);
        addTrigger(IntakeStates.EVALUATE, IntakeStates.REJECT, () -> {
            if (!nearSetpoint()) {
                System.out.println("Intake reject: Left setpoint during evaluation.");
                return true;
            }
            return false;
        });
    }

    public boolean nearSetpoint() {
        return Math.abs(getState().getAngle() - Units.radiansToDegrees(pivotSimModel.getAngleRads())) < Constants.TEST_DELTA;
    }

    @Override
    protected void runState() {
        // Set pivot position
        PositionVoltage command = new PositionVoltage(Units.degreesToRotations(getState().getAngle())).withSlot(0);
        pivot.setControl(command);
        putSmartDashboard("Position (deg)", Units.rotationsToDegrees(pivot.getPosition().getValueAsDouble()));

        // Set wheel speed
        wheels.set(getState().getSpeed());
    }

    @Override
    protected void runSimulation() {
        var pivotSim = pivot.getSimState();

        pivotSimModel.setInputVoltage(pivotSim.getMotorVoltage());
        pivotSimModel.update(0.020);

        pivotSim.setRawRotorPosition(Units.radiansToRotations(pivotSimModel.getAngleRads()) * Constants.Intake.GEARING);
        pivotSim.setRotorVelocity(
            Units.radiansToRotations(pivotSimModel.getVelocityRadPerSec()) * Constants.Intake.GEARING
        );
    }
}
