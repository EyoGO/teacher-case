package com.eyogo.http.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;

@UtilityClass
public class ConnectionManager {

    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DRIVER_KEY = "db.driver";
    private static final String SCHEMA_KEY = "db.schema";

    static {
        loadDriver();
    }

    private static void loadDriver() {
//        try {
//            Class.forName(PropertiesUtil.get(DRIVER_KEY));
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }

    @SneakyThrows
    public static Connection get() {
        return null;
//        return DriverManager.getConnection(//TODO       &        in case it was not first parameter, SHOULD fix
//                PropertiesUtil.get(URL_KEY).concat("?").concat("currentSchema=").concat(PropertiesUtil.get(SCHEMA_KEY)),
//                PropertiesUtil.get(USER_KEY),
//                PropertiesUtil.get(PASSWORD_KEY)
//        );
    }
}
