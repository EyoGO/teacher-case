package com.eyogo.http.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertiesUtilTest {

    @ParameterizedTest
    @MethodSource("getPropertyArguments")
    void checkGet(String key, String expectedValue) {
        String actualValue = PropertiesUtil.get(key);

        assertEquals(expectedValue, actualValue);
    }

    static Stream<Arguments> getPropertyArguments() {
        return Stream.of(
                Arguments.of("db.url", "jdbc:postgresql://localhost:5432/postgres"),
                Arguments.of("db.schema", "teacher-case"),
                Arguments.of("db.user", "postgres"),
                Arguments.of("db.password", "root"),
                Arguments.of("db.driver", "org.postgresql.Driver")
                );
    }
}