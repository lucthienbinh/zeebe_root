package com.mainapplication.spring_boot.user_module.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mainapplication.spring_boot.base_entity_module.model.BaseEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "user", uniqueConstraints={@UniqueConstraint(columnNames = "username"),@UniqueConstraint(columnNames = "email")})
// @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class UserModel extends BaseEntity {

    //#region Main fields for Spring Security 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "username", nullable = false, length = 20, unique=true)
    @Size(max = 20, message = "Username cannot be longer than 20 characters")
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password is mandatory")
    @JsonIgnore // this will make it available in the back but will not return when transforming to json
    private String password;

    @Column(name = "enabled", columnDefinition = "tinyint(1) default 1")
    private boolean enabled;

    @Column(name = "accountNonExpired", columnDefinition = "tinyint(1) default 1")
    private boolean accountNonExpired;

    @Column(name = "accountNonLocked", columnDefinition = "tinyint(1) default 1")
    private boolean accountNonLocked;
    
    @Column(name = "credentialsNonExpired", columnDefinition = "tinyint(1) default 1")
	private boolean credentialsNonExpired;

    @ManyToMany
    @JoinTable( 
        name = "users_roles_join_table", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
    @JsonManagedReference
    // https://stackoverflow.com/questions/3325387/infinite-recursion-with-jackson-json-and-hibernate-jpa-issue
    private Collection<UserRoleModel> roles;
    //#endregion 

    //#region Custom field for more information 
    // https://howtodoinjava.com/regex/java-regex-validate-email-address/
    @Column(name = "email", nullable = false, length = 50, unique=true)
    @Size(max = 50, message = "Email cannot be longer than 50 characters")
    @NotBlank(message = "Email is mandatory")
    @Pattern(regexp = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "Email should be valid")
    private String email;

    @Column(name = "first_name", columnDefinition = "varchar(20) default ''")
    @Size(max = 20, message = "First name cannot be longer than 20 characters")
    private String first_name;

    @Column(name = "last_name", columnDefinition = "varchar(20) default ''")
    @Size(max = 20, message = "Last name cannot be longer than 20 characters")
    private String last_name;

    @PositiveOrZero
    private int age;

    @Column(name="job_title", columnDefinition = "varchar(20) default ''")
    @Size(max = 20, message = "Last name cannot be longer than 20 characters")
    private String job_title;

    @Column(name="phone", columnDefinition = "varchar(15) default ''")
    @Size(max = 15, message = "Phone number cannot be longer than 15 characters")
    private String phone;
    //#endregion

    public UserModel(int id) {
        this.id = id;
    }
    
    @Builder // Builder patern by lombok
    public UserModel(String username, String password, boolean enabled, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, Collection<UserRoleModel> roles, String email, String first_name, String last_name, int age, String job_title, String phone) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.roles = roles;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.job_title = job_title;
        this.phone = phone;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() 
    {
        return getGrantedAuthorities(this.roles, getPrivileges(this.roles));
    }
 
    private List<String> getPrivileges(Collection<UserRoleModel> roles) {
  
        List<UserPrivilegeModel> collection = new ArrayList<>();
        for (UserRoleModel role : roles) {
            collection.addAll(role.getPrivileges());
        }
        List<String> privileges = new ArrayList<>();
        for (UserPrivilegeModel item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }
 
    private List<GrantedAuthority> getGrantedAuthorities(Collection<UserRoleModel> _roles, List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();

         // Extract list of permissions (name)
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        
        // Extract list of roles (ROLE_name)
        for (UserRoleModel role : _roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    public List<String> getAuthoritiesStringList()
    {
        List<String> collectionList = this.getPrivileges(this.roles);
        for (UserRoleModel role : this.roles) {
            collectionList.add(role.getName());
        }
        return collectionList;
    } 
}