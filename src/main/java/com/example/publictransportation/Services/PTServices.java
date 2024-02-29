package com.example.publictransportation.Services;

import com.example.publictransportation.Model.FakeCity;
import com.example.publictransportation.Repository.FakeCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.RouteMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PTServices {

    @Autowired
    private FakeCityRepository fakeCityRepository;

    private final RestTemplate restTemplate;

    public PTServices(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate=restTemplateBuilder.build();
    }
    public List<FakeCity>getAllRouts(){
        return fakeCityRepository.findAll();
    }
   public void createARoute(FakeCity transportRoute){
         fakeCityRepository.save(transportRoute);
    }

    public FakeCity getTransportById(Long id){
        return fakeCityRepository.findAllById(id);
    }
    public FakeCity getRouteByTravelFromAndTravelTo(String from, String to, LocalTime departureTime){

        FakeCity route = new FakeCity();
        boolean fromIsAStation=isStation(from);
        boolean toIsAStation=isStation(to);

        if (fromIsAStation && toIsAStation) {
            FakeCity fromStation = fakeCityRepository.findByStationNameIgnoreCase(from);
            FakeCity toStation = fakeCityRepository.findByStationNameIgnoreCase(to);
            if (fromStation != null && toStation != null) {

                long travelHours = fromStation.getTravelDuration().until(toStation.getTravelDuration(), ChronoUnit.HOURS);
                long traveMinutes = fromStation.getTravelDuration().until(toStation.getTravelDuration(), ChronoUnit.MINUTES) % 60;

                LocalTime arrivalTime = departureTime.plusHours(travelHours).plusMinutes(traveMinutes);
                route.setDepartureFrom(from);
                route.setDestinationTo(to);
                route.setDepartureTime(departureTime);
                route.setTransportTyp(toStation.getTransportTyp());
                route.setArrivalTime(arrivalTime);
                route.setChanges(Math.abs(toStation.getChanges()) - fromStation.getChanges());
                route.setStationName(toStation.getStationName());
                route.setTravelDuration(arrivalTime.minusHours(departureTime.getHour()).minusMinutes(departureTime.getMinute()));
                System.out.println("service " + route);
                return route;
            }
        } else if (!isStation(from)||!isStation(to)) {

            String apiurl =  "https://inotravelplanner.azurewebsites.net/" + from.toUpperCase() + "/"+ to.toUpperCase();
            ResponseEntity<FakeCity[]> response = restTemplate.getForEntity(apiurl, FakeCity[].class);
            FakeCity[] routeResponses = response.getBody();
            System.out.println(routeResponses);
            if (routeResponses !=null && routeResponses.length>0){
                FakeCity routeResponse = routeResponses[0];
                routeResponse.setDepartureFrom(from);
                route.setDestinationTo(to);

                int apiwalkingDuration=0;
                for (FakeCity count:routeResponses
                     ) {
                    int minutesCount=count.getTravelDuration().getHour() * 60 + count.getTravelDuration().getMinute();
                    apiwalkingDuration += minutesCount;
                }

                int totalDuration = apiwalkingDuration + departureTime.getHour() * 60 +departureTime
                        .getMinute();
                int totalHours=totalDuration/60;
                int totalMinutes=totalDuration%60;
                route.setWalkTime(String.valueOf(apiwalkingDuration));
                route.setTravelDuration(LocalTime.of(totalHours,totalMinutes));
                    return route;
            }else {
                return null;
            }



        }

        return route;
    }


    public List<FakeCity>getFavoriteRouts(){
        return fakeCityRepository.findByFavorite(true);
    }

    public void updateFavoriteStatus(Long id, boolean status){
        FakeCity currentFavoriteRoute = fakeCityRepository.findAllById(id);
        if (currentFavoriteRoute != null){
            currentFavoriteRoute.setFavorite(status);
            fakeCityRepository.save(currentFavoriteRoute);
        }
    }

    private boolean isStation(String location){
        FakeCity match = fakeCityRepository.findByStationNameIgnoreCase(location);

            return match !=null;
    }
    public void  reportAnIssue(Long id, String issue, int estimatedDilation){
        FakeCity route = fakeCityRepository.findAllById(id);
        if (route!=null){
            try {
            LocalTime dilation=LocalTime.of(estimatedDilation / 60, estimatedDilation % 60);
                LocalTime newDuration= route.getTravelDuration().plusMinutes(dilation.getMinute()).plusHours(dilation.getHour());

                route.setDelayingInfo(issue);
                route.setDelayingTimeEstimation(String.valueOf(estimatedDilation));
                route.setTravelDuration(newDuration);
                fakeCityRepository.save(route);
            }catch (Exception e){
                System.out.println(" Invalid Format for estimated dilation");
            }
        }
    }
    public List<FakeCity> getDelaysAndIssueInfo(){
        return fakeCityRepository.findByDelayingInfoNotNull();
    }

}
