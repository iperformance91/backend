package com.example.loginregister.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stored_images")
public class StoredImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "img", nullable = false, length = 50000)
    private String img;

    @Column(name = "options", nullable = true)
    private String options;

    @Column(name = "description", nullable = true)
    private String description;
    
    @Column(name = "price", nullable = true)
    private String price;
    
    @Column(name = "product", nullable = true)
    private String product;
    
    @Column(name = "sub_product", nullable = true)
    private String subProduct;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSubProduct() {
        return subProduct;
    }

    public void setSubProduct(String subProduct) {
        this.subProduct = subProduct;
    }

	@Override
	public String toString() {
		return "StoredImage [id=" + id + ", categoryName=" + categoryName + ", name=" + name + ", img=" + img
				+ ", options=" + options + ", description=" + description + ", price=" + price + ", product=" + product
				+ ", subProduct=" + subProduct + "]";
	}

   
}

