package familyapp.exception;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;

import familyapp.exception.BusinessException.MessageLevel;
import familyapp.rest.ResponseWrapper;

@Component
public class ExceptionReportFilter implements ResourceFilter{


	@Context
	private HttpServletRequest request;

	private static final Pattern PASSWORD_PATTERN = Pattern.compile("([pP]assword\\\"\\s*:\\s*\\\")(.+?)\\\"");
	private static final String BODY = "body";

	public ContainerRequestFilter getRequestFilter() {
		return new ContainerRequestFilter() {
			@Override
			public ContainerRequest filter(final ContainerRequest req) {
				// read the entity input stream
				final byte[] body = readBytes(req.getEntityInputStream());
				if (body != null && body.length > 0) {
					request.setAttribute(BODY, new String(body));
				}
				// put the read bytes back
				req.setEntityInputStream(new ByteArrayInputStream(body));
				return req;
			}
		};
	}

	@SuppressWarnings("rawtypes")
	public ContainerResponseFilter getResponseFilter() {
		return new ContainerResponseFilter() {
			@Override
			public ContainerResponse filter(final ContainerRequest req, final ContainerResponse res) {
				// grab the exception from the response
				// exception would have been processed by the exception mappers by this time
				BusinessException reportableException = null;
				if (res.getEntity() != null && res.getEntity() instanceof ResponseWrapper<?>) {
					final BusinessException ex = ((ResponseWrapper<?>) res.getEntity()).getException();
					if (ex != null && MessageLevel.MAJOR.equals(ex.getHighestMessageLevel())) {
						reportableException = ex;
					}
				}
				// report the exception
				if (reportableException != null) {
					final StringBuilder sb = new StringBuilder();
					//sb.append("User:").append(SecurityUtil.getCurrentUsername()).append("\n");
					sb.append(request.getMethod()).append(" ").append(request.getRequestURL().toString()).append("\n");

					// print the http headers
					sb.append("\n");
					sb.append("Headers:\n");
					final Enumeration headerNames = request.getHeaderNames();
					while (headerNames.hasMoreElements()) {
						final String name = (String) headerNames.nextElement();
						final Enumeration values = request.getHeaders(name);
						sb.append("  ").append(name).append(" = ");
						if (values != null) {
							while (values.hasMoreElements()) {
								final String value = (String) values.nextElement();
								sb.append(value);
								if (values.hasMoreElements()) {
									sb.append(", ");
								}
							}
						}
						sb.append("\n");
					}

					// print the params
					final String requestEntity = (String) request.getAttribute(BODY);
					if (!Strings.isNullOrEmpty(requestEntity)) {
						sb.append("\n");
						sb.append("Request Parameters:\n");
						// filter out passwords
						final Matcher m = PASSWORD_PATTERN.matcher(requestEntity);
						final StringBuffer buffer = new StringBuffer();
						while (m.find()) {
							m.appendReplacement(buffer, m.group(1) + "****\\\"");
						}
						m.appendTail(buffer);
						sb.append(buffer.toString());
						sb.append("\n");
					}
					final StringWriter sw = new StringWriter();
					final PrintWriter pw = new PrintWriter(sw);
					reportableException.printStackTrace(pw);
					pw.flush();
					sw.flush();
					sb.append("\n");
					sb.append(sw.toString());
				}

				return res;
			}
		};
	}

	private byte[] readBytes(final InputStream is) {
		try {
			final BufferedInputStream bis = new BufferedInputStream(is);
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			final byte[] buffer = new byte[256];
			int bytesRead = 0;
			while ((bytesRead = bis.read(buffer)) >= 0) {
				bos.write(buffer, 0, bytesRead);
			}
			bos.flush();
			return bos.toByteArray();
		} catch (final IOException ioex) {
			// do nothing
		}
		return null;
	}
}
