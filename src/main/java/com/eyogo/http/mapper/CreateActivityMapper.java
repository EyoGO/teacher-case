package com.eyogo.http.mapper;

import com.eyogo.http.dto.CreateActivityDto;
import com.eyogo.http.dto.CreateUserDto;
import com.eyogo.http.entity.Activity;
import com.eyogo.http.entity.Gender;
import com.eyogo.http.entity.Role;
import com.eyogo.http.entity.User;
import com.eyogo.http.util.LocalDateFormatter;

import java.io.File;

public class CreateActivityMapper implements Mapper<CreateActivityDto, Activity> {

    public static final String IMAGE_FOLDER = "users";
    private static final CreateActivityMapper INSTANCE = new CreateActivityMapper();

    @Override
    public Activity mapFrom(CreateActivityDto object) {
        return Activity.builder()
                .userId(object.getUserId())
//                .uniT(object.getUnitId() != null ? Integer.valueOf(object.getUnitId()) : null)
                .activityName(object.getActivityName())
                .description(object.getDescription())
                .authorId(object.getAuthorId())
                .build();
    }

    public static CreateActivityMapper getInstance() {
        return INSTANCE;
    }
}
