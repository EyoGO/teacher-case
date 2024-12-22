package com.eyogo.http.dao;

import com.eyogo.http.entity.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*Ставити тут анотацію @Repository нема потреби, бо спрінг сканує анотації інтерфейсів та має обробку на це.*/

/*Як же генеруються ці реалізації методів?
Якщо коротко, то створюється проксі QueryExecutorMethodInterceptor, де є мапа <Method, RepositoryQuery>, де ключ - метод репозиторія,
* а значення - реалізація інтерфейсу RepositoryQuery:
    - NamedQuery (використовує анотацію JPA @NamedQuery, яка ставиться над ентіті й пишеться запит під цю назву). Якщо в репозиторії є метод з такою ж назвою як іменований запит над ентіті, то береться з нього.
    - NativeJpaQuery - використовує нативний SQL, щоб використовувати треба анотація @Query
    - SimpleJpaQuery - використовує HQL, щоб використовувати треба анотація @Query
    - StoredProcedureJpaQuery - deprecated, бо логіка має бути в програмі, а не на базі
    - PartTreeJpaQuery - генерація на основі ключових слів у назві методу (запарсити у вигляді Criteria API)
  Пріоритетність їх така - @Query(NativeJpaQuery, SimpleJpaQuery) -> NamedQuery -> PartTreeJpaQuery*/

/*Тут є певна ієрархія в наступному порядку збільшення готового функціоналу:
1) Repository надає найбільш поверхневий функціонал - можливість писати самому методи без реалізацій.
2) CrudRepository предефайнить в інтерфейсі популярні CRUD операції (можна глянути в ньому ж).
3) PagingAndSortingRepository - має все з CrudRepository, але додає findAll(Sorted), findAll(Pageable).
4) JpaRepository - видалення батчами, метод flush EntityManager,...
Аби постійно не генерувати дефолтні PartTreeJpaQuery є уже SimpleJpaRepository клас з реалізаціями й більшість викликів буде перенаправлятись туди.*/

/*@Query може використовуватись для будь-яких операцій, але для UPDATE/DELETE/... треба додати @Modifying*/
public interface UnitRepository extends JpaRepository<Unit, Integer> {

    List<Unit> findAllByParentId(Integer parentId);

    List<Unit> findAll();

    void delete(Unit unit);

    @Query("update Unit u set u.unitName = :name where u.id = :id")
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void updateNameById(String name, Integer id);

    // To pass sorting dynamically
    List<Unit> findTop5ByUnitNameStartingWith(String unitName, Sort sort);

    // Pageable
    // It also enables 3 new types as return types: Streamable (iterator wrapper in stream style) -> Slice -> Page
    // Slice is the same as Page, but Page has count requests, that can be controlled using @Query(countName/countQuery/countProjection)
//    List<Unit> findAllBy(Pageable pageable);
//    Slice<Unit> findAllBy(Pageable pageable);
    @Query(value = "select u from Unit u",
            countQuery = "select count(distinct u.unitName) from Unit u")
    Page<Unit> findAllBy(Pageable pageable);

    // Projections (DTOs) (classes and interfaces) allow fetching only necessary information take a look at UserRepository ---->>>
}
