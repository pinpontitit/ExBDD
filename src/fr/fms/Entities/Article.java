package fr.fms.Entities;

public class Article {
	private int id;
	private String description;
	private String brand;
	private double unitaryPrice;
	private String catName;

	public Article(int id, String description, String brand, double unitaryPrice) {
		this.id = id;
		this.description = description;
		this.brand = brand;
		this.unitaryPrice = unitaryPrice;
	}
	
	public Article(String description, String brand, double unitaryPrice) {
		this.description = description;
		this.brand = brand;
		this.unitaryPrice = unitaryPrice;
	}

	public Article(int id, String description, String brand, double unitaryPrice, String catName) {
		this.id = id;
		this.description = description;
		this.brand = brand;
		this.unitaryPrice = unitaryPrice;
		this.catName = catName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getUnitaryPrice() {
		return unitaryPrice;
	}

	public void setUnitaryPrice(double unitaryPrice) {
		this.unitaryPrice = unitaryPrice;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}


}
