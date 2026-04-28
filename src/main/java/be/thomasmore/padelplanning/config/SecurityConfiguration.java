package be.thomasmore.padelplanning.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.boot.security.autoconfigure.web.servlet.PathRequest.toH2Console;
import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    //if h2-console is activated the login will not work in the deployed version!
//so this variable has to be false when you deploy!!!
//it has to be true when you run locally, true is the default, so that's ok
    @Value(value = "${security.h2-console-needed:true}")
    private boolean h2ConsoleNeeded;
    private final DataSource dataSource;
    //datasource specifies how we connect with the database - see applications.properties file
    public SecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Bean
//means that we use standard tables in the db to define users and roles
    public JdbcUserDetailsManager jdbcUserDetailsManager() {
        return new JdbcUserDetailsManager(dataSource);
    }
    @Bean
//NEVER store readable passwords in the database!
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
//only user with admin role can acccess requests that start with /admin/ :
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
//all other requests can be executed by anyone:
                .anyRequest().permitAll());
        http.formLogin(withDefaults());
        http.logout(withDefaults());
// http.formLogin(form -> form.loginPage("/login"));
// http.logout(form -> form.logoutUrl("/logout"));
//to enable h2-console (default true, should be false when deployed):
        if (h2ConsoleNeeded) {
            http.csrf(csrf -> csrf.ignoringRequestMatchers(toH2Console()));
            http.headers(headers ->
                    headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        }
        return http.build();
    }
}
