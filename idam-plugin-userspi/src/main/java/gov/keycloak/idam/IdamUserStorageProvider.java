package gov.keycloak.idam;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.UserCredentialManager;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.SubjectCredentialManager;
import org.keycloak.models.UserModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.keycloak.storage.user.UserLookupProvider;

@Slf4j
public class IdamUserStorageProvider implements UserStorageProvider, UserLookupProvider {

  protected KeycloakSession session;
  protected ComponentModel model;
  private Map<String, UserModel> users = new HashMap<>();

  public IdamUserStorageProvider(KeycloakSession session, ComponentModel model) {
    this.model = model;
    this.session = session;
  }

  // User Storage Provider Methods
  @Override
  public void close() {}

  // UserLookupProvider methods

  @Override
  public UserModel getUserByUsername(RealmModel realm, String username) {
    UserModel adapter = users.get(username);
    if (adapter == null) {
      adapter = createAdapter(realm, username);
      users.put(username, adapter);
    }
    return adapter;
  }

  protected UserModel createAdapter(RealmModel realm, String username) {
    return new AbstractUserAdapter(session, realm, model) {
      @Override
      public String getUsername() {
        return username;
      }

      @Override
      public SubjectCredentialManager credentialManager() {
        return new UserCredentialManager(session, realm, this);
      }
    };
  }

  @Override
  public UserModel getUserById(RealmModel realm, String id) {
    StorageId storageId = new StorageId(id);
    String username = storageId.getExternalId();
    return getUserByUsername(realm, username);
  }

  @Override
  public UserModel getUserByEmail(RealmModel realm, String email) {
    return null;
  }
}
