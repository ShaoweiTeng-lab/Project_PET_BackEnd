package project_pet_backEnd.manager.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project_pet_backEnd.manager.vo.Manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ManagerDetailsImp implements UserDetails {
    private Manager manager;
    private List<String> permissionsList;
    @JsonIgnore
    private List<SimpleGrantedAuthority>authorities;
    public ManagerDetailsImp(Manager manager, List<String> permissionsList) {
        this.manager = manager;
        //注入權限列表
        this.permissionsList = permissionsList;

    }
    public ManagerDetailsImp() {
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //管理員權限
        if(authorities!=null)//
            return  authorities;
        authorities =new ArrayList<>();
        for (String permission : permissionsList) {
            SimpleGrantedAuthority simpleAuthority =new SimpleGrantedAuthority(permission);
            authorities.add(simpleAuthority);
        }
        // 須返回上界為 GrantedAuthority的類 ，Spring Security 有實作  SimpleGrantedAuthority
        return authorities;
    }

    @Override
    public String getPassword() {
        //返回使用者密碼 ，在 ProviderManager 跟使用者輸入進行比對
        return manager.getManagerPassword();
    }

    @Override
    public String getUsername() {
        //返回使用者帳戶 ，在 ProviderManager 跟使用者輸入進行比對
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
