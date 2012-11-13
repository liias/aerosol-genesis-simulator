package ee.ut.physic.aerosol.simulator.domain.simulation.parameter;

public class ResultParameters {
    private String[] parameterNames;

    public ResultParameters() {
        createParameters();
    }

    private void createParameters() {
        parameterNames = new String[]{
                "time",
                "J-f",
                "Jof",
                "n+/100",
                "n-/100",
                "do",
                "No/500",
                "p1.42",
                "p1.60",
                "p1.81",
                "p2.04",
                "p2.30",
                "p2.60",
                "p2.94",
                "p3.32",
                "p3.74",
                "p4.23",
                "p4.77",
                "p5.39",
                "p6.09",
                "p6.87",
                "p7.76",
                "p8.76",
                "p9.89",
                "p11.17",
                "p12.61",
                "p14.24",
                "p16.08",
                "p18.16",
                "p20.51",
                "p23.16",
                "p26.15",
                "p29.52",
                "p33.34",
                "p37.64",
                "N+/5",
                "N-/5",
                "100q",
        };
    }

    public String[] getParameterNames() {
        return parameterNames;
    }
}
