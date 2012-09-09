package ee.ut.simulator.domain.simulation;

import com.sun.istack.internal.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SimulationResultValue {

    @Id
    private long id;

    @NotNull
    @ManyToOne
    private SimulationResult simulationResult;
}
