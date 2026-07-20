package com.example.manager.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class IpInterceptor implements HandlerInterceptor {

    private static final List<String> ALLOWED_IPS = List.of(
            "185.71.76.0/27",
            "185.71.77.0/27",
            "77.75.153.0/25",
            "77.75.156.11",
            "77.75.156.35",
            "77.75.154.128/25",
            "2a02:5180::/32"
    );

    private static final String[] RPOXY_HEADERS = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    private String getClientIpAddress(HttpServletRequest request) {
        for (String header : RPOXY_HEADERS) {
            String ip = request.getHeader(header);
            if ((ip != null) && (!ip.isEmpty()) && (!"unknown".equalsIgnoreCase(ip))) {
                return ip.split(",")[0].trim();
            }
        }

        return request.getRemoteAddr();
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {

        String clientIp = getClientIpAddress(request);

        if (!ALLOWED_IPS.contains(clientIp)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied by IP");
            return false;
        }
        return true;
    }

}
