package pl.pjatk.skmapi.model;


import java.util.List;

public class RailLine {
    private String[] stations;

    public RailLine() {
    }

    public RailLine(String[] stations) {
        this.stations = stations;
    }

    public String[] getStations() {
        return stations;
    }

    public void setStations(String[] stations) {
        this.stations = stations;
    }
}
