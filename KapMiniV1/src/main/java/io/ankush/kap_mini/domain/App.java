package io.ankush.kap_mini.domain;

import io.ankush.kap_mini.model.Isactive;
import io.ankush.kap_mini.model.Isproduction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class App {

    @Id
    @Column(nullable = false, updatable = false, columnDefinition = "char(36)")
    @GeneratedValue
    @UuidGenerator
    private UUID appId;

    @Column(nullable = false)
    private String name;

    @Column(
            nullable = false,
            name = "\"description\"",
            columnDefinition = "longtext"
    )
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Isactive isActive;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Isproduction isProduction;

    @Column
    private LocalDateTime deleteAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subdomain_id_id", nullable = false)
    private SubDomain subdomainId;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
