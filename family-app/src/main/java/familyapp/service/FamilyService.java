package familyapp.service;

import familyapp.model.Family;

public interface FamilyService {

	public Family addFamilyToUser(Family family , String userEmail);
	public void updateFamilyMemberInfo(String userId , String familyId , String familyUserPetName);
}
