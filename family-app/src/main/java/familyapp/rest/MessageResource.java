package familyapp.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import familyapp.model.Message;
import familyapp.service.MessageService;


@RestController
@RequestMapping("/message")
@Consumes
public class MessageResource {
	
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(value="/{userId}/{familyId}" , method={RequestMethod.POST} )
	public Response.Status create(@RequestBody Message message , @PathVariable("userId") String userId , @PathVariable("familyId") String familyId) throws Exception {
		messageService.createMessage(message , userId , familyId);
		return  Response.Status.OK;
	}

	
	@RequestMapping("/{userId}/{familyId}")
	@ResponseBody
	public ResponseWrapper<List<Message>> getMessages(@PathVariable String userId , @PathVariable String familyId){
		try {
			return new ResponseWrapper<List<Message>>(messageService.getMessagesById(userId, familyId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
