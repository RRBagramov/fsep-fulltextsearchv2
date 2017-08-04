package ru.fsep.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 12.07.2017
 *
 * @author Robert Bagramov.
 */
@Entity
@Table(name = "comment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "secondsfromstart", nullable = false)
    private Double secondsFromStart;

}
