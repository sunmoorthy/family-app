package familyapp.service;

import familyapp.model.User;

public interface UserService {

	public User login(User user) throws Exception ;
	public User getUser(String email) throws Exception; 
	public User createUser(User user) throws Exception; 
	public void updateUser(User user) throws Exception ;
	public void deleteUser(String userId) throws Exception;
	public User getUserByFamilyId(String email , String familyId) throws Exception;
}
