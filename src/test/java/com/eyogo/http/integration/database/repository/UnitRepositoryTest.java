package com.eyogo.http.integration.database.repository;

import com.eyogo.http.repository.UnitRepository;
import com.eyogo.http.entity.Unit;
import com.eyogo.http.integration.IntegrationTestBase;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor

//@Rollback //Default, other one is @Commit
public class UnitRepositoryTest extends IntegrationTestBase {

    private final EntityManager entityManager;
    private final UnitRepository unitDao;

    /*Update must be @Modifying, but besides that it does not reflect the next find, because of 1LVL cache.
    * We still have old object in cache, so we need to clear context after each update using @Modifyinig(clearAutomatically=true),
    * flushAutomatically (flush automatically works in Hibernate, but to ensure better to set to true) flushes before
    * our update query to have all previous changes saved (because we can lose changes after clean).*/

    // So we do not see changes, because FLUSH is called manually (entityManager.flush()), transaction commit, flush before @Query by default
    @Test
    void update() {
        Unit byId = unitDao.getReferenceById(1);
        assertEquals("Безперервний професійний розвиток", byId.getUnitName());
        byId.setUnitName("before");

        unitDao.updateNameById("New", 1);

//        since we have clearAutomatically=true, update request will clear the context (cache) and byId will become detached and will cause LazyInitializationException
//        byId.getSomeNestedEntity().getId();

        Unit same = unitDao.getById(1);
        assertEquals("New", same.getUnitName());
    }

    @Test
    void paramsTest() {
        List<Unit> top5ByUnitNameStartingWith = unitDao.findTop5ByUnitNameStartingWith("П", Sort.by("id").descending());
        List<Unit> top5ByUnitNameStartingWith1 = unitDao.findTop5ByUnitNameStartingWith("П", Sort.by("id").descending().and(Sort.by("unitName").ascending()));
        // or safe using class
        Sort.TypedSort<Unit> sortBy = Sort.sort(Unit.class);
        Sort sort = sortBy.by(Unit::getUnitName)
                .and(sortBy.by(Unit::getId).descending());
        List<Unit> top5ByUnitNameStartingWith2 = unitDao.findTop5ByUnitNameStartingWith("П", sort);

        // even better way is Pageable ->
    }

    @Test
    void pageableTest() {
        Pageable pageable = PageRequest.of(2, 3, Sort.by("id").descending());
//        List<Unit> slice = unitDao.findAllBy(pageable);
        Slice<Unit> slice = unitDao.findAllBy(pageable);
        slice.forEach(System.out::println);
        // Here is advantage - we can get next slice:
        while (slice.hasNext()) {
            slice = unitDao.findAllBy(slice.nextPageable());
            slice.forEach(System.out::println);
        }
        //But in practice most useful is Page (it contains one more additional value - count)
    }

    @Test
    void delete() {
        Optional<Unit> unitOptional = unitDao.findById(1);
        assertTrue(unitOptional.isPresent());
        unitOptional.ifPresent(unitDao::delete);
        entityManager.flush();
        assertTrue(unitDao.findById(1).isEmpty());
    }

    @Test
    void findById() {
        var variable = entityManager.find(Unit.class, 1);
        assertNotNull(variable);
//        Assertions.assertThat(variable.getUnitName()).isEqualTo("Безперервний професійний розвиток");
    }
}
