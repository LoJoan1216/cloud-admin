package com.tellhow.common.security.util;

import cn.hutool.core.map.MapUtil;
import lombok.experimental.UtilityClass;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/8 17:20
 * @descripe oauth2 端点工具
 */
@UtilityClass
public class OAuth2EndpointUtils {

    public final String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";


    public MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
        parameterMap.forEach((key, values) -> {
            if (values.length > 0) {
                for (String value : values) {
                    parameters.add(key, value);
                }
            }
        });
        return parameters;
    }

    /**
     * 匹配Pkce令牌请求
     *
     * @param request
     * @return
     */
    public boolean matchersPkceTokenRequest(HttpServletRequest request) {
        return AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(request.getParameter(OAuth2ParameterNames.GRANT_TYPE))
                && request.getParameter(OAuth2ParameterNames.CODE) != null && request.getParameter(PkceParameterNames.CODE_VERIFIER) != null;
    }

    public void throwError(String errorCode, String parameterName, String errorUri) {
        OAuth2Error error = new OAuth2Error(errorCode, "Oauth2 Parameter:" + parameterName, errorUri);
        throw new OAuth2AuthenticationException(error);
    }


    /**
     * 格式化token信息
     *
     * @param authorization
     * @param claims
     * @return
     */
    public OAuth2AccessTokenResponse sendAccessTokenResponse(OAuth2Authorization authorization, Map<String, Object> claims) {
        OAuth2AccessToken accessToken = authorization.getAccessToken().getToken();
        OAuth2RefreshToken refreshToken = authorization.getRefreshToken().getToken();
        OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
                .tokenType(accessToken.getTokenType()).scopes(accessToken.getScopes());

        if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
            builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
        }

        if (refreshToken != null) {
            builder.refreshToken(refreshToken.getTokenValue());
        }
        if (MapUtil.isEmpty(claims)) {
            builder.additionalParameters(claims);
        }
        return builder.build();
    }
}
