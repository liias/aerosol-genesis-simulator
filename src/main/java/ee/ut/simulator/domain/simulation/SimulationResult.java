package ee.ut.simulator.domain.simulation;

import com.sun.istack.internal.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SimulationResult {

    @Id
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar completionDate;

    @NotNull
    @ManyToOne
    private SimulationProcess simulationProcess;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationResult")
    private Set<SimulationResultValue> resultValues = new HashSet<SimulationResultValue>();
}
