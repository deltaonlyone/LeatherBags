package com.ua.leatherbags.service;

import com.ua.leatherbags.dao.BagRepository;
import com.ua.leatherbags.dao.SliceWrapper;
import com.ua.leatherbags.data.Bag;
import com.ua.leatherbags.data.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BagService {
	private final BagRepository bagRepository;

	public SliceWrapper<Bag> findBagsByStatus(byte status, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());

		Slice<Bag> slice;
		if (status == OrderStatus.NOT_COMPLETED.getValue()) {
			slice = bagRepository.findNotCompleted(pageRequest);
		} else if (status == OrderStatus.ALL.getValue()) {
			slice = bagRepository.findAll(pageRequest);
		} else {
			slice = bagRepository.findByStatus(status, pageRequest);
		}
		return new SliceWrapper<>(slice);
	}

	public Bag saveBag(Bag bag) {
		bag.setStatus(OrderStatus.ORDERED.getValue());
		bag.setOrderDate(LocalDateTime.now());
		bag.setModifyDate(LocalDateTime.now());

		return bagRepository.save(bag);
	}

	public Bag proceedOrder(long id) {
		Bag bag = bagRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("There is no bag with id " + id));
		if (bag.getStatus() == OrderStatus.COMPLETED.getValue()
			|| bag.getStatus() == OrderStatus.CANCELED.getValue()) {
			throw new IllegalArgumentException("Can't proceed order, order already " + bag.getStatusName());
		}
		bag.setModifyDate(LocalDateTime.now());

		bag.setStatus(OrderStatus.next(bag.getStatus()).getValue());

		return bagRepository.save(bag);
	}

	public Bag cancelOrder(long id) {
		Bag bag = bagRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("There is no bag with id " + id));

		if (bag.getStatus() == OrderStatus.COMPLETED.getValue()) {
			throw new IllegalArgumentException("Can't cancel order, order already " + bag.getStatusName());
		}

		bag.setStatus(OrderStatus.CANCELED.getValue());
		bag.setModifyDate(LocalDateTime.now());

		return bagRepository.save(bag);
	}
}
