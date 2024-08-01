package com.ua.leatherbags.dao;

import com.ua.leatherbags.data.Bag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageWrapper<T> {
	@ArraySchema(schema = @Schema(implementation = Bag.class))
	private List<T> content;
	@Schema(example = "1")
	private long totalElements;
	@Schema(example = "1")
	private int totalPages;

	public PageWrapper(Page<T> page) {
		this.content = page.getContent();
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
	}
}
