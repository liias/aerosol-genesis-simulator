package ee.ut.physic.aerosol.simulator.domain.simulation;

import ee.ut.physic.aerosol.simulator.util.ExcludeFromJson;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Entity
public class SimulationProcess {
    @ExcludeFromJson
    private long id;
    @ExcludeFromJson
    private SimulationOrder simulationOrder;
    private List<SimulationResult> simulationResults = new ArrayList<SimulationResult>();
    private List<SimulationProcessParameter> simulationProcessParameters = new ArrayList<SimulationProcessParameter>();
    private Calendar createdAt;
    private SimulationProcessState state = SimulationProcessState.NOT_STARTED;
    private Integer resultFileNumber;
    private String parametersHash;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    public SimulationOrder getSimulationOrder() {
        return simulationOrder;
    }

    public void setSimulationOrder(SimulationOrder simulationOrder) {
        this.simulationOrder = simulationOrder;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationProcess")
    public List<SimulationResult> getSimulationResults() {
        return simulationResults;
    }

    public void setSimulationResults(List<SimulationResult> simulationResults) {
        this.simulationResults = simulationResults;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationProcess")
    public List<SimulationProcessParameter> getSimulationProcessParameters() {
        return simulationProcessParameters;
    }

    public void setSimulationProcessParameters(List<SimulationProcessParameter> simulationProcessParameters) {
        this.simulationProcessParameters = simulationProcessParameters;
    }

    public void addParameter(SimulationProcessParameter parameter) {
        getSimulationProcessParameters().add(parameter);
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    public Calendar getCreatedAt() {
        if (createdAt == null) {
            createdAt = new GregorianCalendar();
        }
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    @Enumerated
    public SimulationProcessState getState() {
        return state;
    }

    public void setState(SimulationProcessState state) {
        this.state = state;
    }

    @Transient
    public void setResultFileNumber(Integer resultFileNumber) {
        this.resultFileNumber = resultFileNumber;
    }

    @Transient
    public Integer getResultFileNumber() {
        return resultFileNumber;
    }

    @Transient
    public Map<String, Map<String, String>> getParametersMap() {
        Map<String, Map<String, String>> parametersMap = new HashMap<String, Map<String, String>>(30);
        for (SimulationProcessParameter parameter : simulationProcessParameters) {
            String name = parameter.getName();
            Map<String, String> parameterValues = new HashMap<String, String>(2);
            parameterValues.put("freeAirMin", parameter.stringValue(parameter.getFreeAirValue()));
            parameterValues.put("freeAirMax", "");
            if (parameter.hasForest()) {
                parameterValues.put("forestMin", parameter.stringValue(parameter.getForestValue()));
                parameterValues.put("forestMax", "");
            }
            parametersMap.put(name, parameterValues);
        }
        return parametersMap;
    }

    public String getParametersHash() {
        return parametersHash;
    }

    public void setParametersHash(String parametersHash) {
        this.parametersHash = parametersHash;
    }

    @Transient
    public String generateParametersHash() {
        StringBuilder allValues = new StringBuilder();
        for (SimulationProcessParameter parameter : getSimulationProcessParameters()) {
            allValues.append(parameter.stringValue(parameter.getFreeAirValue()));
            if (parameter.hasForest()) {
                allValues.append(parameter.stringValue(parameter.getForestValue()));
            }
        }
        return encryptToMd5(allValues.toString());
    }

    @Transient
    private String encryptToMd5(String content) {
        byte[] defaultBytes = content.getBytes();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                hexString.append(Integer.toHexString(0xFF & aMessageDigest));
            }
            return hexString + "";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
