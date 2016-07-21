package familyapp.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import familyapp.util.PropertyUtil;


@SuppressWarnings("serial")
public class BusinessException  extends RuntimeException {
	private List<Message> messages = new ArrayList<Message>();
	
	

	/**
	 * no-arg constructor
	 */
	public BusinessException() {
		super();
	}

	/**
	 * convenience constructor for global errors
	 *
	 * @param messageKey
	 * @param data
	 */
	public BusinessException(final String messageKey, final String... data) {
		super();
		messages.add(new Message(null, messageKey, data));
	}

	/**
	 * convenience constructors fo global errors
	 *
	 * @param messageKey
	 * @param data
	 */
	public BusinessException(final Throwable t, final String messageKey, final String... data) {
		super(t);
		messages.add(new Message(null, messageKey, data));
	}

	/**
	 * Constructor for global errors with message level
	 *
	 * @param messageLevel
	 * @param messageKey
	 * @param data
	 */

	public BusinessException(final MessageLevel messageLevel, final Throwable t, final String messageKey, final String... data) {
		super(t);
		messages.add(new Message(messageLevel, null, messageKey, data));
	}

	/**
	 * merge a BusinessException into another
	 *
	 * @param ex
	 */
	public void add(final BusinessException ex) {
		for (final Message msg : ex.getMessages()) {
			messages.add(msg);
		}
	}

	/**
	 * add a message
	 *
	 * @param messageLevel
	 * @param field
	 * @param messageKey
	 * @param data
	 * @return
	 */
	public BusinessException addFieldMessage(final MessageLevel messageLevel, final String field, final String messageKey, final String... data) {
		messages.add(new Message(messageLevel, field, messageKey, data));
		return this;
	}

	/**
	 * add a message
	 *
	 * @param messageLevel
	 * @param messageKey
	 * @param data
	 * @return
	 */
	public BusinessException addError(final String messageKey, final String... data) {
		messages.add(new Message(null, messageKey, data));
		return this;
	}

	/**
	 * add a message
	 *
	 * @param messageLevel
	 * @param field
	 * @param messageKey
	 * @param data
	 * @return
	 */
	public BusinessException addFieldError(final String field, final String messageKey, final String... data) {
		messages.add(new Message(field, messageKey, data));
		return this;
	}

	/**
	 * check message size and throw the exception
	 *
	 * @throws BusinessException
	 */
	public void checkAndThrow() throws BusinessException {
		if (messages.size() > 0) {
			throw this;
		}
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(final List<Message> messages) {
		this.messages = messages;
	}

	public enum MessageLevel {
		WARNING, ERROR, MAJOR
	}

	/**
	 * return the highest message level
	 *
	 * @return
	 */
	public MessageLevel getHighestMessageLevel() {
		MessageLevel level = MessageLevel.WARNING;
		if (messages != null) {
			for (final Message message : messages) {
				if (message.getMessageLevel() != null && level.ordinal() < message.getMessageLevel().ordinal()) {
					level = message.getMessageLevel();
				}
			}
		}
		return level;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("BusinessException ");
		if (messages != null) {
			for (final Message message : messages) {
				sb.append(message);
				sb.append("; ");
			}
		}
		sb.append(super.toString());
		return sb.toString();
	}

	
	public static class Message {

		public Message() {}

		public Message(final String field, final String messageKey, final String... data) {
			this.field = field;
			this.messageKey = messageKey;
			if (data != null) {
				this.data = Arrays.asList(data);
			}
			this.message = PropertyUtil.decodeMessage(messageKey, data);
		}

		public Message(final MessageLevel messageLevel, final String field, final String messageKey, final String... data) {
			this.messageLevel = messageLevel;
			this.field = field;
			this.messageKey = messageKey;
			if (data != null) {
				this.data = Arrays.asList(data);
			}
			this.message = PropertyUtil.decodeMessage(messageKey, data);
		}

		private String field;

		private String messageKey;

		private List<String> data;

		private String message;

		private MessageLevel messageLevel = MessageLevel.ERROR;

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();
			sb.append("<" + messageLevel + ">:[").
				append(field != null?field:"GLOBAL").
				append("]:").append(message);
			if (data != null) {
				sb.append(",data:[");
				for (String s : data) {
					sb.append(s + ",");
				}
				sb.append("]");
			}
			return sb.toString();
		}

		public String getField() {
			return field;
		}

		public void setField(final String field) {
			this.field = field;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(final String message) {
			this.message = message;
		}

		public MessageLevel getMessageLevel() {
			return messageLevel;
		}

		public void setMessageLevel(final MessageLevel messageLevel) {
			this.messageLevel = messageLevel;
		}

		public String getMessageKey() {
			return messageKey;
		}

		public void setMessageKey(String messageKey) {
			this.messageKey = messageKey;
		}

		public List<String> getData() {
			return data;
		}

		public void setData(List<String> data) {
			this.data = data;
		}
	}

	public static class Error {
		private List<Message> messages = new ArrayList<Message>();

		public Error() {}

		public Error(final List<Message> messages) {
			this.messages = messages;
		}

		public List<Message> getMessages() {
			return messages;
		}

		public void setMessages(List<Message> messages) {
			this.messages = messages;
		}
	}

}
