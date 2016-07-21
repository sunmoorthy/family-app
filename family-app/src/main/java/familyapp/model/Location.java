package familyapp.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Location {

	private String address;
	private int distance;
	private String postalCode;
	private String city;
	private String country;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public String toString() {
		return this.address + " " + this.distance + " "+ this.postalCode + " " + this.city + " "+ this.country;
	}

}
