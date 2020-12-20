package pl.edu.pjatk.simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
public class SkmService {
    TrainService trainService;

    @Autowired
    public SkmService(TrainService trainService) {
        this.trainService = trainService;
    }

    public void moveTime() {
        trainService.moveTimeForward();
    }
}
