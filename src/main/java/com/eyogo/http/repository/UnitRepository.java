package com.eyogo.http.repository;

import com.eyogo.http.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Integer> {

    List<Unit> findAllByParentId(Integer parentId);

}
