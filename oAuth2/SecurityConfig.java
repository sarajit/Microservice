package oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}
	 @Override
	    protected void configure(AuthenticationManagerBuilder auth) 
	      throws Exception {
	        auth.inMemoryAuthentication();
	    }
	 
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	            .anonymous().disable()
	            .csrf().disable()
	            .authorizeRequests()
	            .antMatchers("/home.html").hasRole("USER")
	            .and()
	            .httpBasic()
	            .authenticationEntryPoint(oauth2AuthenticationEntryPoint());
	    }
	 
	    private LoginUrlAuthenticationEntryPoint oauth2AuthenticationEntryPoint() {
	        return new LoginUrlAuthenticationEntryPoint("/login");
	    }
	}


