package pl.edu.pjatk.simulator.model;


import java.util.HashMap;

public enum Station {
    GDYNIA_GLOWNA(2),
    GDYNIA_SW_MAKSYMILIANA(0),
    GDYNIA_REDLOWO(0),
    GDYNIA_ORLOWO(0),
    SOPOT_KAMIENNY_POTOK(0),
    SOPOT(0),
    SOPOT_WYSCIGI(0),
    GDANSK_ZABIANKA(0),
    GDANSK_OLIWA(0),
    GDANSK_PRZYMORZE(0),
    GDANSK_ZASPA(0),
    GDANSK_WRZESZCZ(0),
    GDANSK_POLITECHNIKA(0),
    GDANSK_STOCZNIA(0),
    GDANSK_GLOWNY(2);

    private int waitTime;
    private HashMap<Station, Integer> stationHashMap = new HashMap<>();


    Station(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public int size(){
        return values().length;
    }

}
