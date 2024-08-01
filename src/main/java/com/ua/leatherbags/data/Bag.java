package com.ua.leatherbags.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bag")
public class Bag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@Schema(accessMode = Schema.AccessMode.READ_ONLY,
			example = "0")
	private long id;

	@Column(name = "first_name")
	@Schema(example = "John")
	private String firstName;

	@Column(name = "last_name")
	@Schema(example = "Doe")
	private String lastName;

	@Column(name = "middle_name")
	@Schema(example = "")
	private String middleName;

	@Column(name = "phone_num")
	@Schema(example = "+380000000000")
	private String phoneNum;

	@Column(name = "order_date")
	private LocalDateTime orderDate;

	@Column(name = "modify_date")
	private LocalDateTime modifyDate;

	@Column(name = "color")
	@Schema(example = "Black")
	private String color;

	@Column(name = "size")
	@Schema(example = "XL")
	private String size;

	@Column(name = "type")
	@Schema(example = "Bag Type 1")
	private String type;

	@Column(name = "key_holder")
	@Schema(example = "false")
	private boolean keyHolder;

	@Column(name = "city")
	@Schema(example = "Lviv, Lvivska Obl.")
	private String city;

	@Column(name = "department")
	@Schema(example = "Department 54, Rusovyx St., 2")
	private String department;

	@Column(name = "price")
	@Schema(example = "199.99")
	private double price;

	@Column(name = "status")
	@Schema(accessMode = Schema.AccessMode.READ_ONLY,
			example = "0")
	@JsonIgnore
	private byte status;

	@Schema(example = "Processing")
	public String getStatusName() {
		return OrderStatus.getName(status);
	}
}
