package com.eyogo.http.config;

import com.eyogo.http.service.UnitService;
import com.eyogo.http.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Slf4j
@Configuration
/* Ця анотація обробляється в першу чергу завдяки BFPP ConfigurationClassPostProcessor, який автоматично додається
завдяки new AnnotationConfigApplicationContext(ApplicationConfiguration.class).
У випадку ж використання XML і new ClassPathXmlApplicationContext("application.xml") цей BFPP підключається вручну або завдяки <context:annotation-config/>*/
//@PropertySource("classpath:application.properties")
//@ComponentScan(basePackages = "com.eyogo.http")
// @Import(MyWebConfig.class) -додає до поточного конфіга вказані тут, як правило вписують те що не скануємо в цьому, але потрібне.
// @ImportResource("...app.xml") - додає xml конфіг і дає можливість змішувати XML, annotation, java-based
public class ApplicationConfiguration {
    // Один з ключових моментів тут це те, що @Configuration класи теж є звичайними бінами, тому ми можемо й сюди заінжектити щось.
    // Така необхідність виникає лише коли в створюваних бінах використовується щось спільне, у інших випадках просто інжектимо в параметри бін методів.

    // Але як же ці біни створюються? Дуже просто - не через конструктор і не через сеттера, а через фабричні методи (Service Locator - антипаттерн підхід)
    // Фактино якщо глянути в створені bean definition, то у них буде виставлено factoryBeanName="applicationContext", factoryMethodName="userService2" -
    // тобто вказівка хто відповідальний за створення - який бін і який метод в ньому

    // У класа ConfigurationClassBeanDefinitionReader, відповідального за зчитування @Configuration є поле Environment -
    // фактично агрегатор усіх non/system properties і використовувати його слід тільки в @Configuration, бо в звичайних бінах ми можемо через @Value все дістати.
    // Крім цього Environment керує профілями. Можемо поставити умову над біном або й над всією конфігурацією @Profiles("web|prod").
    // 1. Активуємо через проперті spring.profiles.active=web або
    // 2. в коді через appContext.getEnvironment().setActiveProfiles("web", "prod") АЛЕ, так як на даний момент контекст
    // уже проініціалізований (конструктор з параметрами класу конфігів реєструє все і в кінці викликає refresh(), що означає що контекст уже готовий і після ми не можемо застосувати профілі),
    // ТРЕБА генерувати пустий контекст, зареєструвати наші конфіг класи (це ще нічого не зробить до виклику refresh() - читай javadoc register()), Дотати профілі й РЕФРЕШНУТИ ЙОГО appContext.refresh()

    // Завдяки Java-based конфігу ми тепер можемо створити не тільки 1 бін. Бо @Component над класом давав лише таку можливість.
//    @Bean
//    public UserService userService2(@Value("${db.user}") String user) {
//        log.info(user);
//        return new UserService();
//    }

//    @Bean
//    public UnitService unitService2(@Qualifier("userService2") UserService userService2) {
//        log.info("1. Injected UserService " + userService2);
//        return new UnitService();
//    }

//    @Bean
//    public UnitService unitService3(@Value("${db.user}") String user) {
//        // Не найкраща ідея юзати метод коли є параметри, бо треба його передавати тепер в цей метод.
//        // Працює приблизно ось так - над цим класом робиться проксі, коли викликається метод перевіряється в проксі методі
//        // чи був уже створений цей бін. Якщо був - то просто поверни його. Саме тому, що б я не передавав параметром далі
//        // в викликах userService("2"), повернеться бін з тим параметром яким він був попередньо створений.
//        UserService userService = userService2(user);
//        log.info("2. Injected UserService " + userService);
//        log.info("3. Injected UserService " + userService2("2")); // Не трігерне вивід з userService2(...), бо бін уже створений і проксі не запустить реальний метод.
//        return new UnitService();
//    }
}

