package io.github.karl.admin;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import jakarta.servlet.DispatcherType;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;



@Configuration(proxyBeanMethods = false)
public class SecuritySecureConfig {

    private final AdminServerProperties adminServer;

    private final SecurityProperties security;

    public SecuritySecureConfig(AdminServerProperties adminServer, SecurityProperties security) {
        this.adminServer = adminServer;
        this.security = security;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(this.adminServer.path("/"));

        http.authorizeHttpRequests((authorizeRequests) -> authorizeRequests //
                        .requestMatchers(new AntPathRequestMatcher(this.adminServer.path("/assets/**")))
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher(this.adminServer.path("/actuator/**")))
                        .permitAll()
//                        .requestMatchers(new AntPathRequestMatcher(adminServer.path("/actuator/health")))
//                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher(this.adminServer.path("/login")))
                        .permitAll()
                        .dispatcherTypeMatchers(DispatcherType.ASYNC)
                        .permitAll() // https://github.com/spring-projects/spring-security/issues/11027
                        .anyRequest()
                        .authenticated())
                .formLogin(
                        (formLogin) -> formLogin.loginPage(this.adminServer.path("/login")).successHandler(successHandler))
                .logout((logout) -> logout.logoutUrl(this.adminServer.path("/logout")))
                .httpBasic(Customizer.withDefaults());
        http.csrf().disable();

//        http.addFilterAfter(new CustomCsrfFilter(), BasicAuthenticationFilter.class)
//                .csrf((csrf) -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
//                        .ignoringRequestMatchers(
//                                new AntPathRequestMatcher(this.adminServer.path("/instances"), POST.toString()),
//                                new AntPathRequestMatcher(this.adminServer.path("/instances/*"), DELETE.toString()),
//                                new AntPathRequestMatcher(this.adminServer.path("/actuator/**"))
//                        ));

        http.rememberMe((rememberMe) -> rememberMe.key(UUID.randomUUID().toString()).tokenValiditySeconds(1209600));

        return http.build();

    }

    // Required to provide UserDetailsService for "remember functionality"
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user1 = User.withUsername("user").password(passwordEncoder.encode("password")).roles("USER").build();
        UserDetails user2 = User.withUsername("admin").password(passwordEncoder.encode("admin")).roles("USER", "ADMIN").build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

//@Configuration(proxyBeanMethods = false)
//public class SecuritySecureConfig {
//}
//class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
//    private final SecurityProperties security;
//    private final AdminServerProperties adminServer;
//
//    public SecuritySecureConfig(AdminServerProperties adminServer, SecurityProperties security) {
//        this.adminServer = adminServer;
//        this.security = security;
//    }
//
////    @Bean
////    PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
//        successHandler.setTargetUrlParameter("redirectTo");
//        successHandler.setDefaultTargetUrl(this.adminServer.path("/"));
//
//        http.authorizeRequests(authorizeRequests ->
//                        authorizeRequests.antMatchers(this.adminServer.path("/assets/**")).permitAll()
//                                .antMatchers(this.adminServer.path("/actuator/**")).permitAll()
//                                .antMatchers(this.adminServer.path("/actuator/health")).permitAll()
//                                .antMatchers(this.adminServer.path("/login")).permitAll()
//                                .anyRequest().authenticated()
//                )
//                .formLogin(
//                        (formLogin) -> formLogin.loginPage(this.adminServer.path("/login")).successHandler(successHandler).and()
//                )
//                .logout((logout) -> logout.logoutUrl(this.adminServer.path("/logout"))).httpBasic(Customizer.withDefaults())
//                .csrf((csrf) -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                        .ignoringRequestMatchers(
//                                new AntPathRequestMatcher(this.adminServer.path("/instances"),
//                                        HttpMethod.POST.toString()),
//                                new AntPathRequestMatcher(this.adminServer.path("/instances/*"),
//                                        HttpMethod.DELETE.toString()),
//                                new AntPathRequestMatcher(this.adminServer.path("/actuator/**"))
//                        ))
//                .rememberMe((rememberMe) -> rememberMe.key(UUID.randomUUID().toString()).tokenValiditySeconds(1209600));
//    }
//
//    // Required to provide UserDetailsService for "remember functionality"
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser(security.getUser().getName())
//                .password("{noop}" + security.getUser().getPassword())
//                .roles("USER");
//    }
//
//
//}
