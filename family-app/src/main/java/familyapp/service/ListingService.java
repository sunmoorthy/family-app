package familyapp.service;

import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import familyapp.model.VenueResponse;

@Service
public class ListingService {

	public VenueResponse getListings()  {
		return getVenuList();
//		FoursquareApi api = new FoursquareApi("ZFANJJZ5JWVMGOR4Z4FZK100LTHW2NKRQPIRMGCLTGOYYISR", "I11J40GC0LSTAVZCEKSVTNRWWYAN4HSTJXJT5ZOMRPSV4DUX", "");
//		api.setVersion("20150628");
//		// After client has been initialized we can make queries.
//		Map<String,String> map = new HashMap<String, String>(); 
//		map.put("ll", "40.7,-74");
//	    Result<VenuesSearchResult> result = api.venuesSearch(map);
//	    
//	    if (result.getMeta().getCode() == 200) {
//	      // if query was ok we can finally we do something with the data
//	      for (CompactVenue venue : result.getResult().getVenues()) {
//	        // TODO: Do something we the data
//	        System.out.println(venue.getName());
//	      }
//	    } else {
//	      // TODO: Proper error handling
//	      System.out.println("Error occured: ");
//	      System.out.println("  code: " + result.getMeta().getCode());
//	      System.out.println("  type: " + result.getMeta().getErrorType());
//	      System.out.println("  detail: " + result.getMeta().getErrorDetail()); 
//	    }
		
	}
	
	public VenueResponse getVenuList() {
		try{
			ClientConfig config = new DefaultClientConfig();
			//config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			//config.getClasses().add(VenueResponse.class);
			Client client = Client.create(config);
			WebResource webResource = client.resource("https://api.foursquare.com/v2/venues/search?ll=40.7,-74&client_id=ZFANJJZ5JWVMGOR4Z4FZK100LTHW2NKRQPIRMGCLTGOYYISR&" +
					"client_secret=I11J40GC0LSTAVZCEKSVTNRWWYAN4HSTJXJT5ZOMRPSV4DUX&v=20150628");
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "
			+ response.getStatus());
		}
		String str = response.getEntity(String.class).toString();
		//VenueResponse str = webResource.get(new GenericType<VenueResponse>(){});
		VenueResponse venueResponse = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(str, VenueResponse.class );
		System.out.println(venueResponse);
		return venueResponse ;
		}catch(Exception e){
			System.out.println(e);
		}
		return new VenueResponse();
	}

}
