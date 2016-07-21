package familyapp.service;

import java.util.List;

import familyapp.model.Message;

public interface MessageService {
	
	public void createMessage(Message message , String userId, String familyId) throws Exception;
	public List<Message> getMessagesById(String userId , String familyId) throws Exception;

}
