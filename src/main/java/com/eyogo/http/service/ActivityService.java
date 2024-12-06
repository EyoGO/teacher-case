package com.eyogo.http.service;

import com.eyogo.http.dao.ActivitiyDao;
import com.eyogo.http.dao.UnitDao;
import com.eyogo.http.dao.UserDao;
import com.eyogo.http.dto.CreateActivityDto;
import com.eyogo.http.dto.GetActivityDto;
import com.eyogo.http.entity.Activity;
import com.eyogo.http.entity.Unit;
import com.eyogo.http.mapper.CreateActivityMapper;
import com.eyogo.http.mapper.GetUserMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    private static final ActivityService INSTANCE = new ActivityService();

    private final ActivitiyDao activitiyDao = ActivitiyDao.getInstance();
    private final UnitDao unitDao = UnitDao.getInstance();
    private final UserDao userDao = UserDao.getInstance();

    private final GetUserMapper getUserMapper = GetUserMapper.getInstance();
    private final CreateActivityMapper createActivityMapper = CreateActivityMapper.getInstance();

    private ActivityService() {
    }

    @SneakyThrows
    public Integer create(CreateActivityDto activityDto) {
        Activity activityEntity = createActivityMapper.mapFrom(activityDto);
        activitiyDao.save(activityEntity);
        // return id
        return activityEntity.getId();
    }

    @SneakyThrows
    public void update(Integer id, CreateActivityDto activityDto) {
        Activity activityEntity = createActivityMapper.mapFrom(activityDto);
        activityEntity.setId(id);
        activitiyDao.update(activityEntity);
    }

    @SneakyThrows
    public boolean delete(Integer id) {
        return activitiyDao.delete(id);
    }

    public List<GetActivityDto> findAll() {
        List<Activity> allActivities = activitiyDao.findAll();
        return allActivities.stream()
                .map(activity -> GetActivityDto.builder()
                        .id(activity.getId())
                        .user(userDao.findById(activity.getUserId()).map(getUserMapper::mapFrom).orElse(null))//TODO maybe just pass userID
                        .name(activity.getActivityName())
                        .description(activity.getDescription())
                        .author(userDao.findById(activity.getAuthorId()).map(getUserMapper::mapFrom).orElse(null))//TODO maybe just pass userID
                        .build())
                .collect(Collectors.toList());
    }

    public Optional<GetActivityDto> findStrictedDataById(Integer activityId) {
        Optional<Activity> activitiyOptional = activitiyDao.findById(activityId);
        return activitiyOptional.map(activity -> GetActivityDto.builder()
                .id(activity.getId())
                .name(activity.getActivityName())
                .description(activity.getDescription())
                .build());
    }

    public List<GetActivityDto> findByUserId(Integer userId) {
        List<Activity> allActivitiesOfUser = activitiyDao.findByUser(userId);
        return allActivitiesOfUser.stream()
                .map(activity -> GetActivityDto.builder()
                        .id(activity.getId())
                        .user(userDao.findById(activity.getUserId()).map(getUserMapper::mapFrom).orElse(null))//TODO maybe just pass userID
                        .name(activity.getActivityName())
                        .description(activity.getDescription())
                        .author(userDao.findById(activity.getAuthorId()).map(getUserMapper::mapFrom).orElse(null))//TODO maybe just pass userID
                        .build())
                .collect(Collectors.toList());
    }

    public List<GetActivityDto> findByUserAndUnitId(Integer userId, Integer unitId, boolean useCascade) {
        List<Activity> allActivitiesOfUserByUnit = activitiyDao.findByUserAndUnit(userId, unitId);
        if (useCascade) {
            fetchCascade(userId, unitId, allActivitiesOfUserByUnit);
        }
        return allActivitiesOfUserByUnit.stream()
                .map(activity -> GetActivityDto.builder()
                        .id(activity.getId())
                        .user(userDao.findById(activity.getUserId()).map(getUserMapper::mapFrom).orElse(null))//TODO maybe just pass userID
                        .name(activity.getActivityName())
                        .description(activity.getDescription())
                        .author(userDao.findById(activity.getAuthorId()).map(getUserMapper::mapFrom).orElse(null))//TODO maybe just pass userID
                        .build())
                .collect(Collectors.toList());
    }

    private void fetchCascade(Integer userId, Integer unitId, List<Activity> allActivitiesOfUserByUnit) {//TODO try using batch SQL query (accumulate queries)
        List<Unit> subUnits = unitDao.findSubUnits(unitId);
        for (Unit unit: subUnits) {
            allActivitiesOfUserByUnit.addAll(activitiyDao.findByUserAndUnit(userId, unit.getId()));
            fetchCascade(userId, unit.getId(), allActivitiesOfUserByUnit);
        }
    }

    public static ActivityService getInstance() {
        return INSTANCE;
    }
}
