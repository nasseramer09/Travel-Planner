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
                "GARDEN_MALL", "Buss", 0, LocalTime.of(0,0,0),0,"Cloudy", null
        ));
        fakeCities.add(new FakeCity(
                "Edu_station", "Buss", 7, LocalTime.of(0,7,0),0,"Overcast", null
        ));
        fakeCities.add(new FakeCity(
                "UNIVERSITY_SCHOOL", "Buss", 12, LocalTime.of(0,15,0),0,"Rainy", null
        ));
        fakeCities.add(new FakeCity(
                "Lib_station", "Buss", 18, LocalTime.of(0,24,0),0,"Drizzling", null
        ));
        fakeCities.add(new FakeCity(
                "Evil_street_station", "Buss", 22, LocalTime.of(0,32,0),0,"Drizzling", null
        ));

        fakeCities.add(new FakeCity(
                "POLICE_STATION", "Buss/Train", 28, LocalTime.of(0,21,0),1,"Raimy", null
        ));
        fakeCities.add(new FakeCity(
                "Blue_street_station", "Train", 32, LocalTime.of(0,25,0),0,"Foggy", null
        ));

        fakeCities.add(new FakeCity(
                "Out_station", "Buss/Train", 36, LocalTime.of(0,40,0),2,"Cloudy", null
        ));
        fakeCities.add(new FakeCity(
                "InoStation", "Buss/Train", 40, LocalTime.of(0,48,0),3,"Cloudy", null
        ));
        fakeCityRepository.saveAll(fakeCities);

    }
}
