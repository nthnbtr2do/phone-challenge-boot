package challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableOAuth2Sso
public class PhoneChallengeBootApplication extends WebSecurityConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(PhoneChallengeBootApplication.class, args);
	}
	
	
	  @Override
	  protected void configure(HttpSecurity http) throws Exception {
	    http
	      .antMatcher("/**")
	      .authorizeRequests()
	        .antMatchers("/", "/login**", "/webjars/**", "/translatePhoneNum", "/index.html", "/about.html", "/contact.html", "/uuser", "/authenticateSession", "/theme-example.html")
	        .permitAll()
	      .anyRequest()
	        .authenticated()
	      .and().csrf().disable();
//	      .and().csrf().csrfTokenRepository(csrfTokenRepository())
//		  .and().addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
	  }

//	  private Filter csrfHeaderFilter() {
//		  return new OncePerRequestFilter() {
//		    @Override
//		    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//		        FilterChain filterChain) throws ServletException, IOExeption {
//		      CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
//		      if (csrf != null) {
//		        Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
//		        String token = csrf.getToken();
//		        if (cookie == null || token != null && !token.equals(cookie.getValue())) {
//		          cookie = new Cookie("XSRF-TOKEN", token);
//		          cookie.setPath("/");
//		          response.addCookie(cookie);
//		        }
//		      }
//		      filterChain.doFilter(request, response);
//		    }
//		  };
//		}
//	  
//	  private CsrfTokenRepository csrfTokenRepository() {
//		  HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//		  repository.setHeaderName("X-XSRF-TOKEN");
//		  return repository;
//		}
	  
}
