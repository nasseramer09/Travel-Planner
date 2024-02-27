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
     return  routeCalculation(from,to, departureTime);
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
    public FakeCity routeCalculation(String from, String to, LocalTime departureTime){
        boolean isFromStation = isStation(from);
        boolean isToStation=isStation(to);
        FakeCity route = new FakeCity();
        route.setDepartureTime(departureTime.plusMinutes(5));
        if (isFromStation&&isToStation){

            FakeCity fromStation=fakeCityRepository.findByStationNameIgnoreCase(from);
            FakeCity toStation=fakeCityRepository.findByStationNameIgnoreCase(to);

            long travelHours=fromStation.getTravelDuration().until(toStation.getTravelDuration(), ChronoUnit.HOURS);
            long traveMinutes=fromStation.getTravelDuration().until(toStation.getTravelDuration(), ChronoUnit.MINUTES)%60;

            LocalTime arrivalTime=departureTime.plusHours(travelHours).plusMinutes(traveMinutes);
            route.setArrivalTime(arrivalTime);

            long totalHours=departureTime.until(arrivalTime, ChronoUnit.HOURS);
            long totalMinutes=departureTime.until(arrivalTime, ChronoUnit.MINUTES)%60;


           LocalTime calculatedDuration= departureTime.plusHours(totalHours).plusMinutes(totalMinutes);
           route.setTravelDuration(calculatedDuration);


        }else {
            //get Data from walk api
        route = null;
    }
    return route;
    }
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
