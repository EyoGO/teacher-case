package com.eyogo.http.integration.database.repository;

import com.eyogo.http.entity.Activity;
import com.eyogo.http.integration.IntegrationTestBase;
import com.eyogo.http.integration.annotation.IT;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor

//@Rollback //Default, other one is @Commit
public class ActivityRepositoryTest extends IntegrationTestBase {

    private final EntityManager entityManager;

    @Test
    void findById() {
        var variable = entityManager.find(Activity.class, 5);
        assertNotNull(variable);
//        Assertions.assertThat(variable.getUnitName()).isEqualTo("Безперервний професійний розвиток");
    }
}
