package com.ekar.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_audit_event")
public class EventAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime eventTime;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String document;

    @Column(nullable = false)
    private String eventType;

    public enum EventType {
        NEW_SETTINGS,
        END_PROCESSING,
        NEW_COUNTER_VALUE
    }
}
