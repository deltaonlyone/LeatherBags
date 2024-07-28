package com.ua.leatherbags.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class 	Bag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "phone_num")
	private String phoneNum;

	@Column(name = "order_date")
	private LocalDateTime orderDate;

	@Column(name = "modify_date")
	private LocalDateTime modifyDate;

	@Column(name = "color")
	private String color;

	@Column(name = "size")
	private String size;

	@Column(name = "type")
	private String type;

	@Column(name = "city")
	private String city;

	@Column(name = "department")
	private int department;

	@Column(name = "status")
	@JsonIgnore
	private byte status;

	public String getStatusName() {
		return OrderStatus.getName(status);
	}
}
