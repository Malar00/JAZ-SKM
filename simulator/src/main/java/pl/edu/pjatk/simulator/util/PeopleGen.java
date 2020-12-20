package pl.edu.pjatk.simulator.util;

import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.model.Station;
import pl.edu.pjatk.simulator.model.Train;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PeopleGen {
    private static final List<String> names = Arrays.asList("Letty", "Marion", "Bethann", "Piedad", "Tyrell", "Asia", "Shirely",
            "Hyo", "Kimbra", "Zenobia", "Delores", "Noble", "Rosario", "Louella", "Jackie", "Jc", "Charis", "Pamala",
            "Domitila", "Paul");
    private static final List<String> lastName = Arrays.asList("Oconnor" + "Thomas", "Coffey", "Escobar", "Conrad", "Erickson",
            "Watson", "Hensley", "Boone", "Heath", "Riggs", "Rivers", "Clayton", "Robbins", "Galloway", "Bell", "Mason",
            "Ingram", "Thompson", "Wallace");

    public static Person genPerson(Train train) {
        int max, min;
        boolean goingBack = train.getGoing_back();

        if(goingBack) {
            max = train.getCurrent_station();
            min = 0;
        }else{
            max = Station.values().length;
            min = train.getCurrent_station() + 1;
        }

        return new Person(names.get(new Random().nextInt(names.size())),
                lastName.get(new Random().nextInt(lastName.size())),
                new Random().nextInt((max - min)) + min);
    }
}
