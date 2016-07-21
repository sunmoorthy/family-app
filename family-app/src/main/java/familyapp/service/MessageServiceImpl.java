package familyapp.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import familyapp.dao.FamilyDAO;
import familyapp.dao.MessageDAO;
import familyapp.dao.UserDAO;
import familyapp.model.Message;
import familyapp.model.Family;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageDAO messageDAO;
	@Autowired
	private FamilyDAO familyDAO;
	@Autowired
	private UserDAO userDAO;
	
	
	public void createMessage(Message message , String userId , String familyId) throws Exception {
		message.setAuthor(userDAO.getUserById(userId));
		Family family = familyDAO.getFamily(familyId);
		List<String> familyIds = new ArrayList<String>();
		familyIds.add(family.getFamilyId());
		message.setFamilyIds(familyIds);
		message.setRecipents(family.getMembers());
		messageDAO.createMessage(message);
	}

	@Override
	public List<Message> getMessagesById(String userId , String familyId) throws Exception {
		//Get family
		return messageDAO.getAllMessagesByUser(userId, familyId);
		
	}
	

}
