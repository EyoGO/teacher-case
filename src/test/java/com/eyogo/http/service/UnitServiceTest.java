package com.eyogo.http.service;

import com.eyogo.http.dao.UnitRepository;
import com.eyogo.http.dto.GetUnitDto;
import com.eyogo.http.entity.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {
    public static final int ID = 3;

    @Mock
    private UnitRepository unitDao;
    @InjectMocks
    private UnitService unitService;

    /*@Test
    void findAll() {
    }*/

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Unit(ID, "UnitName", 1, false)))
                .when(unitDao).findById(ID);

        Optional<GetUnitDto> actualResult = unitService.findById(ID);

        Assertions.assertTrue(actualResult.isPresent());

        GetUnitDto expected = GetUnitDto.builder().id(ID).name("UnitName").managedByAdmin(false).build();
        actualResult.ifPresent(actual -> Assertions.assertEquals(expected, actual));

        Mockito.verify(unitDao, Mockito.times(1)).findById(ID);
        Mockito.verifyNoMoreInteractions(unitDao);
    }
}