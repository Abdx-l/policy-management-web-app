package dev.akhras;
import org.springframework.boot.*;import org.springframework.boot.autoconfigure.*;import org.springframework.context.annotation.*;import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;import org.springframework.security.config.annotation.web.builders.HttpSecurity;import org.springframework.security.web.SecurityFilterChain;import org.springframework.security.core.userdetails.*;import org.springframework.security.provisioning.InMemoryUserDetailsManager;import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@SpringBootApplication @EnableMethodSecurity
public class Application{
 public static void main(String[]a){SpringApplication.run(Application.class,a);}
 @Bean SecurityFilterChain security(HttpSecurity h)throws Exception{return h.csrf(c->c.disable()).authorizeHttpRequests(a->a.anyRequest().authenticated()).httpBasic(b->{}).build();}
 @Bean UserDetailsService users(){var encoder=new BCryptPasswordEncoder();return new InMemoryUserDetailsManager(User.withUsername("agent").password(encoder.encode(System.getenv().getOrDefault("AGENT_PASSWORD","local-agent-change-me"))).roles("AGENT").build(),User.withUsername("adjuster").password(encoder.encode(System.getenv().getOrDefault("ADJUSTER_PASSWORD","local-adjuster-change-me"))).roles("ADJUSTER").build(),User.withUsername("admin").password(encoder.encode(System.getenv().getOrDefault("ADMIN_PASSWORD","local-admin-change-me"))).roles("ADMIN").build());}
}
