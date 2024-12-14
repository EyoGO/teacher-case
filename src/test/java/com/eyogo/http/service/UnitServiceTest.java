package com.eyogo.http.service;

import com.eyogo.http.dao.UnitDao;
import com.eyogo.http.dto.GetUnitDto;
import com.eyogo.http.entity.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {

    @Mock
    private UnitDao unitDao;
    @InjectMocks
    private UnitService unitService;

    /*@Test
    void findAll() {
    }*/

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Unit(3, "UnitName", 1, false)))
                .when(unitDao).findById(3);

        Optional<GetUnitDto> actualResult = unitService.findById(3);

        Assertions.assertTrue(actualResult.isPresent());

        GetUnitDto expected = GetUnitDto.builder().id(3).name("UnitName").managedByAdmin(false).build();
        actualResult.ifPresent(actual -> Assertions.assertEquals(expected, actual));

        Mockito.verify(unitDao, Mockito.times(1)).findById(3);
        Mockito.verifyNoMoreInteractions(unitDao);
    }
}