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

    public FakeCity getRouteByTravelFromAndTravelTo(String from, String to){
     return  routeCalculation(from,to);
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
        FakeCity match = fakeCityRepository.findByStationName(location);
            return match == null;
    }
    //Needs to modify and implement calculation logik if startpoint is not a station gå to Inos api and get walkroute and time
    public FakeCity routeCalculation(String from, String to){
        boolean isFromStation = isStation(from);
        boolean isToStation=isStation(to);
        if (isFromStation&&isToStation){

            /* To calculate travel distance
            int distanceFrom=fakeCityRepository.findByStationName(from).getDistance();
            int distanceTo=fakeCityRepository.findByStationName(from).getDistance();
            int calculatedRoute = distanceTo - distanceFrom;*/

            // To calculate travel duration
            LocalTime durationFrom = fakeCityRepository.findByStationName(from).getTravelDuration();
            LocalTime durationTo = fakeCityRepository.findByStationName(from).getTravelDuration();
            long hours=durationFrom.until(durationTo, ChronoUnit.HOURS);
            long minutes=durationFrom.until(durationTo, ChronoUnit.MINUTES)%60;
           LocalTime calculatedDuration= durationFrom.plusHours(hours).plusMinutes(minutes);
           FakeCity newCalculatedRoute = new FakeCity();
           newCalculatedRoute.setArrivalTime(calculatedDuration);
           return newCalculatedRoute;

        }else {
            //get Data from walk api
        return routeCalculation(from,to);
    }}
    public List<FakeCity> getRouteByTyp(String typ){
        return fakeCityRepository.findByTransportTyp(typ);
    }

    public void  reportAnIssue(Long id, String issue, LocalTime estimatedDilation){
        FakeCity route = fakeCityRepository.findAllById(id);
        if (route!=null){
            LocalTime newDuration= route.getTravelDuration().plusMinutes(estimatedDilation.getMinute()).plusHours(estimatedDilation.getHour());
            route.setDelayingInfo(issue);
            route.setTravelDuration(newDuration);
            fakeCityRepository.save(route);
        }
    }
    public List<FakeCity> getDelaysAndIssueInfo(){
        return fakeCityRepository.findByDelayingInfoNotNull();
    }

}
