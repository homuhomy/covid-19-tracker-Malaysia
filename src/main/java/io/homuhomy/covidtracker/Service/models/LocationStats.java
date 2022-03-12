package io.homuhomy.covidtracker.Service.models;

public class LocationStats {
    private String state;
    private int cases_new;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCases_new() {
        return cases_new;
    }

    public void setCases_new(int cases_new) {
        this.cases_new = cases_new;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", cases_new=" + cases_new +
                '}';
    }
}
