package ee.ut.physic.aerosol.simulator.service.simulation;

public class RatedProcess implements Comparable<RatedProcess> {
    private long processId;
    private double rating;

    public long getProcessId() {
        return processId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setProcessId(long processId) {
        this.processId = processId;
    }

    @Override
    public int compareTo(RatedProcess o) {
        if (rating < o.getRating()) {
            return -1;
        } else if (rating > o.getRating()) {
            return 1;
        } else {
            return 0;
        }
    }
}
