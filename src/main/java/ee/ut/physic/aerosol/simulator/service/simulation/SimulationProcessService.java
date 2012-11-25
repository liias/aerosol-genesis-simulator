package ee.ut.physic.aerosol.simulator.service.simulation;

import ee.ut.physic.aerosol.simulator.domain.simulation.SimulationProcess;
import ee.ut.physic.aerosol.simulator.errors.GeneralException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface SimulationProcessService {
    void startInNewThread(SimulationProcess process);

    void setCanceled(SimulationProcess simulationProcess);

    void setFailed(SimulationProcess process);

    void setCompleted(SimulationProcess process);

    void stop();

    SimulationProcess getById(long id);

    Map<String, Map<String, String>> getParametersMapById(long id) throws GeneralException;

    String getBestFileContent(SimulationProcess process, double rating);

    Long getBestProcessId() throws GeneralException;

    boolean isFinishedProcessWithHashAlreadyExisting(String hash);

    void stopCurrentProcess();
}
