package com.example.publictransportation.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalTime;

@Entity
@Data
public class PublicTransportRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String routeTyp;
    private String startPoint;
    private String endPoint;
    private String travelFrom;
    private String travelTo;
    private String delayingInfo;
    private boolean favorite;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int numberOfRouteChanges;
    private int duration;
}
