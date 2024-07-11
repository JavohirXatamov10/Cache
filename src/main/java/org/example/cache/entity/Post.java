package org.example.cache.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String title;
    private String body;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Attachment photo;

}