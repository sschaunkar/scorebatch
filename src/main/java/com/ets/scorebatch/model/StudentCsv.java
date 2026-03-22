package com.ets.scorebatch.model;

public class StudentCsv {
	//name	nationality	city	latitude	longitude	gender
private Long Id;
private String name;
private String nationality;
private String city;
private String latitude;
private String longitude;
private String gender;

public Long getId() {
	return Id;
}
public void setId(Long id) {
	Id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getNationality() {
	return nationality;
}
public void setNationality(String nationality) {
	this.nationality = nationality;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getLatitude() {
	return latitude;
}
public void setLatitude(String latitude) {
	this.latitude = latitude;
}
public String getLongitude() {
	return longitude;
}
public void setLongitude(String longitude) {
	this.longitude = longitude;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
@Override
public String toString() {
	return "StudentCsv [Id=" + Id + ", name=" + name + ", nationality=" + nationality + ", city=" + city + ", latitude="
			+ latitude + ", longitude=" + longitude + ", gender=" + gender + "]";
}

}
