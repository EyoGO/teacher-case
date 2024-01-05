package com.eyogo.http.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Unit {

    private Integer id;
    private String unitName;
    private Integer parentId;
    private Boolean managedByAdmin;

}

