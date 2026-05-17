package sdproject.url_shortener.URLShortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sdproject.url_shortener.URLShortener.entity.URLMapping;

@Repository
public interface URLRepository extends JpaRepository<URLMapping, Long> {}