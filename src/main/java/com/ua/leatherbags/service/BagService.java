package com.ua.leatherbags.service;

import com.ua.leatherbags.dao.BagRepository;
import com.ua.leatherbags.dao.PageWrapper;
import com.ua.leatherbags.data.Bag;
import com.ua.leatherbags.data.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BagService {
	private final BagRepository bagRepository;

	public PageWrapper<Bag> findBagsByStatus(byte status, int page, int size) {
		page--;
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());

		Page<Bag> resultPage;
		if (status == OrderStatus.NOT_COMPLETED.getValue()) {
			resultPage = bagRepository.findNotCompleted(pageRequest);
		} else if (status == OrderStatus.ALL.getValue()) {
			resultPage = bagRepository.findAll(pageRequest);
		} else {
			resultPage = bagRepository.findByStatus(status, pageRequest);
		}
		return new PageWrapper<>(resultPage);
	}

	public Bag saveBag(Bag bag) {
		bag.setId(0);
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
