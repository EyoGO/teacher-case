package com.eyogo.http.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UnitReadDto {
    Integer id;
    String name;
    List<UnitReadDto> descendants;
    Boolean managedByAdmin;
}
