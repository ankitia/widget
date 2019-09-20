package com.ia.modal;

/**
 * @author Ankit
 *
 */
public class MapsPlaceData {

	private int mapsId;
	private String name;
	private String query;
	private String categories;
	private String domain;
	private String reviews;
	private String phone;
	public int getMapsId() {
		return mapsId;
	}
	public void setMapsId(int mapsId) {
		this.mapsId = mapsId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getCategories() {
		return categories;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getReviews() {
		return reviews;
	}
	public void setReviews(String reviews) {
		this.reviews = reviews;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}