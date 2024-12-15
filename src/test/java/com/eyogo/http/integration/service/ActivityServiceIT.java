package com.eyogo.http.integration.service;

import com.eyogo.http.config.DatabaseProperties;
import com.eyogo.http.integration.annotation.IT;
import com.eyogo.http.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@IT
@RequiredArgsConstructor
public class ActivityServiceIT {

    private UnitService unitService;
    private DatabaseProperties databaseProperties;

    @Test
    void test() {

    }
}
