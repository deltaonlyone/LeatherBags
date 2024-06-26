package com.ua.leatherbags.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SliceWrapper<T> {
	private List<T> content;
	private boolean hasNext;

	public SliceWrapper(Slice<T> slice) {
		this.content = slice.getContent();
		this.hasNext = slice.hasNext();
	}
}
