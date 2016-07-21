package familyapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import familyapp.model.VenueResponse;
import familyapp.service.ListingService;

@RestController
@RequestMapping("/listing")
public class ListingResource {
	@Autowired
	ListingService listingService;

	@RequestMapping(value="/locations" ,method={RequestMethod.GET} )
	public ResponseWrapper<VenueResponse>  getListings() {
		return  new ResponseWrapper<VenueResponse>(listingService.getListings());
	}

}
