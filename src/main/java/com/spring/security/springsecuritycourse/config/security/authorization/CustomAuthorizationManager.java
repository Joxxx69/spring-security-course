package com.spring.security.springsecuritycourse.config.security.authorization;

import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import com.spring.security.springsecuritycourse.persistence.entity.security.Operation;
import com.spring.security.springsecuritycourse.persistence.repository.security.OperationRepository;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext>{

    @Autowired
    private OperationRepository operationRepository;

    @Override
    @Nullable
    public AuthorizationDecision check(Supplier<Authentication> authentication,
            RequestAuthorizationContext requestContext) {
        HttpServletRequest request = requestContext.getRequest();
        
        String url = extractUrl(request);
        String httpMethod = request.getMethod();
        boolean isPublic = isPublic(url, httpMethod);
        
        return new AuthorizationDecision(isPublic);
    }

    private boolean isPublic(String url, String httpMethod) {
        List<Operation> publicAccessEndPoint = operationRepository.findByPublicAccess();

        boolean isPublic = publicAccessEndPoint.stream().anyMatch(operation -> {
            String basePath = operation.getModule().getBasePath();
            Pattern pattern = Pattern.compile(basePath.concat(operation.getPath()));
            Matcher matcher = pattern.matcher(url);
            return matcher.matches() && operation.getHttpMethod().equals(httpMethod);
        });
        return isPublic;

    }

    private String extractUrl(HttpServletRequest request) {

        String contextPath = request.getContextPath();
        String url = request.getRequestURI();
        url = url.replace(contextPath, "");

        return url;
    }
    
}
