package com.eyogo.http.integration.service;

import com.eyogo.http.config.DatabaseProperties;
import com.eyogo.http.dto.UnitReadDto;
import com.eyogo.http.integration.annotation.IT;
import com.eyogo.http.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

// Краще передивитись цей розділ у курсі
// Наступна анотація вливається в життєвий цикл JUnit 5. (є багато способів, але саме цей через колбеки - ми
// маємо @BeforeAll->@Before->... і в цьому ланцюжку всюди є проміжний колбек типу beforeTestExectuion...)
// В таких колбеках ми можемо заінжектити спрінгові депенденсі. Крім того це дозволяє зберігати закешований контекст - @ContextConfiguration - він обов'язковий
//@ExtendWith(SpringExtension.class)

// Наступна анотація обов'язкова для вказування який контекст ми запустимо для тестів.
// АЛЕ цього недостатньо, бо потрібно вказати ще й проперті файл і якщо це YAML, то @PropertySource(...yaml) над AppRunner не допоможе, як і
// @TestPropertySource(...yaml) над цим класом, бо ці проперті не працюють з YAML (тільки BOOT).
// Щоб це працювало, потрібно ще одну властивість вказати contextInitializers=ConfigDataApplicationContextInitializer.class
//@ContextConfiguration(classes = ApplicationRunner.class, initializers = ConfigDataApplicationContextInitializer.class)

// Дивитись REFERENCE 1
// Але писати цю анотацію над кожним класом не те що ми хочемо. Тому в spring.properties ми можемо виставити spring.test.constructor.autowire.mode=all
@RequiredArgsConstructor
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)

// Що робити якщо нам потрібно видозмінити певні проперті з нашого YAML? Перше що спадає на думку - добавити такий же application.yaml в тестову ієрархію,
// АЛЕ через такий підхід оригінальний файл проігнорується і зчитуватись буде лише тестовий (бо таке ж саме ім'я), тоді прийдеться перевизначати всі проперті
// в тестовтому файлі. Це не те чого ми хотіли добитись (ми хотіли базу перевизначити тільки БД, наприклад).
// Аби мати новий файл з лише перевизначеними з основного пропертями, треба використати профілі -
// замість цілого application.yaml в тестовій ієрархії додаємо профільний application-test.yaml і після
// цього пишемо його в наступну анотацію (або в @SpringBootTest(args=...) але не варто).
//@ActiveProfiles("test")

// Всі верхні анотації (крім профілів, але і їх можна передати як агрументи в цю анотацію, але не варто) можуть бути замінені
// на одну наступну. Вона включає інтеграцію спрінга в JUnit 5 через @ExtendWith(SpringExtension.class)
// Та той же функціонал для YAML
// АЛЕ що важливіше - тут є автоматичне сканування конфігів - автоматично знайде єдиний @SpringBootApplication клас і візьме його за контекст. Але й це можна вказати в атрибуті classes
//@SpringBootTest

// Ми б хотіли спростити все максимально. Тому можемо ввести свою анотацію для інтеграційних тестів, яка міститиме @SpringBootTest та @ActiveProfiles("test").
// Пояснення - читати все зверху.
@IT


// Далі спробуємо додати ще один клас тестів, але помітимо його не кастомною анотацією, а просто @SpringBootTest
// Тест відпрацює, АЛЕ в логах ми побачимо що запустилось 2 спрінг контекста (легко помітити по візерунку SPRING)
// Це через те, що спрінг автоматично визначає чи можна перевикористати контекст. У даному випадку в кастомі є профіль, тому контексти різні.
// ЯК ТІЛЬКИ ми використаємо усюди @IT, або вручну впишемо в другий тестовий клас те ж що і в @IT, тоді контекст буде один,
// а це означає, що контекст був використаний ІСНУЮЧИЙ.
// Ми все одно можемо щось перевизначити за необхідності АЛЕ ЦЕ ЗРОБИТЬ ОКРЕМИЙ КОНТЕКСТ (заінжектити інший бін...). Робиться це @MockBean(name="назва біна")
// над полем яке треба перевизначити.
// Далі можна створити окрему @TestConfiguration class TestApplicationRunner, де перевизначити для всіх тестів якісь біни як згадано раніше й заюзати цей клас в @SpringBootTest(classes)
// Краще це подивитись у відео (останній урок стартера тестів)


public class UnitServiceIT {
    public static final int ID = 3;

    // REFERENCE 1
    // Як же позбутись @Autowire та інжектити через конструктор? Додатковою анотацією над класом @TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
//    @Autowired
    private UnitService unitService;
    //    @Autowired
    private DatabaseProperties databaseProperties;

    @Test
    void findById() {
        Optional<UnitReadDto> actualResult = unitService.findById(ID);

        Assertions.assertTrue(actualResult.isPresent());

        UnitReadDto expected = UnitReadDto.builder().id(ID).name("UnitName").managedByAdmin(false).build();
        actualResult.ifPresent(actual -> Assertions.assertEquals(expected, actual));
    }
}
