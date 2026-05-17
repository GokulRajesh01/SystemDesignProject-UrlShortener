package sdproject.url_shortener.URLShortener.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@Table(name = "urls")
public class URLMapping {
    @Column(name = "url_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "actual_url", nullable = false)
    private String actualUrl;

    @Column(name = "Created_At")
    private Instant createdAt;

    @Column(name = "Expiry_Date")
    private Instant expiryDate;

    @PrePersist
    public void prePersist(){
        this.createdAt = Instant.now();
        this.expiryDate = this.createdAt.plus(3, ChronoUnit.DAYS);
    }
}
