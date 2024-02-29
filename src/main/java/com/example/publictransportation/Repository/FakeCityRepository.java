package com.example.publictransportation.Repository;

import com.example.publictransportation.Model.FakeCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FakeCityRepository extends JpaRepository<FakeCity, Long>{

    FakeCity findAllById(Long id);

    FakeCity findByStationNameIgnoreCase(String name);
    List<FakeCity>findByDelayingInfoNotNull();
    List<FakeCity>findByFavorite(boolean favorite);

}
