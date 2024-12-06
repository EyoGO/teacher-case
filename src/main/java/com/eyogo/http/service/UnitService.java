package com.eyogo.http.service;

import com.eyogo.http.dao.ActivitiyDao;
import com.eyogo.http.dao.UnitDao;
import com.eyogo.http.dao.UserDao;
import com.eyogo.http.dto.GetActivityDto;
import com.eyogo.http.dto.GetUnitDto;
import com.eyogo.http.entity.Activity;
import com.eyogo.http.entity.Unit;
import com.eyogo.http.mapper.GetUserMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UnitService {

    private static final UnitService INSTANCE = new UnitService();

    private final UnitDao unitDao = UnitDao.getInstance();

    public UnitService() {
    }

    public List<GetUnitDto> findAll() {
        List<Unit> units = unitDao.findAll();
        List<GetUnitDto> rootUnits = new ArrayList<>();

        Map<Integer, List<Unit>> parentIdToUnit = units.stream().collect(Collectors.groupingBy(Unit::getParentId));
        for (Unit unit: units) {
            if (unit.getParentId() == -1) {
                rootUnits.add(GetUnitDto.builder()
                        .id(unit.getId())
                        .name(unit.getUnitName())
                        .descendants(getDescendants(unit.getId(), parentIdToUnit))
                        .managedByAdmin(unit.getManagedByAdmin())
                        .build()
                );
            }
        }
        return rootUnits;
    }

    private List<GetUnitDto> getDescendants(Integer unitId, Map<Integer, List<Unit>> parentIdToUnits) {
        List<GetUnitDto> descendants = new ArrayList<>();
        List<Unit> descendantUnits = parentIdToUnits.get(unitId);
        if (descendantUnits != null) {
            for (Unit descendantUnit: descendantUnits) {
                Integer descendantId = descendantUnit.getId();
                descendants.add(GetUnitDto.builder()
                        .id(descendantId)
                        .name(descendantUnit.getUnitName())
                        .descendants(getDescendants(descendantId, parentIdToUnits))
                        .managedByAdmin(descendantUnit.getManagedByAdmin())
                        .build()
                );
            }
        }
        return descendants;
    }

    public Optional<GetUnitDto> findById(Integer unitId) {
        Optional<Unit> unitById = unitDao.findById(unitId);
        return unitById.map(unit -> GetUnitDto.builder()
                .id(unit.getId())
                .name(unit.getUnitName())
                .descendants(null)//TODO consider this, maybe need new DTO
                .managedByAdmin(unit.getManagedByAdmin())
                .build());
    }

    public static UnitService getInstance() {
        return INSTANCE;
    }
}
