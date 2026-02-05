package dev.java10x.gamesearch.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 100)
    private String genre;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    private double rating;

    private String description;

    private String developer;

    private String publisher;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(name = "game_category",
    joinColumns = @JoinColumn(name = "game_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @ManyToMany
    @JoinTable(name = "game_platform",
    joinColumns = @JoinColumn(name = "game_id"),
    inverseJoinColumns = @JoinColumn(name = "platform_id"))
    private List<Platform> platforms;
}
