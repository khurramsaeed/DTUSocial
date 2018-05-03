package grp21.dtusocial.service;

import grp21.dtusocial.resource.filters.SecurityFilter;
import io.jsonwebtoken.Jwts;

/**
 *
 * @author Khurram Saeed Malik
 * Extracts username from authorization token
 * username is than used for various services, such as chat
 */
public class JWTService {

    public static String resolveUser(String authHeader){
        String token = authHeader.substring("Bearer".length()).trim();
        return Jwts.parser().setSigningKey(SecurityFilter.generatedKey).parseClaimsJws(token).getBody().get("username").toString();
    }
}