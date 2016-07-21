package familyapp.exception;

import javax.ws.rs.core.Response.Status;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
    public ResponseEntity<APIErrorResponse> serviceError(BusinessException e) {
       APIErrorResponse error = new APIErrorResponse();
       error.setErrorCode(HttpStatus.BAD_REQUEST.value());
       error.setMessage(e.getMessages().get(0).getMessage());
       error.setStatus("ERROR");
       return new ResponseEntity<APIErrorResponse>(error, HttpStatus.BAD_REQUEST);
     //  return new ResponseEntity<ResponseWrapper>(ResponseWrapper.error(e), HttpStatus.BAD_REQUEST);
       //return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON_TYPE).entity(ResponseWrapper.error(e)).build();
       
    }
	
	public class APIErrorResponse {
		int errorCode;
		String message;
		String status;
		public int getErrorCode() {
			return errorCode;
		}
		public void setErrorCode(int errorCode) {
			this.errorCode = errorCode;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
		
		
	}
	

	
}

