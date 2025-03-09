package ai.creator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ai.creator.entity.HtmlQuery;

@Repository
public interface HtmlQueryRepository extends JpaRepository<HtmlQuery, Long> {
}
