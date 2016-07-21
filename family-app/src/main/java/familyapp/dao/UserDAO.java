package familyapp.dao;

import familyapp.model.User;

public interface UserDAO {
	
	User createUser(User user) throws Exception;
	User getUser(String username) throws Exception;
	void updateUser(User user) throws Exception;
	User auth(String username , String password) throws Exception;
	void deleteUser(String userId)throws Exception;
	User getUserById(String userId) throws Exception;
	
}
