package familyapp.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import familyapp.model.Family;
import familyapp.model.User;

@Repository
public class FamilyDAOImpl implements FamilyDAO{

	@Autowired MongoTemplate mongoTempalte;
	
	@Override
	public Family createFamily(Family family) {
		mongoTempalte.insert(family);
		return family;
	}
	
	public List<Family> getFamiliesById(List<ObjectId> objectIds) {
		return null;
	}
	
	public Family getFamily(String familyId) {
		return mongoTempalte.findById(familyId, Family.class);
	}
	
	/**
	 * This add user to existing family if user doesn't exist in the family
	 * If admin flag is true add user as a administrator
	 * If admin flag is false remove user from administratory
	 * @param userId
	 */
	public void updateUserToFamily(String familyId , User user , boolean adminFlag) {
		Query findQuery = new Query( Criteria.where("members.userId").in(user.getUserId()).andOperator(Criteria.where("_id").is(familyId)));
		Family family = mongoTempalte.findOne(findQuery, Family.class);
		Update update = new Update();
		if(family==null){
			update.push("members", user);
		}else{
			//update.pull and push again???
		}
		
		mongoTempalte.updateFirst(new Query(Criteria.where("_id").is(familyId)) , update, Family.class);
	}
	
	
	/**
	 * This method update family member details for the family id and user id
	 * @param familyId
	 * @param userId
	 */
	public void updateFamilyMemberInfo(String userId , String familyId , String familyUserPetName) {
		
		Query findQuery = new Query( Criteria.where("members.userId").in(new ObjectId(userId)).andOperator(Criteria.where("_id").is(familyId)));
		Family family = mongoTempalte.findOne(findQuery, Family.class);
		if(family!=null){
			family.getMembers().get(0).setFamilyUserPetName(familyUserPetName);
			mongoTempalte.save(family);
		}
	}
	

}
