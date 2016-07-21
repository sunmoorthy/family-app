package familyapp.exception;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.spi.container.ResourceFilter;
import com.sun.jersey.spi.container.ResourceFilterFactory;

@Component
public class CommonResourceFilterFactory implements ResourceFilterFactory {

	@Autowired
	private ExceptionReportFilter exception;
	@Override
	public List<ResourceFilter> create(final AbstractMethod am) {
		return Arrays.<ResourceFilter>asList(exception);
	}

}
