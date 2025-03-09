package html.aicreator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import html.aicreator.entity.HtmlQuery;

@Repository
public interface HtmlQueryRepository extends JpaRepository<HtmlQuery, Long> {
}
