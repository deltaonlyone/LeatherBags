package com.ua.leatherbags.dao;

import com.ua.leatherbags.data.Bag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BagRepository
	extends JpaRepository<Bag, Long> {
	Page<Bag> findByStatus(byte status, PageRequest pageRequest);

	@Query("SELECT b FROM Bag b WHERE status != -1 AND status != 3")
	Page<Bag> findNotCompleted(PageRequest pageRequest);
}
