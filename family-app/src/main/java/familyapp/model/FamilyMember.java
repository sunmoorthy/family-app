package familyapp.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

public class FamilyMember {

	private ObjectId userId;
	private String email;
	@Indexed
	private String firstName;
	@Indexed
	private String lastName;
	private String familyUserPetName;
	private boolean admin;
	
	
	
	public ObjectId getUserId() {
		return userId;
	}
	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}
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
	public String getFamilyUserPetName() {
		return familyUserPetName;
	}
	public void setFamilyUserPetName(String familyUserPetName) {
		this.familyUserPetName = familyUserPetName;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	

}
