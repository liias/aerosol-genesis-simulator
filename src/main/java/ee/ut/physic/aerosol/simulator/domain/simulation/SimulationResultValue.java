package ee.ut.physic.aerosol.simulator.domain.simulation;

import javax.persistence.*;

@Entity
public class SimulationResultValue {
    private long id;
    private SimulationResult simulationResult;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMU_RESULT_VALUE_SEQ")
    @SequenceGenerator(name = "SIMU_RESULT_VALUE_SEQ", sequenceName="SIMU_RESULT_VALUE_SEQ", allocationSize=1)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    public SimulationResult getSimulationResult() {
        return simulationResult;
    }

    public void setSimulationResult(SimulationResult simulationResult) {
        this.simulationResult = simulationResult;
    }
}
