package com.eyogo.http.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

// ДУЖЕ ВАЖЛИВА ІНФОРМАЦІЯ - парсінг проперті файлів у класи (біни)

// Це другий спосіб ініціалізації проперті біна (перший в якомусь конфіг класі створити бін з цим типом з пустими проперті з цією ж анотацією)
// Але відповідно до першого способу, цей запаршений проперті повинен бути біном, тому просто @ConfigurationProperties("db") недостатньо.
// Ми ПОВИННІ або зробити цей клас компонентом, або поставити над якимось класом @ConfigurationPropertiesScan
// (в основного класу, щоб під ним шукало як і будь-які біни, тільки тепер проперті конфіги)
//@ConfigurationProperties(prefix = "db")
//@Data
//@NoArgsConstructor

// DEPRECATED інформація далі (з Boot 3.0.0 не потрібно): АЛЕ ЩОБ ВИКОРИСТОВУВАТИ КОНСТРУКТОР (хочемо IMMUTABLE - final поля) треба ще додати @ConstructorBinding.
// Якщо ж ми використовуємо record, то спрінг сам це зрозуміє і без анотації
@Value
@ConfigurationProperties(prefix = "db")
public class DatabaseProperties {

    String driver;
    String user;
    String password;
    String url;
    String schema;
    Map<String, Object> properties;
    PoolProperties pool;
    List<PoolProperties> pools;

    @Value
    public static class PoolProperties {
        Integer size;
        Integer timeout;
    }
}
