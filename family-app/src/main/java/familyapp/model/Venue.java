package familyapp.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Venue {

public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
private String id;
private String name;
private Location location;
private List<VenueCategory> categories;



public List<VenueCategory> getCategories() {
	return categories;
}
public void setCategories(List<VenueCategory> categories) {
	this.categories = categories;
}
public String toString() {
	return this.id + ""+ this.getName() + "" + this.getLocation();
}


}
