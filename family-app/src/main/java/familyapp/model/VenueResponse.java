package familyapp.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VenueResponse {
 private Meta meta;

public Meta getMeta() {
	return meta;
}

public void setMeta(Meta meta) {
	this.meta = meta;
}


private VenResponse response;

public VenResponse getResponse() {
	return response;
}

public void setResponse(VenResponse response) {
	this.response = response;
}

public String toString() {
	return this.meta + " " + this.response;
}

}
