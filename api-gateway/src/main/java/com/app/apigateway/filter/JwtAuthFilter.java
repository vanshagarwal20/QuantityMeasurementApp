package com.app.apigateway.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;

@Component
public class JwtAuthFilter extends
    AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    @Value("${jwt.secret}")
    private String secret;

    public JwtAuthFilter() { super(Config.class); }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String authHeader = request.getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return onError(exchange,
                    "Missing or invalid Authorization header",
                    HttpStatus.UNAUTHORIZED);
            }
            
            System.out.println("Authorization Header: " + exchange.getRequest().getHeaders().getFirst("Authorization"));

            String token = authHeader.substring(7);
            try {
                Key key = Keys.hmacShaKeyFor(secret.getBytes());
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key).build()
                        .parseClaimsJws(token).getBody();

                // Forward username as header to downstream service
                ServerHttpRequest mutatedRequest = request.mutate()
                        .header("X-User-Name", claims.getSubject())
                        .build();
                return chain.filter(exchange.mutate()
                        .request(mutatedRequest).build());

            } catch (ExpiredJwtException e) {
                return onError(exchange, "Token expired",
                        HttpStatus.UNAUTHORIZED);
            } catch (JwtException e) {
                return onError(exchange, "Invalid token",
                        HttpStatus.UNAUTHORIZED);
            }
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange,
            String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().add("Content-Type", "application/json");
        byte[] body = ("{\"error\":\"" + message + "\"}").getBytes();
        return response.writeWith(Mono.just(
                response.bufferFactory().wrap(body)));
    }

    public static class Config {}
}
