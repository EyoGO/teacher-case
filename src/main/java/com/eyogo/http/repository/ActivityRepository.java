package com.eyogo.http.repository;

import com.eyogo.http.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    List<Activity> findByUserIdAndUnitIdOrderById(Integer userId, Integer unitId);

    List<Activity> findByUserIdAndUnitIdInOrderById(Integer userId, List<Integer> unitId);
}
