package com.task.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productTransaction")
public class ProductTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private Double price;
	private LocalDate dateOfSale;
	private Boolean isSold;
	private String category;

//----------------------------------------------------------------------------------------------------------------------
	// Generate Getter and Setter Method

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public LocalDate getDateOfSale() {
		return dateOfSale;
	}

	public void setDateOfSale(LocalDate dateOfSale) {
		this.dateOfSale = dateOfSale;
	}

	public Boolean getIsSold() {
		return isSold;
	}

	public void setIsSold(Boolean isSold) {
		this.isSold = isSold;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

//----------------------------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		return "ProductTransaction [id=" + id + ", title=" + title + ", description=" + description + ", price=" + price
				+ ", dateOfSale=" + dateOfSale + ", isSold=" + isSold + ", category=" + category + "]";
	}
//----------------------------------------------------------------------------------------------------------------------
}
