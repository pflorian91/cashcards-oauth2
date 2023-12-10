package com.webgenerals.cashcards;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * The cash card REST API
 */
@RestController
@RequestMapping("/cashcards")
public class CashCardController {
	private CashCardRepository cashCardRepository;

	public CashCardController(CashCardRepository cashCardRepository) {
		this.cashCardRepository = cashCardRepository;
	}

	@PostAuthorize("returnObject.body.owner == authentication.name")
	@GetMapping("/{requestedId}")
	public ResponseEntity<CashCard> findById(@PathVariable Long requestedId) {
		return this.cashCardRepository.findById(requestedId)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<CashCard> createCashCard(@RequestBody CashCardRequest cashCardRequest, UriComponentsBuilder ucb, @CurrentOwner String owner) {
		CashCard cashCard = new CashCard(cashCardRequest.amount(), owner);
		CashCard savedCashCard = this.cashCardRepository.save(cashCard);
		URI locationOfNewCashCard = ucb
				.path("cashcards/{id}")
				.buildAndExpand(savedCashCard.id())
				.toUri();
		return ResponseEntity.created(locationOfNewCashCard).body(savedCashCard);
	}

	@GetMapping
	public ResponseEntity<Iterable<CashCard>> findAll() {
		return ResponseEntity.ok(this.cashCardRepository.findAll());
	}
}