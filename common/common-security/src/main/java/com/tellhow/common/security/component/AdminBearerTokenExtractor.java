package com.tellhow.common.security.component;

import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/13 13:43
 * @descripe
 */
public class AdminBearerTokenExtractor implements BearerTokenResolver {
//    授权模式
    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-:._~+/]+=*)$",Pattern.CASE_INSENSITIVE);

    private static final String  ACCESS_TOKEN = "access_token";

//    允许表单编码正文参数
    private boolean allowFormEncodeBodyParameter = false;
//    允许 URL 查询参数
    private boolean allowUrlQueryParameter = false;
//    不记名令牌头名称  Authorization
    private String bearerTokenHeaderName = HttpHeaders.AUTHORIZATION;

//    路径匹配器
    private final PathMatcher pathMatcher = new AntPathMatcher();

    private final PermitAllUrlProperties urlProperties;

    public AdminBearerTokenExtractor(PermitAllUrlProperties permitAllUrlProperties){
        this.urlProperties = permitAllUrlProperties;
    }



    @Override
    public String resolve(HttpServletRequest request) {

        boolean match = urlProperties.getUrls().stream().anyMatch(url -> pathMatcher.match(url,request.getRequestURI()));
        if (match){
            return null;
        }
        String authorizationHeaderToken = resolveFromAuthorizationHeader(request);

        return null;
    }

    /**
     * authorization 解析
     * @param request
     * @return
     */
    public String resolveFromAuthorizationHeader(HttpServletRequest request){
        String authorization = request.getHeader(this.bearerTokenHeaderName);
//        authorizationPattern 开头格式
        if (!StringUtils.startsWithIgnoreCase(authorization,"bearer")){
            return null;
        }
        Matcher matcher = authorizationPattern.matcher(authorization);
        if (!matcher.matches()){
//           令牌错误
            BearerTokenError error = BearerTokenErrors.invalidToken("Bearer token is malformed");
            throw new OAuth2AuthorizationException(error);
        }
        return matcher.group("token");
    }

    /**
     * token 解析
     * @param request
     * @return
     */
    public static String resolveFromRequestParameters(HttpServletRequest request){
        String[] parameterValues = request.getParameterValues(ACCESS_TOKEN);
        if (parameterValues == null || parameterValues.length == 0){
            return null;
        }
        if (parameterValues.length == 1){
            return parameterValues[0];
        }

        BearerTokenError error = BearerTokenErrors.invalidToken("Found multiple beareer tokens in the requese");
        throw new OAuth2AuthorizationException(error);
    }



}
