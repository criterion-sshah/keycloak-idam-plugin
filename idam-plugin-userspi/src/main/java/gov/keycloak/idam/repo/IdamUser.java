package gov.keycloak.idam.repo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class IdamUser {
    private int idNumber;
    private String userName;
    private String organization;
    private String displayName;
    private String email;
    private String workPhone;
    private String homePhone;
    private String customerOrg;
    private Set<String> roles;

    public IdamUser(int idNumber, String username, String organization, String displayName, String email, String workPhone, String homePhone, String customerOrg, Set<String> roles) {
        this.idNumber = idNumber;
        this.userName = username;
        this.organization = organization;
        this.displayName = displayName;
        this.email = email;
        this.workPhone = workPhone;
        this.homePhone = homePhone;
        this.customerOrg = customerOrg;
        this.roles = roles;
    }



}