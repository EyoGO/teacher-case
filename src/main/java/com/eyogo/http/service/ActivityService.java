package com.eyogo.http.service;

import com.eyogo.http.repository.ActivityRepository;
import com.eyogo.http.repository.UnitRepository;
import com.eyogo.http.repository.UserRepository;
import com.eyogo.http.dto.ActivityCreateEditDto;
import com.eyogo.http.dto.ActivityReadDto;
import com.eyogo.http.entity.Activity;
import com.eyogo.http.entity.Unit;
import com.eyogo.http.mapper.ActivityCreateMapper;
import com.eyogo.http.mapper.UserReadMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;

    private final UnitRepository unitRepository;
    private final UserRepository userRepository;

    private final UserReadMapper userReadMapper;
    private final ActivityCreateMapper activityCreateMapper;

    @SneakyThrows
    public Integer create(ActivityCreateEditDto activityDto) {
        Unit unit = unitRepository.findById(activityDto.getUnitId())
                .orElseThrow(() -> new EntityNotFoundException("Unit not found"));
        Activity activityEntity = activityCreateMapper.mapFrom(activityDto);
        activityEntity.setUnit(unit);

        Activity save = activityRepository.save(activityEntity);
        // return id
        return save.getId();
    }

    @SneakyThrows
    public void update(Integer id, ActivityCreateEditDto activityDto) {
        Optional<Activity> byId = activityRepository.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Activity not found.");
        }

        Activity activity = byId.get();
        //TODO validate correct parameters
        //TODO use criteria API or QueryDSL
        if (activityDto.getActivityName() != null) {
            activity.setActivityName(activityDto.getActivityName());
        }
        if (activityDto.getDescription() != null) {
            activity.setDescription(activityDto.getDescription());
        }
        activityRepository.save(activity);
    }

    @SneakyThrows
    public void delete(Integer id) {
        activityRepository.deleteById(id);
    }

    public Optional<ActivityReadDto> findActivityById(Integer activityId) {
        Optional<Activity> activitiyOptional = activityRepository.findById(activityId);
        return activitiyOptional.map(activity -> ActivityReadDto.builder()
                .id(activity.getId())
                .name(activity.getActivityName())
                .description(activity.getDescription())
                .authorId(activity.getAuthorId())
                .build());
    }

    public List<ActivityReadDto> findByUserAndUnitId(Integer userId, Integer unitId, boolean useCascade) {
        List<Activity> allActivitiesOfUserByUnit;
        if (useCascade) {
            List<Integer> unitIds = new LinkedList<>();
            unitIds.add(unitId);
            fetchCascadeIds(unitId, unitIds);
            allActivitiesOfUserByUnit = activityRepository.findByUserIdAndUnitIdInOrderById(userId, unitIds);
        } else {
            allActivitiesOfUserByUnit = activityRepository.findByUserIdAndUnitIdOrderById(userId, unitId);
        }
        return allActivitiesOfUserByUnit.stream()
                .map(activity -> ActivityReadDto.builder()
                        .id(activity.getId())
                        .user(activity.getUserId())
                        .name(activity.getActivityName())
                        .description(activity.getDescription())
                        .authorId(activity.getAuthorId())
                        .build())
                .collect(Collectors.toList());
    }

    private void fetchCascadeIds(Integer unitId, List<Integer> unitsIds) {
        List<Integer> newIds = unitRepository.findAllByParentId(unitId).stream()
                .map(Unit::getId)
                .toList();
        unitsIds.addAll(newIds);
        for (Integer id: newIds) {
            fetchCascadeIds(id, unitsIds);
        }
    }
}
