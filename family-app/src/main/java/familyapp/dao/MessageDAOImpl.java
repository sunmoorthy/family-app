package familyapp.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import familyapp.model.Message;

@Repository
public class MessageDAOImpl implements MessageDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * Create message for user id and familyId
	 */
	public void createMessage(Message message){
		mongoTemplate.save(message);
	}
	
	
	/**
	 * User can see the messages based on selected family
	 * @param userId
	 * @param familyId
	 * @return
	 */
	public List<Message>getAllMessagesByUser(String userId , String familyId) {
		Query familyQuery = new Query(Criteria.where("familyIds").in(familyId)).addCriteria(Criteria.where("recipents.userId").in(new ObjectId(userId)));
		List<Message> messages = mongoTemplate.find(familyQuery, Message.class);
		return messages;
	}

}
