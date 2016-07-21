package familyapp.service;

import java.util.ArrayList;
import java.util.Calendar;

import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import familyapp.dao.FamilyDAO;
import familyapp.model.Family;
import familyapp.model.FamilyMember;
import familyapp.model.User;

@Service
public class FamilyServiceImpl implements FamilyService{

	@Autowired
	UserService userService;
	
	@Autowired
	FamilyDAO familyDAO;
	
	public Family addFamilyToUser(Family family , String userEmail) {
		User userFromDB;
		try {
			userFromDB = userService.getUser(userEmail);
			FamilyMember familyMember = new FamilyMember();
			BeanUtils.copyProperties(userFromDB, familyMember, "userId" );
			family.setMembers(new ArrayList<FamilyMember>());
			familyMember.setUserId(new ObjectId(userFromDB.getUserId()));
			family.getMembers().add(familyMember);
			family.setCreatedDate(Calendar.getInstance().getTime());
			family.setCreatedBy(userFromDB.getUserId());
			return familyDAO.createFamily(family);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void updateFamilyMemberInfo(String userId , String familyId , String familyUserPetName) {
		familyDAO.updateFamilyMemberInfo(userId, familyId, familyUserPetName);
	}

}
