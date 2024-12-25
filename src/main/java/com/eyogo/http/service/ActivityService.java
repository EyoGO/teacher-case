package com.eyogo.http.service;

import com.eyogo.http.dao.ActivityRepository;
import com.eyogo.http.dao.UnitRepository;
import com.eyogo.http.dao.UserRepository;
import com.eyogo.http.dto.CreateActivityDto;
import com.eyogo.http.dto.GetActivityDto;
import com.eyogo.http.entity.Activity;
import com.eyogo.http.entity.Unit;
import com.eyogo.http.mapper.CreateActivityMapper;
import com.eyogo.http.mapper.GetUserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    private static final ActivityService INSTANCE = new ActivityService();

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UnitRepository unitDao;
    @Autowired
    private UserRepository userRepository;

    private final GetUserMapper getUserMapper = GetUserMapper.getInstance();
    private final CreateActivityMapper createActivityMapper = CreateActivityMapper.getInstance();

    private ActivityService() {
    }

    @SneakyThrows
    public Integer create(CreateActivityDto activityDto) {
        Unit unit = unitDao.findById(activityDto.getUnitId())
                .orElseThrow(() -> new EntityNotFoundException("Unit not found"));
        Activity activityEntity = createActivityMapper.mapFrom(activityDto);
        activityEntity.setUnit(unit);

        Activity save = activityRepository.save(activityEntity);
        // return id
        return save.getId();
    }

    @SneakyThrows
    public void update(Integer id, CreateActivityDto activityDto) {
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

    public List<GetActivityDto> findAll() {
//        List<Activity> allActivities = activitiyDao.findAll();
//        return allActivities.stream()
//                .map(activity -> GetActivityDto.builder()
//                        .id(activity.getId())
//                        .user(userRepository.findById(activity.getUserId()).map(getUserMapper::mapFrom).orElse(null))//TODO maybe just pass userID
//                        .name(activity.getActivityName())
//                        .description(activity.getDescription())
//                        .author(userRepository.findById(activity.getAuthorId()).map(getUserMapper::mapFrom).orElse(null))//TODO maybe just pass userID
//                        .build())
//                .collect(Collectors.toList());
        return List.of();
    }

    public GetActivityDto findStrictedDataById(Integer activityId) {
        Optional<Activity> activitiyOptional = activityRepository.findById(activityId);
        return activitiyOptional.map(activity -> GetActivityDto.builder()
                .id(activity.getId())
                .name(activity.getActivityName())
                .description(activity.getDescription())
                .build()).orElseThrow(() -> new EntityNotFoundException("Unit not found"));
    }

    public List<GetActivityDto> findByUserId(Integer userId) {
//        List<Activity> allActivitiesOfUser = activitiyDao.findByUser(userId);
//        return allActivitiesOfUser.stream()
//                .map(activity -> GetActivityDto.builder()
//                        .id(activity.getId())
//                        .user(userRepository.findById(activity.getUserId()).map(getUserMapper::mapFrom).orElse(null))//TODO maybe just pass userID
//                        .name(activity.getActivityName())
//                        .description(activity.getDescription())
//                        .author(userRepository.findById(activity.getAuthorId()).map(getUserMapper::mapFrom).orElse(null))//TODO maybe just pass userID
//                        .build())
//                .collect(Collectors.toList());
        return List.of();
    }

    public List<GetActivityDto> findByUserAndUnitId(Integer userId, Integer unitId, boolean useCascade) {
        List<Activity> allActivitiesOfUserByUnit = activityRepository.findByUserIdAndUnitIdOrderById(userId, unitId);
        if (useCascade) {
            fetchCascade(userId, unitId, allActivitiesOfUserByUnit);
        }
        return allActivitiesOfUserByUnit.stream()
                .map(activity -> GetActivityDto.builder()
                        .id(activity.getId())
                        .user(userRepository.findById(activity.getUserId()).map(getUserMapper::mapFrom).orElse(null))//TODO maybe just pass userID
                        .name(activity.getActivityName())
                        .description(activity.getDescription())
                        .author(userRepository.findById(activity.getAuthorId()).map(getUserMapper::mapFrom).orElse(null))//TODO maybe just pass userID
                        .build())
                .collect(Collectors.toList());
    }

    private void fetchCascade(Integer userId, Integer unitId, List<Activity> allActivitiesOfUserByUnit) {//TODO try using batch SQL query (accumulate queries)
        List<Unit> subUnits = unitDao.findAllByParentId(unitId);
        for (Unit unit: subUnits) {
            allActivitiesOfUserByUnit.addAll(activityRepository.findByUserIdAndUnitIdOrderById(userId, unit.getId()));
            fetchCascade(userId, unit.getId(), allActivitiesOfUserByUnit);
        }
    }

    public static ActivityService getInstance() {
        return INSTANCE;
    }
}
