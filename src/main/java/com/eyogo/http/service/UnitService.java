package com.eyogo.http.service;

import com.eyogo.http.repository.UnitRepository;
import com.eyogo.http.dto.UnitReadDto;
import com.eyogo.http.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;

    public List<UnitReadDto> findAll() {
        List<Unit> units = unitRepository.findAll();
        List<UnitReadDto> rootUnits = new ArrayList<>();

        Map<Integer, List<Unit>> parentIdToUnit = units.stream().collect(Collectors.groupingBy(Unit::getParentId));
        for (Unit unit: units) {
            if (unit.getParentId() == -1) {
                rootUnits.add(UnitReadDto.builder()
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

    private List<UnitReadDto> getDescendants(Integer unitId, Map<Integer, List<Unit>> parentIdToUnits) {
        List<UnitReadDto> descendants = new ArrayList<>();
        List<Unit> descendantUnits = parentIdToUnits.get(unitId);
        if (descendantUnits != null) {
            for (Unit descendantUnit: descendantUnits) {
                Integer descendantId = descendantUnit.getId();
                descendants.add(UnitReadDto.builder()
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

    public Optional<UnitReadDto> findById(Integer unitId) {
        Optional<Unit> unitById = unitRepository.findById(unitId);
        return unitById.map(unit -> UnitReadDto.builder()
                .id(unit.getId())
                .name(unit.getUnitName())
                .managedByAdmin(unit.getManagedByAdmin())
                .build());
    }
}
