package com.ua.leatherbags.controller;

import com.ua.leatherbags.dao.SliceWrapper;
import com.ua.leatherbags.data.Bag;
import com.ua.leatherbags.data.OrderStatus;
import com.ua.leatherbags.service.BagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bags")
@RequiredArgsConstructor
public class BagController {
	private final BagService bagService;

	@GetMapping
	public SliceWrapper<Bag> getBags(@RequestParam byte status,
									 @RequestParam int page,
									 @RequestParam int size) {
		return bagService.findBagsByStatus(status, page, size);
	}

	@PostMapping
	public Bag addBag(@RequestBody Bag bag) {
		bag = bagService.saveBag(bag);

		return bag;
	}

	@PutMapping("/proceed")
	public Bag proceedBag(@RequestBody int id) {
		return bagService.proceedOrder(id);
	}

	@PutMapping("/cancel")
	public Bag cancelBag(@RequestBody int id) {
		return bagService.cancelOrder(id);
	}

	@GetMapping("/statuses")
	public OrderStatus[] getOrderStatuses() {
		return OrderStatus.values();
	}
}
