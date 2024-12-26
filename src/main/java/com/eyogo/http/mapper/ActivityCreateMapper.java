package com.eyogo.http.mapper;

import com.eyogo.http.dto.ActivityCreateEditDto;
import com.eyogo.http.dto.UnitReadDto;
import com.eyogo.http.entity.Activity;
import org.springframework.stereotype.Component;

@Component
public class ActivityCreateMapper implements Mapper<ActivityCreateEditDto, Activity> {

    public static final String IMAGE_FOLDER = "users";

    @Override
    public Activity mapFrom(ActivityCreateEditDto object) {
//        UnitReadDto unit = unitMapper.mapFrom(object.getUnit());
        return Activity.builder()
                .userId(object.getUserId())
//                .unit(object.getUnitId() != null ? Integer.valueOf(object.getUnitId()) : null)
                .activityName(object.getActivityName())
                .description(object.getDescription())
                .authorId(object.getAuthorId())
                .build();
    }
}
