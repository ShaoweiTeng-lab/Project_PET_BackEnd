package project_pet_backEnd.manager.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project_pet_backEnd.manager.vo.Manager;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class ManagerDetailsImp implements UserDetails {
    private Manager manager;
    private List<String> permissionsList;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return manager.getManagerPassword();
    }

    @Override
    public String getUsername() {
        return manager.getManagerAccount();
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
}
