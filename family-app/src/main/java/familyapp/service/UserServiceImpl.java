package familyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import familyapp.dao.FamilyDAO;
import familyapp.dao.UserDAO;
import familyapp.model.Family;
import familyapp.model.User;

/**
 * This class service layer for all business services and business logics
 * @author sundar
 *
 */
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private FamilyDAO familyDAO;
	
	public User login(User user) throws Exception {
		User loggedInUser = null;
		loggedInUser = userDAO.auth(user.getEmail(), user.getPassword());
		return loggedInUser;
	}
	
	public User getUser(String email) throws Exception {
		User loggedInUser = userDAO.getUser(email);
		return loggedInUser;
	}
	
	public User getUserByFamilyId(String email , String familyId) throws Exception {
		User loggedInUser = userDAO.getUser(email);
		if(loggedInUser!=null) {
			if(loggedInUser.getFamilies()!=null && !loggedInUser.getFamilies().isEmpty()) {
				for(Family f : loggedInUser.getFamilies()) {
					if(f.getFamilyId().equals(familyId)){
						loggedInUser.setDefaultFamily(familyId);
						break;
					}
				}
			}
		}
		return loggedInUser;
	}
	
	
	public User createUser(User user) throws Exception {
		return userDAO.createUser(user);
	}
	public void updateUser(User user) throws Exception {
		User userFromDB = getUser(user.getEmail());
		if(userFromDB!=null){
			user.setFirstName(user.getFirstName());
			user.setLastName(user.getLastName());
			userDAO.updateUser(user);
		}
	}
	
	public void updateFamilyMemberInfo(String familyId , String userId){
		//familyDAO.up
	}
	
	public void deleteUser(String userId) throws Exception{
		userDAO.deleteUser(userId);
	}
	
	
	public void addUserToFamily(String email , String familyId , boolean adminFlag) throws Exception {
		User user = getUser(email);
		if(user!=null && user.getUserId()!=null) {
			familyDAO.updateUserToFamily(familyId, user, adminFlag);
		}
	}

}
