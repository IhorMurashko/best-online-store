package com.beststore.userservice.model;

import com.common.lib.userModule.AuthProvider.AuthProvider;
import com.common.lib.userModule.roles.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Concrete user entity representing a system user with extended profile data.
 *
 * <p>Extends {@link AbstractBasicUser} to include personal information such as name,
 * contact details, and address.</p>
 * <p>
 * Fields:
 * - firstName, lastName: user's full name
 * - phoneNumber: optional contact phone
 * - country, city, streetName, houseNumber, zipCode: address data
 * <p>
 * This entity is persisted in the "users" table.
 *
 * @author Ihot Murashko
 */


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends AbstractBasicUser {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String country;
    private String city;
    private String streetName;
    private String houseNumber;
    private String zipCode;
    private AuthProvider authProvider;
    private String oauthId;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillingInfo> billingInfos = new ArrayList<>();

    public User(String email, String password, AuthProvider authProvider, String oauthId,
                boolean isAccountNonExpired, boolean isAccountNonLocked,
                boolean isCredentialsNonExpired, boolean isEnabled,
                Set<Role> roles) {
        super(email, password, isAccountNonExpired, isAccountNonLocked,
                isCredentialsNonExpired, isEnabled, roles);
        this.authProvider = authProvider;
        this.oauthId = oauthId;
    }
}
