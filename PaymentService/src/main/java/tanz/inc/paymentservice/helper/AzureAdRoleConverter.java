package tanz.inc.paymentservice.helper;

import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collection;
import org.springframework.core.convert.converter.Converter;

public class AzureAdRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        // Azure AD sends roles in the "roles" claim
        List<String> roles = jwt.getClaimAsStringList("roles");
        if (roles == null) return List.of();

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("APPROLE_" + role))
                .collect(Collectors.toList());
    }
}
