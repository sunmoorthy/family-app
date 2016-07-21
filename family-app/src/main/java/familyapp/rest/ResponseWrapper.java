package familyapp.rest;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import familyapp.exception.BusinessException;
import familyapp.exception.BusinessException.Message;


public class ResponseWrapper<T> {


		private Result result = Result.OK;

		private T data;

		private List<Error> errors;

	    private List<Error> warnings;
	    
	    private BusinessException exception;


		public ResponseWrapper() {}

		public ResponseWrapper(T data) {
			this.result = Result.OK;
			this.data = data;
		}

		public Result getResult() {
			return result;
		}

		public ResponseWrapper<T> setResult(Result result) {
			this.result = result;
	        return this;
		}

		public T getData() {
			return data;
		}

		public ResponseWrapper<T> setData(T data) {
			this.data = data;
	        return this;
		}

		public List<Error> getErrors() {
			return errors;
		}

		public void setErrors(List<Error> errors) {
			this.errors = errors;
		}

	    public void addError(Error error) {
	        if (this.errors == null) {
	            this.errors = new ArrayList<Error>();
	        }
	        this.errors.add(error);
	    }
	    
		@JsonIgnore
		public BusinessException getException() {
			return exception;
		}

		public void setException(BusinessException exception) {
			this.exception = exception;
		}




		public static ResponseWrapper<Object> ok() {
			return new ResponseWrapper<Object>();
		}

		public static enum Result {
			OK, ERROR
		}
		
		public static ResponseWrapper<Object> error(BusinessException ex) {
			final ResponseWrapper<Object> response = new ResponseWrapper<Object>();
			response.setException(ex);
			response.setResult(Result.ERROR);
			response.setErrors(new ArrayList<ResponseWrapper.Error>());
			for (final Message msg : ex.getMessages()) {
				response.getErrors().add(new Error(msg.getMessage(), msg.getMessageKey(), msg.getField(), msg.getData().toArray(new String[0])));
			}
			return response;
		}


		public static class Error {
			private String message;
			private String code;
			private String param;
			private String[] errorData;

			public Error() {}

			public Error(String message, String code, String param, String... errorData) {
				this.message = message;
				this.code = code;
				this.param = param;
				this.errorData = errorData;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public String getCode() {
				return code;
			}

			public void setCode(String code) {
				this.code = code;
			}

			public String getParam() {
				return param;
			}

			public void setParam(String param) {
				this.param = param;
			}

			public String[] getErrorData() {
				return errorData;
			}

			public void setErrorData(String[] errorData) {
				this.errorData = errorData;
			}

		}
}
