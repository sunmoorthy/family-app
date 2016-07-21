package familyapp.dao;

import java.util.List;

import familyapp.model.Message;

public interface MessageDAO {

	public void createMessage(Message message)throws Exception;
	public List<Message>getAllMessagesByUser(String userId , String familyId)throws Exception;
}
