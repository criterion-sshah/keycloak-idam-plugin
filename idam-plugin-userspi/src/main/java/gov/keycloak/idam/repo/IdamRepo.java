package gov.keycloak.idam.repo;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class IdamRepo {

    private Map<String, IdamUser> users = new HashMap<String, IdamUser>();

    public IdamRepo() {
        users.put("huey", new IdamUser(1, "hduck", "duckerberg_police", "Huey D. Duck", "hduck@duckbergpd.com",
                "555-555-0000", "555-555-0001", "Detectives", Set.of("Role1", "Role2", "Role3")));
        users.put("dewey", new IdamUser(2, "dduck", "shush", "Dewey D. Duck", "dduck@shush.org", "555-555-1000",
                "555-555-1001", "Analytics", Set.of("Role1", "Role2", "Role3", "Role4", "Role5")));
        users.put("louie", new IdamUser(3, "lduck", "mcduck_enterprises", "Louie D. Duck",
                "lduck@mcduckenterprises.com", "555-555-2000", "555-555,2001", "Executives", Set.of("Role1")));
    }

    public IdamUser getUser(String username) {
        return users.getOrDefault(username, null);
    }

}