package tw.com.pollochang.security


import grails.plugin.springsecurity.userdetails.GrailsUser
import org.springframework.security.core.GrantedAuthority

/**
 * 帳號註冊
 * @author JamesChang
 * @since Grails4.0.1
 * @see {@link # https://www.djamware.com/post/5db9a359fe53660ee3228772/grails-4-tutorial-spring-security-core-login-example#install-spring-security}
 * @see {@link # http://plugins.grails.org/plugin/grails/spring-security-core}
 */
class CustomUserDetails extends GrailsUser {

    CustomUserDetails(String username, String password, boolean enabled,
                      boolean accountNonExpired, boolean credentialsNonExpired,
                      boolean accountNonLocked,
                      Collection<GrantedAuthority> authorities,
                      long id) {
        super(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities, id)

    }
}
