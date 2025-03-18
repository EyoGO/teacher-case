package com.eyogo.http.mapper;

import com.eyogo.http.dto.ActivityCreateEditDto;
import com.eyogo.http.entity.Activity;
import org.springframework.stereotype.Component;

@Component
public class ActivityCreateMapper implements Mapper<ActivityCreateEditDto, Activity> {

    @Override
    public Activity mapFrom(ActivityCreateEditDto object) {
        return Activity.builder()
                .userId(object.getUserId())
                .activityName(object.getActivityName())
                .description(object.getDescription())
                .authorId(object.getAuthorId())
                .build();
    }
}
