package familyapp.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Transient;


public class Family {
	
	@org.springframework.data.annotation.Id
	private String familyId;
	private String familyName;
	private String familyType;
	private Date createdDate;
	private List<FamilyMember> members;
	private String createdBy;
	@Transient
	private int noOfUsers;
	
	
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getFamilyType() {
		return familyType;
	}
	public void setFamilyType(String familyType) {
		this.familyType = familyType;
	}
	
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getFamilyId() {
		return familyId;
	}
	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}
	
	public List<FamilyMember> getMembers() {
		return members;
	}
	public void setMembers(List<FamilyMember> members) {
		this.members = members;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public int getNoOfUsers() {
		return (members!=null && !members.isEmpty() ? members.size() +1 : 0);
	}
	
	
	
	
	
	
}
