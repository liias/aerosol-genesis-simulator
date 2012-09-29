package ee.ut.physic.aerosol.simulator.service.simulation;

public interface ResultFileParserService {
    // reads in the file
    void readResultFile();

    //parses the content to something semantical
    void parseResultFile();
}
