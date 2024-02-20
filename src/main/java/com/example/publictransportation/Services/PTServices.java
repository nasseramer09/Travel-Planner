package com.example.publictransportation.Services;

import com.example.publictransportation.Model.PublicTransportRoute;
import com.example.publictransportation.Repository.PTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
