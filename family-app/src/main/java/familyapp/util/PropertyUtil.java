package familyapp.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;


public class PropertyUtil {

	private static Logger logger = Logger.getLogger(PropertyUtil.class);

	private static ResourceBundle resource = null;

	private PropertyUtil() {}

	static {
		try {
			PropertyUtil.resource = new PropertyResourceBundle(PropertyUtil.class.getResourceAsStream("/messages.properties"));
		} catch (final IOException ex) {
			PropertyUtil.logger.fatal("Resource bundle messages could not be loaded", ex);
		} catch (final NullPointerException npe) {
			PropertyUtil.logger.fatal("Resource bundle messages could not be loaded", npe);
		}
	}

	public static String decodeMessage(final String messageKey, final String... data) {
		String message = null;
		try {
			message = PropertyUtil.resource.getString(messageKey);
		} catch (final MissingResourceException ex) {
			PropertyUtil.logger.debug("Missing resource " + messageKey, ex);
		}
		if (message == null) {
			message = messageKey;
		}
		return MessageFormat.format(message, (Object[]) data).trim();
	}

}
