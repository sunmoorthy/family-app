package familyapp.model;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Message {
	
	@Id
	private String messageId;
	private String content;
	private Calendar postedDate;
	private List<String> familyIds;
	private List<FamilyMember> recipents;
	private User author;
	private List<Comment> coments;
	
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Calendar getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(Calendar postedDate) {
		this.postedDate = postedDate;
	}
	
	public List<String> getFamilyIds() {
		return familyIds;
	}
	public void setFamilyIds(List<String> familyIds) {
		this.familyIds = familyIds;
	}
	
	
	public List<FamilyMember> getRecipents() {
		return recipents;
	}
	public void setRecipents(List<FamilyMember> recipents) {
		this.recipents = recipents;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	
	
	
	
	
	
	
}
