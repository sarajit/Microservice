import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;

import oauth2.google.GoogleUser;

@ControllerAdvice

public class ControllerAdvicer
{
    
	@ModelAttribute("currentUser")
	public GoogleUser currentUser(@AuthenticationPrincipal GoogleUser currentUser,HttpSession httpSession)
    {
       
    	System.out.println(" ControllerAdvicercurrentUser----------------------------"+currentUser);
    	httpSession.setAttribute("currentUser", currentUser);
     	 System.out.println("ControllerAdvicercurrentUser currentUser----------------------------"+httpSession.getAttribute("currentUser"));
      	return currentUser;
    }
	
	
	@ExceptionHandler(HttpSessionRequiredException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason="The session has expired")
	public String handleSessionExpired(){
	  return "sessionExpired";
	}
}
