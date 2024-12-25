package com.eyogo.http.service;

import com.eyogo.http.dao.UnitRepository;
import com.eyogo.http.dto.GetUnitDto;
import com.eyogo.http.entity.Unit;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UnitService {

    private static final UnitService INSTANCE = new UnitService();

    @Autowired
    private UnitRepository unitRepository;

    //TODO Deprecated
    public UnitService() {
    }

    @Autowired
    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public List<GetUnitDto> findAll() {
        List<Unit> units = unitRepository.findAll();
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

    public GetUnitDto findById(Integer unitId) {
        Optional<Unit> unitById = unitRepository.findById(unitId);
        return unitById.map(unit -> GetUnitDto.builder()
                .id(unit.getId())
                .name(unit.getUnitName())
                .descendants(null)//TODO consider this, maybe need new DTO
                .managedByAdmin(unit.getManagedByAdmin())
                .build())
                .orElseThrow(() -> new EntityNotFoundException("Unit not found."));
    }

    public static UnitService getInstance() {
        return INSTANCE;
    }
}
