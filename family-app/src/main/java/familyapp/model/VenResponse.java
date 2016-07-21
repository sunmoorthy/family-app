package familyapp.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class VenResponse {

	private List<Venue> venues;
	private boolean confident;

	public List<Venue> getVenues() {
		return venues;
	}
	

	public void setVenues(List<Venue> venues) {
		this.venues = venues;
	}
	
	public String toString() {
		return ""+confident;
	}
	
	
	
}
