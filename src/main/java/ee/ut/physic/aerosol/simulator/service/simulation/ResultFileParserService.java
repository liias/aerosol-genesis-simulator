package ee.ut.physic.aerosol.simulator.service.simulation;

public interface ResultFileParserService {
    // reads in the file
    void readResultFile();

    //parses the content to something semantical. Should create SimulationResultValue from each item and add them to SimulationResult
    // and add SimulationResult to SimulationProcess and then persist the result
    void parseResultFile();
}
