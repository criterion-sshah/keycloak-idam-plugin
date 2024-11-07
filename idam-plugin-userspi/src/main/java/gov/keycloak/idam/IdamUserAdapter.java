package gov.keycloak.idam;

import gov.keycloak.idam.repo.IdamUser;

import lombok.Getter;
import org.keycloak.common.util.MultivaluedHashMap;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

public class IdamUserAdapter extends AbstractUserAdapterFederatedStorage {
    private IdamUser user;
    private String firstName;
    private String lastName;

    public IdamUserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, IdamUser user) {
        super(session, realm, model);
        this.user = user;
        this.storageId = new StorageId(this.storageProviderModel.getId(), Integer.toString(this.user.getIdNumber()));
        String fullName = this.user.getDisplayName();
        int idx = fullName.lastIndexOf(' ');
        if (idx == -1)
            this.firstName = fullName;
        else {
            this.firstName = fullName.substring(0, idx);
            this.lastName = fullName.substring(idx + 1);
        }
        this.setSingleAttribute("homePhone", this.user.getHomePhone());
        this.setSingleAttribute("workPhone", this.user.getWorkPhone());
        this.setSingleAttribute("organization", this.user.getOrganization());
        this.setSingleAttribute("customerOrg", this.user.getCustomerOrg());
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public String getEmail() {
        return this.user.getEmail();
    }

    @Override
    public void setUsername(String username) {
        throw new RuntimeException("This storage provider does not have the ability to set a username!");
    }

}