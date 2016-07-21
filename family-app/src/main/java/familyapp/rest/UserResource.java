package familyapp.rest;

import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;

import familyapp.exception.BusinessException;
import familyapp.model.Family;
import familyapp.model.Signup;
import familyapp.model.User;
import familyapp.service.FamilyService;
import familyapp.service.UserService;

/**
 * This method is rest api for all user related items
 * @author sundar
 *
 */
@RestController
@RequestMapping("/user")
@Consumes
public class UserResource {
	@Autowired
	private UserService userService;
	@Autowired
	private FamilyService familyService;
	@RequestMapping(value="/login" ,method={RequestMethod.POST} )
	public ResponseWrapper<User> login(@RequestBody User user) throws Exception {
		return  new ResponseWrapper<User>(userService.login(user));
	}
	
	@RequestMapping(value="/{email:.+}/{familyId}" , method={RequestMethod.GET})
	public ResponseWrapper<User> getUserByFamily(@PathVariable("email") String email , 
		@PathVariable("familyId") String familyId , HttpServletResponse res) throws Exception {
		res.setHeader("Access-Control-Allow-Origin", "*");
		return  new ResponseWrapper<User>(userService.getUserByFamilyId(email ,familyId));
	} 
	
	@RequestMapping(value="/{email:.+}" ,method={RequestMethod.GET} )
	public ResponseWrapper<User> getUser(@PathVariable("email") String email , 
		HttpServletResponse res) throws Exception {
		return  new ResponseWrapper<User>(userService.getUser(email));
	}
	
	
	@RequestMapping(method={RequestMethod.POST} )
	public Response.Status create(@RequestBody User user) throws Exception {
		userService.createUser(user);
		return  Response.Status.OK;
	}
	
	@RequestMapping(method={RequestMethod.PUT} )
	public Response.Status update(@RequestBody User user) throws Exception {
		userService.updateUser(user);
		return  Response.Status.OK;
	}
	
	@RequestMapping(method={RequestMethod.PUT} , value="/family/{email:.+}")
	public ResponseWrapper<User> createFamily(@RequestBody familyapp.model.Family family , @PathVariable String email) throws Exception {
		familyService.addFamilyToUser(family , email);
		return  new ResponseWrapper<User>(new User());
	}
	
	@RequestMapping(method={RequestMethod.POST} , value="/workflow/{step}")
	public ResponseWrapper<User>  loginWorkFlow(@RequestBody Signup signup , @PathVariable String step , 
		HttpSession session , HttpServletRequest request , 
		HttpServletResponse response , @HeaderParam("host") final String host) throws Exception {
		Cookie[] c = request.getCookies();
		
		
		if(signup==null){
			signup = new Signup();
		}
		
		if("1".equalsIgnoreCase(step)) {
			if(!validateEmail(signup.getEmail())){
				throw new BusinessException("error.email.invalid");
			}
			request.getSession(true);
			User user = new User();
			user.setEmail(signup.getEmail());
			session.setAttribute("user", user );
			return new ResponseWrapper<User>(user);
		}else if("2".equalsIgnoreCase(step) ){
			User user = (User) session.getAttribute("user");
			if(!validateEmail(signup.getEmail())){
				throw new BusinessException("error.email.invalid");
			}else if(user==null || !validateEmail(user.getEmail()) || 
				!user.getEmail().equalsIgnoreCase(signup.getEmail())){
				throw new BusinessException("error.email.mismatch");
			}else{
				// check if user already exist and have secure access bring all the families
				// check user exist through cookie as well
				User userFromDB = checkUserExist(signup);
				if(userFromDB!=null) {
					return new ResponseWrapper<User>(userFromDB);
				}else{
					return new ResponseWrapper<User>((User) session.getAttribute("user"));
				}
			}
		}else if("3".equalsIgnoreCase(step) ){
			User user = (User) session.getAttribute("user");
			if(user==null || !validateEmail(user.getEmail())){
				throw new BusinessException("error.user.details.wrong");
			}
			if(Strings.isNullOrEmpty(signup.getFamilyName())){
				throw new BusinessException("error.familyname.required");
			}
			// if user exist create a family with given family id
			if(!Strings.isNullOrEmpty(signup.getUserId())){
				User userFromDB = checkUserExist(signup);
				Family family  = new Family();
				family.setFamilyName(signup.getFamilyName());
				Family familyFromDB = familyService.addFamilyToUser(family, user.getEmail());
				userFromDB.setDefaultFamily(familyFromDB.getFamilyId());
				buildAuthCookie(user.getEmail(), host);
				return new ResponseWrapper<User>(userFromDB);
			}else{
				//Create a user and add a fmaily to user
				Family family  = new Family();
				family.setFamilyName(signup.getFamilyName());
				User userFromDB = userService.createUser(user);
				Family familyFromDB = familyService.addFamilyToUser(family, user.getEmail());
				userFromDB.setDefaultFamily(familyFromDB.getFamilyId());
				buildAuthCookie(user.getEmail(), host);
				return new ResponseWrapper<User>(userFromDB);
			}
		}else if("4".equalsIgnoreCase(step) ){
			User userFromDB = userService.getUser(signup.getEmail());
			if(userFromDB!=null){
				familyService.updateFamilyMemberInfo(userFromDB.getUserId(), signup.getFamilyId() , signup.getUserIdentificationName());
			}
			buildAuthCookie(signup.getEmail(), host);
			
		}else if("0".equalsIgnoreCase(step)){ // email and confirm email page
			if(signup==null || !validateEmail(signup.getEmail())
				|| !validateEmail(signup.getConfirmEmail()) || !signup.getEmail().equalsIgnoreCase(signup.getConfirmEmail())){
				throw new BusinessException("error.email.mismatch");
			}else {
				User userFromDB = checkUserExist(signup);
				if(userFromDB!=null) {
					return new ResponseWrapper<User>(userFromDB);
				}else{
					return new ResponseWrapper<User>((User) session.getAttribute("user"));
				}

			}
		}
		return new ResponseWrapper<User>(new User());
	}
	
	private boolean validateEmail(String email){
		
		if(Strings.isNullOrEmpty(email) || !Pattern.matches(EMAIL_PATTERN, email)){
			return false;
		}
		return true;
	}

	private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private User checkUserExist(Signup signup){
		try {
			User userFromDB = userService.getUser(signup.getEmail());
			return userFromDB;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private NewCookie buildAuthCookie(final String token, final String host ) {
		return buildAuthCookie(token, host);
	}
	
	public static NewCookie buildAuthCookie(final String token, final String host, final String cookieName, String domain , String scheme) {
		if (Strings.isNullOrEmpty(domain)) {
			domain = host.replaceFirst("^(?:[^.]+)((\\.[^.]+){2,100})$", "$1");
		}
		
		final javax.ws.rs.core.Cookie innerCookie = new javax.ws.rs.core.Cookie(cookieName, 
			Strings.isNullOrEmpty(token)?"null":token+";HttpOnly;"+ ("https".equals(scheme) ? "Secure" : ""), "/",  domain.equals(host)?null:domain);
		if (token == null) {
			// Expire immediately
			return new NewCookie(innerCookie, "", 0, false);
		} else {
			// Expire at end of session
			return new NewCookie(innerCookie, "", -1, false);
		}
	}
	
}
