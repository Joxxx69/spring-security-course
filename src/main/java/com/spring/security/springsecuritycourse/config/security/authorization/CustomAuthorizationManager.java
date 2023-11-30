package com.spring.security.springsecuritycourse.config.security.authorization;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import com.spring.security.springsecuritycourse.exception.ObjectNotFoundException;
import com.spring.security.springsecuritycourse.persistence.entity.security.Operation;
import com.spring.security.springsecuritycourse.persistence.entity.security.User;
import com.spring.security.springsecuritycourse.persistence.repository.security.OperationRepository;
import com.spring.security.springsecuritycourse.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext>{

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private UserService userService;

    @Override
    @Nullable
    public AuthorizationDecision check(Supplier<Authentication> authentication,
            RequestAuthorizationContext requestContext) {
        HttpServletRequest request = requestContext.getRequest();
        
        String url = extractUrl(request);
        String httpMethod = request.getMethod();
        boolean isPublic = isPublic(url, httpMethod);
        if (isPublic) {
            return new AuthorizationDecision(true);
        }
        boolean isGranted = isGranted(url, httpMethod, authentication.get());
        return new AuthorizationDecision(isGranted);

        
    }

    private boolean isGranted(String url, String httpMethod, Authentication authentication) {
        if (authentication == null || !(authentication instanceof UsernamePasswordAuthenticationToken)) {
            throw new AuthenticationCredentialsNotFoundException("User not logged in");
        }
        List<Operation> operations = obtainOperations(authentication);
        System.out.println(operations);
        boolean isGranted = operations.stream().anyMatch(getOperationPredicate(url, httpMethod));
        System.out.println("is Granted: "+ isGranted);
        return isGranted;
    }

    private static Predicate<Operation> getOperationPredicate(String url, String httpMethod) {
        return operation -> {
            String basePath = operation.getModule().getBasePath();
            Pattern pattern = Pattern.compile(basePath.concat(operation.getPath()));
            Matcher matcher = pattern.matcher(url);
            return matcher.matches() && operation.getHttpMethod().equals(httpMethod);
        };
    }

    private List<Operation> obtainOperations(Authentication authentication) {
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
        String username = (String) authToken.getPrincipal();
        User user = userService.findOneByUsername(username)
            .orElseThrow(()-> new ObjectNotFoundException("User not found. Username: "+ username));
        
        return  user.getRole().getPermissions().stream()
                    .map(grantedPermission ->grantedPermission.getOperation())
                    .collect(Collectors.toList());

    }

    private boolean isPublic(String url, String httpMethod) {
        List<Operation> publicAccessEndPoint = operationRepository.findByPublicAccess();

        boolean isPublic = publicAccessEndPoint.stream().anyMatch(getOperationPredicate(url, httpMethod));
        System.out.println("is public: "+ isPublic);
        return isPublic;

    }

    private String extractUrl(HttpServletRequest request) {

        String contextPath = request.getContextPath();
        String url = request.getRequestURI();
        url = url.replace(contextPath, "");

        return url;
    }
    
}
