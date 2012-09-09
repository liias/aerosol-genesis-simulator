package ee.ut.simulator.domain.simulation;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SIMULATION_RESULT_VALUE")
public class SimulationResultValue {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SIMULATION_RESULT_VALUE_SEQ")
    @SequenceGenerator(name = "SIMULATION_RESULT_VALUE_SEQ", sequenceName="simulation_result_value_seq", allocationSize=1)
    private long id;

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
