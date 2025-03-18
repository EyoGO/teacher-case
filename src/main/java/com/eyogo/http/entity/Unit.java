package com.eyogo.http.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Unit implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String unitName;
    private Integer parentId;
    private Boolean managedByAdmin;
}

