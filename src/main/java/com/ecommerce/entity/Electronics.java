package com.ecommerce.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.ecommerce.customAnnotation.UniqueField;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "electronics")
@Data
public class Electronics implements Serializable {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy") })

	@Column(name = "id", nullable = false)
	private String id;

	@Column(name = "title", nullable = false, unique = true)
	@NotBlank(message = "Please fill the title property properly.")
	@NotNull(message = "title value should not be null or empty.")
	@UniqueField(message = "Please enter unique value for title.")
	private String title;

	@NotBlank(message = "Please fill the description property properly.")
	@Column(name = "description", nullable = false)
	@NotNull(message = "Description value should not be null or empty.")
	private String description;

	@Column(name = "rating", nullable = false)
	@Min(value = 0, message = "Rating should be greater than and equal to 0")
	@Max(value = 5, message = "Rating should be less than and equal 5")
	@NotNull(message = "Rating value should not be null or empty")
	private Short rating;

	@Column(name = "no_of_people_rates", nullable = false)
	@Positive(message = "Please enter the positive number for no_of_people_rates.")
	@NotNull(message = "Number of people value should not be null or empty.")
	private Integer noOfPeopleRates;

	@Column(name = "final_price", nullable = false)
	@Positive(message = "Please enter the positive number for final price.")
	@NotNull(message = "final price value should not be null or empty.")
	private Integer finalPrice;

	@Column(name = "original_price", nullable = false)
	@Positive(message = "Please enter the positive number for original price.")
	@NotNull(message = "Original price value should not be null or empty.")
	private Integer originalPrice;

	@Column(name = "discounted_price", nullable = false)
	@NotBlank(message = "Please valid discount price eg: x off")
	private String discountedPrice;

	@OneToMany(mappedBy = "electronics",fetch = FetchType.EAGER,cascade = {CascadeType.DETACH, CascadeType.PERSIST,CascadeType.REFRESH},orphanRemoval = true)
	@JsonManagedReference
	private List<FileDetails> fileDetails = new ArrayList<>();

	@Column(name = "created_date",nullable = false)
	@ReadOnlyProperty
	private LocalDateTime createdDate = LocalDateTime.now(Clock.systemUTC());

	@Column(name = "updated_date", nullable = false)
	@ReadOnlyProperty
	private LocalDateTime updatedDate =LocalDateTime.now(Clock.systemUTC());

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Short getRating() {
		return rating;
	}

	public void setRating(Short rating) {
		this.rating = rating;
	}

	public Integer getNoOfPeopleRates() {
		return noOfPeopleRates;
	}

	public void setNoOfPeopleRates(Integer noOfPeopleRates) {
		this.noOfPeopleRates = noOfPeopleRates;
	}

	public Integer getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Integer finalPrice) {
		this.finalPrice = finalPrice;
	}

	public Integer getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Integer originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(String discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void createdDateAndUpdatedDate(LocalDateTime createdDate, LocalDateTime updatedDate){
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	public List<FileDetails> getFileDetails() {
		return fileDetails;
	}

	public void setFileDetails(List<FileDetails> fileDetails) {
		this.fileDetails = fileDetails;
	}

	@Override
	public String toString() {
		return "Electronics{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", rating=" + rating +
				", noOfPeopleRates=" + noOfPeopleRates +
				", finalPrice=" + finalPrice +
				", originalPrice=" + originalPrice +
				", discountedPrice='" + discountedPrice + '\'' +
				", createdDate=" + createdDate +
				", updatedDate=" + updatedDate +
				'}';
	}
}
