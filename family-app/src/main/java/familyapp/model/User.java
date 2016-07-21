package familyapp.model;

import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="user")
public class User {
	
	@org.springframework.data.annotation.Id
	private String userId;
	@Indexed(unique=true)
	private String email;
	@Indexed
	private String firstName;
	@Indexed
	private String lastName;
	private String password;
	private boolean active;
	private String defaultFamily;
	// this two properties are based on selected family
	private String familyUserPetName;
	private boolean admin;
	// this two properties are based on selected family
	
	
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Transient
	private List<Family> families;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Family> getFamilies() {
		return families;
	}
	public void setFamilies(List<Family> families) {
		this.families = families;
	}
	
	public String toString() {
		return "" + this.firstName + " " + this.lastName + " " + this.email ;
	}
	
	public String getDefaultFamily() {
		return this.defaultFamily;
	}
	public void setDefaultFamily(String defaultFamily) {
		this.defaultFamily = defaultFamily;
		if(families!=null && !families.isEmpty()){
			for(Family family:families){
				if(family.getFamilyId().equals(defaultFamily)){
					for(FamilyMember memberInfo:family.getMembers()) {
						if(userId.equals(memberInfo.getUserId().toString())){
							this.familyUserPetName = memberInfo.getFamilyUserPetName();
							this.admin = memberInfo.isAdmin();
						}
					}
				}
			}
		}
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFamilyUserPetName() {
		return familyUserPetName;
	}
	public void setFamilyUserPetName(String familyUserPetName) {
		this.familyUserPetName = familyUserPetName;
	}
	
	
	
	
	

}
