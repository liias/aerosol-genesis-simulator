package ee.ut.simulator.domain.simulation;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class SimulationResultValue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMULATION_RESULT_VALUE_SEQ")
    @SequenceGenerator(name = "SIMULATION_RESULT_VALUE_SEQ", sequenceName = "simulation_result_value_seq", allocationSize = 1)
    private long id;

    @NotNull
    @ManyToOne
    private SimulationResult simulationResult;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SimulationResult getSimulationResult() {
        return simulationResult;
    }

    public void setSimulationResult(SimulationResult simulationResult) {
        this.simulationResult = simulationResult;
    }
}
