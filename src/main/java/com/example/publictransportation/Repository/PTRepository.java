package com.example.publictransportation.Repository;

import com.example.publictransportation.Model.PublicTransportRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface PTRepository extends JpaRepository<PublicTransportRoute, Long> {
    PublicTransportRoute findPublicTransportRouteById(Long id);
    PublicTransportRoute findPublicTransportRouteByTravelFromAndTravelTo(String from, String to);
    PublicTransportRoute findPublicTransportRouteByTravelFrom(String from);
    PublicTransportRoute findPublicTransportRouteByTravelTo(String to);
    PublicTransportRoute findPublicTransportRouteByArrivalTimeAndDepartureTime(LocalTime arrival, LocalTime departure);
    PublicTransportRoute findPublicTransportRouteByArrivalTime(LocalTime arrival);
    PublicTransportRoute findPublicTransportRouteByDepartureTime(LocalTime departure);
    PublicTransportRoute findPublicTransportRouteByDuration(int duration);
    List<PublicTransportRoute> findPublicTransportRouteByStartPointAndEndPoint(String startPoint, String endPoint);
    PublicTransportRoute findPublicTransportRouteByStartPoint(String startPoint);
    PublicTransportRoute findPublicTransportRouteByEndPoint(String endPoint);
    PublicTransportRoute findPublicTransportRouteByRouteTyp(String routeTyp);
    List<PublicTransportRoute> findPublicTransportRouteByDelayingInfoNotNull();
    List<PublicTransportRoute>findPublicTransportRouteByFavorite(boolean favorite);


}
