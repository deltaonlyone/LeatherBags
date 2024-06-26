package com.ua.leatherbags.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrderStatus {
	NOT_COMPLETED((byte) -2, "Не отримані"),
	ALL((byte) -3, "Усі"),
	ORDERED((byte) 0, "Створене"),
	CONFIRMED((byte) 1, "Підтверджене"),
	DELIVERING((byte) 2, "Доставляється"),
	COMPLETED((byte) 3, "Отримане"),
	CANCELED((byte) -1, "Скасовано");

	private final byte value;
	private final String name;

	public static String getName(byte value) {
		for (OrderStatus status : values()) {
			if (status.value == value) {
				return status.name;
			}
		}
		return "";
	}

	public static OrderStatus getByValue(byte value) {
		for (OrderStatus status : values()) {
			if (status.value == value) {
				return status;
			}
		}
		return COMPLETED;
	}

	public static OrderStatus next(byte value) {
		if (value == COMPLETED.value) {
			return COMPLETED;
		}
		byte next = (byte) (value + 1);
		return getByValue(next);
	}
}
