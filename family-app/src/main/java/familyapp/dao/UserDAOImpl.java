package familyapp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import familyapp.model.Family;
import familyapp.model.User;

@Repository
public class UserDAOImpl implements UserDAO{
	
	@Autowired MongoTemplate mongoTempalte;
	
	@Override
	public User createUser(User user) {
		try{
			mongoTempalte.save(user);
			return getUser(user.getEmail());
		}catch(Exception e){
			throw e;
		}
		
	}

	@Override
	public User getUser(String email) {
		try{
			Query userQuery = new Query(Criteria.where("email").is(email));
			User user = mongoTempalte.findOne(userQuery, User.class, "user");
			if(user!=null) {
				Query familyQuery = new Query(Criteria.where("members").elemMatch(Criteria.where("email").is(user.getEmail())));
				List<Family> families = mongoTempalte.find(familyQuery, Family.class);
				user.setFamilies(families);
				if(user.getFamilies()!=null && !user.getFamilies().isEmpty()) {
				user.setDefaultFamily(families.get(0).getFamilyId());
				}
			}
			return user;
		}catch(Exception e){
			throw e;
		}
		
	}
	
	@Override
	public User getUserById(String userId) {
		return mongoTempalte.findById(userId, User.class);
	}

	@Override
	public void updateUser(User user) {
		try{
			Query builder = new Query(Criteria.where("email").is(user.getEmail()));
			Update update = new Update();
			update.set("firstName", user.getFirstName());
			update.set("lastName", user.getLastName());
			mongoTempalte.findAndModify(builder, update, User.class);
		}catch(Exception e){
			throw e;
		}
		
	}
	
	@Override
	public User auth(String email, String password) throws Exception {
		try{
		Query builder = new Query(Criteria.where("email").is(email).andOperator(Criteria.where("password").is(password)));
		User user = mongoTempalte.findOne(builder, User.class, "user");
		Query familyQuery = new Query(Criteria.where("userIds").in(user.getUserId()));
		List<Family> families = mongoTempalte.find(familyQuery, Family.class);
		user.setFamilies(families);
		user.setDefaultFamily(families.get(0).getFamilyId());
		return user;
		}catch(Exception e){
			throw e;
		}
	}

	
	@Override
	public void deleteUser(String userId) {
		mongoTempalte.remove(userId);
	}
	
	
}
