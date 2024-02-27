package com.example.publictransportation.Services;

import com.example.publictransportation.Model.FakeCity;
import com.example.publictransportation.Repository.FakeCityRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class FakeCityInitializer {
    @Autowired
    private FakeCityRepository fakeCityRepository;

    @PostConstruct
    public void init(){
        List<FakeCity>fakeCities=new ArrayList<>();

        fakeCities.add(new FakeCity(
                "Uni Station", "Buss", 0, LocalTime.of(0,0,0),0,"Cloudy", null
        ));
        fakeCities.add(new FakeCity(
                "Edu station", "Buss", 7, LocalTime.of(0,7,0),0,"Overcast", null
        ));
        fakeCities.add(new FakeCity(
                "Edu station", "Buss", 12, LocalTime.of(0,15,0),0,"Rainy", null
        ));
        fakeCities.add(new FakeCity(
                "Lib station", "Buss", 18, LocalTime.of(0,24,0),0,"Drizzling", null
        ));
        fakeCities.add(new FakeCity(
                "Evil street station", "Buss", 22, LocalTime.of(0,32,0),0,"Drizzling", null
        ));
        fakeCities.add(new FakeCity(
                "Evil street station", "Train", 22, LocalTime.of(0,18,0),0,"Rainy", null
        ));

        fakeCities.add(new FakeCity(
                "Polis station", "Train", 28, LocalTime.of(0,21,0),1,"Raimy", null
        ));
        fakeCities.add(new FakeCity(
                "Blue street station", "Train", 32, LocalTime.of(0,25,0),0,"Foggy", null
        ));

        fakeCities.add(new FakeCity(
                "Out station", "Train", 36, LocalTime.of(0,40,0),2,"Cloudy", null
        ));
        fakeCityRepository.saveAll(fakeCities);

    }
}
