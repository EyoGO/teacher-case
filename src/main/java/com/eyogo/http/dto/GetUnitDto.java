package com.eyogo.http.dto;

import com.eyogo.http.entity.Gender;
import com.eyogo.http.entity.Role;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class GetUnitDto {
    Integer id;
    String name;
    List<GetUnitDto> descendants;
    Boolean managedByAdmin;
}
