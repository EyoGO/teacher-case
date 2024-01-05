package com.eyogo.http.dto;

import com.eyogo.http.entity.Gender;
import com.eyogo.http.entity.Role;
import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class GetUserDto {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final String email;

    @JsonAdapter(LocalDateTypeAdapter.class)//TODO maybe remove
    private final LocalDate birthday;
    private final String image;
    private final Role role;
    private final Gender gender;

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

    GetUserDto(Integer id, String firstName, String lastName, String email, LocalDate birthday, String image, Role role, Gender gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.image = image;
        this.role = role;
        this.gender = gender;
    }

    public static GetUserDtoBuilder builder() {
        return new GetUserDtoBuilder();
    }

    public Integer getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public String getImage() {
        return this.image;
    }

    public Role getRole() {
        return this.role;
    }

    public Gender getGender() {
        return this.gender;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof GetUserDto)) return false;
        final GetUserDto other = (GetUserDto) o;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) return false;
        final Object this$lastName = this.getLastName();
        final Object other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$birthday = this.getBirthday();
        final Object other$birthday = other.getBirthday();
        if (this$birthday == null ? other$birthday != null : !this$birthday.equals(other$birthday)) return false;
        final Object this$image = this.getImage();
        final Object other$image = other.getImage();
        if (this$image == null ? other$image != null : !this$image.equals(other$image)) return false;
        final Object this$role = this.getRole();
        final Object other$role = other.getRole();
        if (this$role == null ? other$role != null : !this$role.equals(other$role)) return false;
        final Object this$gender = this.getGender();
        final Object other$gender = other.getGender();
        if (this$gender == null ? other$gender != null : !this$gender.equals(other$gender)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $birthday = this.getBirthday();
        result = result * PRIME + ($birthday == null ? 43 : $birthday.hashCode());
        final Object $image = this.getImage();
        result = result * PRIME + ($image == null ? 43 : $image.hashCode());
        final Object $role = this.getRole();
        result = result * PRIME + ($role == null ? 43 : $role.hashCode());
        final Object $gender = this.getGender();
        result = result * PRIME + ($gender == null ? 43 : $gender.hashCode());
        return result;
    }

    public String toString() {
        return "GetUserDto(id=" + this.getId() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", email=" + this.getEmail() + ", birthday=" + this.getBirthday() + ", image=" + this.getImage() + ", role=" + this.getRole() + ", gender=" + this.getGender() + ")";
    }

    public static class GetUserDtoBuilder {
        private Integer id;
        private String firstName;
        private String lastName;
        private String email;
        private LocalDate birthday;
        private String image;
        private Role role;
        private Gender gender;

        GetUserDtoBuilder() {
        }

        public GetUserDtoBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public GetUserDtoBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public GetUserDtoBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public GetUserDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public GetUserDtoBuilder birthday(LocalDate birthday) {
            this.birthday = birthday;
            return this;
        }

        public GetUserDtoBuilder image(String image) {
            this.image = image;
            return this;
        }

        public GetUserDtoBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public GetUserDtoBuilder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public GetUserDto build() {
            return new GetUserDto(this.id, this.firstName, this.lastName, this.email, this.birthday, this.image, this.role, this.gender);
        }

        public String toString() {
            return "GetUserDto.GetUserDtoBuilder(id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", email=" + this.email + ", birthday=" + this.birthday + ", image=" + this.image + ", role=" + this.role + ", gender=" + this.gender + ")";
        }
    }
}
