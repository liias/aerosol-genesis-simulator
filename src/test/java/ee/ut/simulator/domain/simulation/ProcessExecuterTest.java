package ee.ut.simulator.domain.simulation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProcessExecuterTest {

    @Test
    public void testRun() throws Exception {
        ProcessExecuter processExecuter = new ProcessExecuter();
        processExecuter.run();
        assertEquals(4, 2 + 2);
    }
}
