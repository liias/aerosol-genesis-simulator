package ee.ut.physic.aerosol.simulator.domain.simulation;

import javax.persistence.*;

@Entity
public class SimulationResultValue {
    private long id;
    // value is not Float or smthing like that, because sometimes value = ?
    private String name, value;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


}
