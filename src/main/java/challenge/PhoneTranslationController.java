package challenge;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.auth.openidconnect.IdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonSimpleJsonParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class PhoneTranslationController {
    
	 @Autowired
	 private HttpSession httpSession;
	
	private static final String CLIENT_ID = "974863897862-q6gprj5gjmbnq3j2rtnvbjinl1f4nmfs.apps.googleusercontent.com";
	
    @RequestMapping("/translatePhoneNum")
    public @ResponseBody PhoneNumbers getMatchingNumbers(@RequestParam String inputNum, @RequestParam int pageNum) {
    	System.out.println("Session authorized to: " + httpSession.getAttribute("email"));
    	
    	if(httpSession.getAttribute("email") == null) {
    		return new PhoneNumbers("NOT AUTHORIZED");
    	}
    	
    	String inputNumClean = stripExtraChars(inputNum);
    	
    	String errorMsg = validateNum(inputNumClean);
    	
    	if(errorMsg != null)
    	{
    		return new PhoneNumbers(errorMsg);
    	}    	
    	
    	PhoneTranslator pt = new PhoneTranslator(inputNumClean);
    	//Calculate Grand Total
    	int grandTotal = pt.getTotal();
    	
    	//Get holding object for return info
    	PhoneNumbers phoneNum = new PhoneNumbers(1, grandTotal, pageNum, 10);
    	
    	//Fill in with results!
    	pt.getResultOptions(phoneNum);
    	
    	return phoneNum;
    }
    
	private String validateNum(String inputNum) {
		// TODO Do more through validation
		String errorMsg = null;
		
		
		//if(inputNum.chars().filter(Character::isAlphabetic).mapToObj(c -> Character.toString((char)c))
        //.collect(Collectors.joining()).length() > 0)
		//TODO: Not working... but gets cleaned up anyway by the next statement
		if(inputNum.chars().filter(Character::isAlphabetic).findFirst().isPresent())
		{
			return "Phone Numbers Can't Can't have letters, that's what this app is for!";
		}
		
		String cleanNum = stripExtraChars(inputNum);
		
		if(cleanNum.length() != 7 && cleanNum.length() != 10)
		{
			return "Phone number must be 7 or 10 digits. Please try again!";
		}
		
		return errorMsg;
	}

	private String stripExtraChars(String inputNum) {
		String cleanNum = inputNum.chars().filter(Character::isDigit)
				.mapToObj(c -> Character.toString((char)c))
		        .collect(Collectors.joining());
		return cleanNum;
	}

// Another Failed Auth Attempt! - this is the right way to do it... secure the page!
//	@RequestMapping("/user")
//	  public Principal user(Principal principal) {
//		System.out.println("Authenticated principal: " + principal);
//	    return principal;
//	  }
	  
    @RequestMapping("/authenticateSession")
    public @ResponseBody String authenticateSession(@RequestParam String idtoken) {
    	String email = null;
    	
    	System.out.println(idtoken);
    	GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
    		    .setAudience(Arrays.asList(CLIENT_ID))
    		    .setIssuer("https://accounts.google.com")
    		    .build();
      
    	System.out.println("verifier: " + verifier.getTransport());
	    GoogleIdToken idToken = null;
	    
	    
	    try {
			idToken = verifier.verify(idtoken);
		} catch (GeneralSecurityException | IOException e) {
			System.out.println("Failed to get Token: " + e.toString());
			e.printStackTrace();
		}
	    
	    // Well, the other isn't working, so... do this instead!
	    if(idToken == null) {
	    	 //GoogleIdToken.parse(getJsonFactory(), idTokenString);
//	    	URL url = new URL("");
//	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//	        conn.setRequestMethod("GET");
//	        conn.setRequestProperty("Accept", "application/json");
//	        conn.
//	    	JsonSimpleJsonParser jsjp = new JsonSimpleJsonParser();
//	    	jsjp.parse();
	    	
	    	RestTemplate restTemplate = new RestTemplate();
	        String output  = restTemplate.getForObject("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + idtoken, String.class);
	        System.out.println("Simple REST Call Output: " + output.toString());

	        //Nope, this doesn't work either... what is UP with that???
//	        try {
//				idToken = GoogleIdToken.parse(new JacksonFactory(), output);
//			} catch (IOException e) {
//				System.out.println("Failed to parse Token: " + e.toString());
//				e.printStackTrace();
//			}
	        
	        

			try {
				JSONParser parser = new JSONParser();
				JSONObject jsonObject = (JSONObject) parser.parse(output);
				email = (String) jsonObject.get("email");
			} catch (ParseException e) {
				System.out.println("Error parsing JSON object");
				e.printStackTrace();
			}
    		
	    }
	    
	    //Not working! Google can't HANDLE the token... 
	    if (idToken != null) {
	      System.out.println("ID TOKEN RECIEVED! SUCCESS!");
	      Payload payload = idToken.getPayload();
	
	      // Print user identifier
	      String userId = payload.getSubject();
	      System.out.println("User ID: " + userId);
	
	      // Get profile information from payload
	      //String email = payload.getEmail();
	      //boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
	      String name = (String) payload.get("name");
	      String pictureUrl = (String) payload.get("picture");
	      String locale = (String) payload.get("locale");
	      String familyName = (String) payload.get("family_name");
	      String givenName = (String) payload.get("given_name");
	
	      System.out.println(name + " " + familyName + " " + givenName);
	      // Use or store profile information
	    } else {
	      System.out.println("Invalid ID token... Or so says google's official API!");
	    }
	    
	    
	    httpSession.setAttribute("email", email);
	    return email;
    }
    
}
