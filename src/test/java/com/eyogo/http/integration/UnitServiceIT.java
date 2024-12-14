package com.eyogo.http.integration;

import com.eyogo.http.ApplicationRunner;
import com.eyogo.http.dto.GetUnitDto;
import com.eyogo.http.entity.Unit;
import com.eyogo.http.service.UnitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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


// Що робити якщо нам потрібно видозмінити певні проперті з нашого YAML. Перше що спадає на думку - добавити такий же application.yaml в тестову ієрархію,
// АЛЕ через такий підхід оригінальний файл проігнорується і зчитуватись буде лише тестовий (бо таке ж саме ім'я), тоді прийдеться перевизначати всі проперті
// в тестовтому файлі. Це не те чого ми хотіли добитись (ми хотіли базу перевизначити тільки наприклад).
// Аби мати новий файл з лише перевизначеними з основного пропертями, треба використати профілі -
// замість цілого application.yaml в тестовій ієрархії додаємо профільний application-test.yaml і після
// цього пишемо його в наступну анотацію (або в @SpringBootTest(args=...) але не варто).
@ActiveProfiles("test")
//TODO віддебажити


// Всі верхні анотації (крім профілів, але й їх можна передати як агрументи в цю анотацію, але не варто) можуть бути замінені
// на одну наступну. Вона включає інтеграцію спрінга в JUnit 5 через @ExtendWith(SpringExtension.class)
// Та той же функціонал для YAML
// АЛЕ що важливіше - тут є автоматичне сканування конфігів - автоматично знайде єдиний @SpringBootApplication клас і візьме його за контекст. Але й це можна вказати в атрибуті classes
@SpringBootTest
public class UnitServiceIT {
    public static final int ID = 3;

    @Autowired
    private UnitService unitService;

    @Test
    void findById() {
        Optional<GetUnitDto> actualResult = unitService.findById(ID);

        Assertions.assertTrue(actualResult.isPresent());

        GetUnitDto expected = GetUnitDto.builder().id(ID).name("UnitName").managedByAdmin(false).build();
        actualResult.ifPresent(actual -> Assertions.assertEquals(expected, actual));
    }
}
