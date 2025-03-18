package com.eyogo.http.dto;

import com.eyogo.http.entity.Gender;
import com.eyogo.http.entity.Role;
import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Value
@Builder
@RequiredArgsConstructor
public class UserReadDto {
    Integer id;
    String firstName;
    String lastName;
    String email;

    @JsonAdapter(LocalDateTypeAdapter.class)
    LocalDate birthday;
    String image;
    Role role;
    Gender gender;

    static class LocalDateTypeAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public JsonElement serialize(final LocalDate date, final Type typeOfSrc,
                                     final JsonSerializationContext context) {
            return new JsonPrimitive(date.format(formatter));
        }

        @Override
        public LocalDate deserialize(final JsonElement json, final Type typeOfT,
                                     final JsonDeserializationContext context) throws JsonParseException {
            return LocalDate.parse(json.getAsString(), formatter);
        }
    }
}
