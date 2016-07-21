package familyapp.model;

public class Signup {

	private String email;
	private String confirmEmail;
	private String familyName;
	private String userIdentificationName;
	private String userId;
	private String familyId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmEmail() {
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getUserIdentificationName() {
		return userIdentificationName;
	}

	public void setUserIdentificationName(String userIdentificationName) {
		this.userIdentificationName = userIdentificationName;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}
	
	
	
	
	
}
