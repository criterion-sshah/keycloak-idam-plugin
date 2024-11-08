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

import gov.keycloak.idam.repo.IdamRepo;
import gov.keycloak.idam.repo.IdamUser;

@Slf4j
public class IdamUserStorageProvider implements UserStorageProvider, UserLookupProvider {

  protected KeycloakSession session;
  protected ComponentModel model;
  private Map<String, UserModel> users = new HashMap<>();

  private IdamRepo repo;


  public IdamUserStorageProvider(KeycloakSession session, ComponentModel model) {
    this.model = model;
    this.session = session;
    this.repo = new IdamRepo();
  }

  // User Storage Provider Methods
  @Override
  public void close() {}

  // UserLookupProvider methods

  @Override
  public UserModel getUserByUsername(RealmModel realm, String username) {
    UserModel adapter = users.get(username);
    if (adapter == null) {
      IdamUser user = this.repo.getUser(username);
      if(user != null) {
        adapter = new IdamUserAdapter(this.session, realm, this.model, user);
      }
    }
    return adapter;
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
