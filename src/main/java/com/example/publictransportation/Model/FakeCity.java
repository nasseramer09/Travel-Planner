package com.example.publictransportation.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Data
public class FakeCity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "stationName")
    private String stationName;

    @Column(name = "transportTyp")
    private String transportTyp;

    @Column(name = "distance")
    private int distance;

    @Column(name="TravelDuration")
    private LocalTime TravelDuration;

    @Column(name="changes")
    private int changes;

    @Column(name = "weather")
    private String weather;

    @Column(name="delayingInfo")
    private String delayingInfo;

    @Column(name="delayingTimeEstimation")
    private String delayingTimeEstimation;

    @Column(name="walkTime")
    private String walkTime;

    @Column(name = "description")
    private String description;

    @Column(name="favorite")
    private boolean favorite;

    @Column(name="departureTime")
    private LocalTime departureTime;

    @Column(name="arrivalTime")
    private LocalTime arrivalTime;
    public FakeCity(){}
    public FakeCity(String stationName, String transportTyp, int distance, LocalTime TravelDuration, int changes, String weather,  String delayingInfo){
        this.stationName=stationName;
        this.transportTyp=transportTyp;
        this.distance=distance;
        this.TravelDuration=TravelDuration;
        this.changes=changes;
        this.weather=weather;
        this.delayingInfo=delayingInfo;
    }
}
