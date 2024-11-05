package gov.keycloak.idam;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

public class IdamUserStorageProviderFactory
    implements UserStorageProviderFactory<IdamUserStorageProvider> {
  @Override
  public IdamUserStorageProvider create(KeycloakSession session, ComponentModel model) {
    return new IdamUserStorageProvider(session, model);
  }

  @Override
  public String getId() {
    return "idam-user-storage";
  }
}
