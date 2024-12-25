package com.eyogo.http.dao;

import com.eyogo.http.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    //TODO: can be enhanced to order by predefined column with creation time
    List<Activity> findByUserIdAndUnitIdOrderById(Integer userId, Integer unitId);
}
