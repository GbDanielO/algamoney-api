package br.com.algamoney.api.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableWebSecurity
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter implements AuthenticationManager {

    @Autowired
    public void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder( NoOpPasswordEncoder.getInstance() )
                .withUser( "admin" ).password( "admin" ).roles( "ROLE" );
    }

    @Override
    public void configure( HttpSecurity http ) throws Exception {
        http.authorizeRequests()
                .antMatchers( "/categorias" ).permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS )
                .and().csrf().disable();
    }

    @Override
    public void configure( ResourceServerSecurityConfigurer resources ) throws Exception {
        resources.stateless( true );
    }

    @Override
    public Authentication authenticate( Authentication auth ) throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials().toString();
        // to add more logic
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add( new SimpleGrantedAuthority( "ROLE_USER" ) );
        return new UsernamePasswordAuthenticationToken( username, password, grantedAuths );
    }

}
