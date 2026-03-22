package org.salpa.market.repository;

import org.salpa.market.entity.news.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,Integer> {
}
