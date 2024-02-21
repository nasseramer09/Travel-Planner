package com.example.publictransportation.Services;

import com.example.publictransportation.Model.PublicTransportRoute;
import com.example.publictransportation.Repository.PTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class PTServices {

    @Autowired
    private PTRepository publicTransportRepository;

    public List<PublicTransportRoute>getAllRouts(){
        return publicTransportRepository.findAll();
    }
    public void createARoute(PublicTransportRoute transportRoute){
         publicTransportRepository.save(transportRoute);
    }

    public PublicTransportRoute getTransportById(Long id){
        return publicTransportRepository.findPublicTransportRouteById(id);
    }

    public PublicTransportRoute getRouteByTravelFromAndTravelTo(String from, String to){
        return  publicTransportRepository.findPublicTransportRouteByTravelFromAndTravelTo(from, to);
    }

    public PublicTransportRoute getRouteByTravelFrom(String from){
        PublicTransportRoute route= publicTransportRepository.findPublicTransportRouteByTravelFrom(from);
        if (!isStation(from)){
            return routeCalculation(from);
        }

        return route;
    }

    private boolean isStation(String location){
        return false;
    }
    //Needs to modify and implement calculation logik
    private PublicTransportRoute routeCalculation(String from){
        return getRouteByTravelFrom(from);
    }
    public PublicTransportRoute getRouteByTravelTo(String to){
        return  publicTransportRepository.findPublicTransportRouteByTravelTo(to);
    }
    public PublicTransportRoute getRouteByArrivalAndDeparture(LocalTime arrival, LocalTime departure){
        return  publicTransportRepository.findPublicTransportRouteByArrivalTimeAndDepartureTime(arrival, departure);
    }
    public PublicTransportRoute getRouteByArrival(LocalTime arrival){
        return  publicTransportRepository.findPublicTransportRouteByArrivalTime(arrival);
    }
    public PublicTransportRoute getRouteByDeparture(LocalTime departure){
        return  publicTransportRepository.findPublicTransportRouteByDepartureTime(departure);
    }
    public  PublicTransportRoute getRouteByDuration(int duration){
        return publicTransportRepository.findPublicTransportRouteByDuration(duration);
    }
    public PublicTransportRoute getRouteByStarAndEndPoint(String start, String end){
        return publicTransportRepository.findPublicTransportRouteByStartPointAndEndPoint(start,end);
    }
    public PublicTransportRoute getRouteByStart( String start){
        return publicTransportRepository.findPublicTransportRouteByStartPoint(start);
    }
    public PublicTransportRoute getRouteByEndpoint( String end){
        return publicTransportRepository.findPublicTransportRouteByEndPoint(end);
    }

    public PublicTransportRoute getRouteByTyp(String typ){
        return publicTransportRepository.findPublicTransportRouteByRouteTyp(typ);
    }



}
