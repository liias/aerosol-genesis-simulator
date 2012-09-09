package ee.ut.simulator.domain.simulation;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SimulationProcess {

    @Id
    private long id;

    @NotNull
    @ManyToOne
    private SimulationResult simulationOrder;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationProcess")
    private Set<SimulationResult> results = new HashSet<SimulationResult>();
}
