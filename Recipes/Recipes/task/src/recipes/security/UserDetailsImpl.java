package recipes.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import recipes.model.businessLayer.user.User;

import java.util.Collection;
import java.util.List;

/**
 * Used by UserDetailsServiceImpl to represent object of the User class
 */
public class UserDetailsImpl implements UserDetails {
    private final String email;
    private final String password;
    private final List<GrantedAuthority> rolesAndAuthorities;

    public UserDetailsImpl(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        rolesAndAuthorities = List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolesAndAuthorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<GrantedAuthority> getRolesAndAuthorities() {
        return rolesAndAuthorities;
    }
}
