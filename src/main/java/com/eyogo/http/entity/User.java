package com.eyogo.http.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED) // Enables envers, property excludes related entities from auditing.
// In case we have List/Collection we need to add above it @NotAudited, otherwise we have to audit related
// We have to create Revision table and User audit table to make it work. But we also can enable ddl auto in test resources not to waste time.
// Besides that need to enable Envers in Audit configuration ->
public class User extends AuditingEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDate birthday;
    private String image;
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
