package com.eyogo.http.projection;

import org.springframework.beans.factory.annotation.Value;

public interface UserNameProjection {
    Integer getId();
    String getFirstName();
    String getLastName();

    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();
}
