package familyapp.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.ControllerAdvice;

import com.sun.jersey.api.client.ClientResponse.Status;

import familyapp.rest.ResponseWrapper;

//@ControllerAdvice
public class BusinessExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<BusinessException> {
	
	@Override
	public Response toResponse(final BusinessException ex) {
		return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON_TYPE).entity(ResponseWrapper.error(ex)).build();
	}

	

}
