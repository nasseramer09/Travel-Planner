package com.example.publictransportation.Services;

import com.example.publictransportation.Model.FakeCity;
import com.example.publictransportation.Repository.FakeCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PTServices {

    @Autowired
    private FakeCityRepository fakeCityRepository;
    public List<FakeCity>getAllRouts(){
        return fakeCityRepository.findAll();
    }
   public void createARoute(FakeCity transportRoute){
         fakeCityRepository.save(transportRoute);
         System.out.println(transportRoute);
    }

    public FakeCity getTransportById(Long id){
        return fakeCityRepository.findAllById(id);
    }

    public FakeCity getRouteByTravelFromAndTravelTo(String from, String to, LocalTime departureTime){
        FakeCity route = new FakeCity();

        FakeCity fromStation=fakeCityRepository.findByStationNameIgnoreCase(from);
        FakeCity toStation=fakeCityRepository.findByStationNameIgnoreCase(to);
        if (fromStation !=null && toStation !=null){

            long travelHours=fromStation.getTravelDuration().until(toStation.getTravelDuration(), ChronoUnit.HOURS);
            long traveMinutes=fromStation.getTravelDuration().until(toStation.getTravelDuration(), ChronoUnit.MINUTES)%60;

            LocalTime arrivalTime=departureTime.plusHours(travelHours).plusMinutes(traveMinutes);

            route.setStationName(toStation.getStationName());
            route.setTransportTyp(toStation.getTransportTyp());
            route.setDepartureTime(departureTime);
            route.setArrivalTime(arrivalTime);
            route.setChanges(Math.abs(toStation.getChanges()) - fromStation.getChanges());
            route.setTravelDuration(arrivalTime.minusHours(departureTime.getHour()).minusMinutes(departureTime.getMinute()));
            return route;
        }else {
            return  null;
        }

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
            return match == null;
    }
    //Needs to modify and implement calculation logik if startpoint is not a station gå to Inos api and get walkroute and time
    public void  reportAnIssue(Long id, String issue, String estimatedDilation){
        FakeCity route = fakeCityRepository.findAllById(id);
        if (route!=null){
            try {
            LocalTime dilation=LocalTime.parse(estimatedDilation);
                LocalTime newDuration= route.getTravelDuration().plusMinutes(dilation.getMinute()).plusHours(dilation.getHour());

                route.setDelayingInfo(issue);
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
