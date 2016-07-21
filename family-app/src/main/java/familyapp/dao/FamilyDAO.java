package familyapp.dao;

import familyapp.model.Family;
import familyapp.model.User;

public interface FamilyDAO {

	Family createFamily(Family family);
	Family getFamily(String familyId);
	public void updateUserToFamily(String familyId , User user , boolean adminFlag);
	public void updateFamilyMemberInfo(String userId, String familyId , String familyUserPetName);

}
