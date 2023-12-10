package com.webgenerals.cashcards;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * The cash card repository.
 *
 * <p>Spring Data JDBC provides the implementation for this interface
 * at runtime.
 */
public interface CashCardRepository extends CrudRepository<CashCard, Long> {
	Iterable<CashCard> findByOwner(String owner);

	@Query("select * from cash_card cc where cc.owner = :#{authentication.name}")
	Iterable<CashCard> findAll();
}
