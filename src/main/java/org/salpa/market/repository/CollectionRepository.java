package org.salpa.market.repository;

import org.salpa.market.entity.skin.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {
}
