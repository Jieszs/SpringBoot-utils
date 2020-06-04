package com.example.demo.jwtToken;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.constant.HttpHeader;
import com.example.demo.exception.UnauthorizedException;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Token工具类
 *
 * 依赖
 *         <dependency>
 *             <groupId>com.auth0</groupId>
 *             <artifactId>java-jwt</artifactId>
 *             <version>3.4.0</version>
 *         </dependency>
 * @date 2020/1/14
 */
public class TokenUtils {
    private static final String TOKEN_SECRET = "centerm2020";//token签名key
    public static final int TOKEN_EXPIRE_TIME = 12 * 60;//token过期时间(分钟)

    private static String get(HttpServletRequest request, String key) throws UnauthorizedException {
        if (request == null || StringUtils.isEmpty(key)) return null;
        String token = request.getHeader(HttpHeader.AUTHORIZATION);
        if (StringUtils.isEmpty(token)) return null;
        return parse(token).get(key).asString();
    }


    public static Integer getInteger(HttpServletRequest request, String key) throws UnauthorizedException {
        String val = get(request, key);
        return StringUtils.isEmpty(val) ? null : Integer.valueOf(val);
    }

    public static boolean getBoolean(HttpServletRequest request, String key) throws UnauthorizedException {
        String val = get(request, key);
        return StringUtils.isEmpty(val) ? null : Boolean.valueOf(val);
    }


    public static String getString(HttpServletRequest request, String key) throws UnauthorizedException {
        return get(request, key);
    }
    /**
     * 创建token
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String createToken(Map<String, String> data) {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, TOKEN_EXPIRE_TIME);
        Date expiresDate = nowTime.getTime();
        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        JWTCreator.Builder builder = JWT.create().withHeader(map) // header
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate); // expire time
        for (Map.Entry<String, String> e : data.entrySet()) {
            builder.withClaim(e.getKey(), e.getValue());
        }
        String token = builder.sign(Algorithm.HMAC256(TOKEN_SECRET)); // signature
        return token;
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> parse(String token) throws UnauthorizedException {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnauthorizedException("invalid token");
        }
    }

    /**
     * 校验token
     *
     * @param token
     * @return 1:校验通过,0:过期,-1:校验不通过
     */
    public static int verify(String token) throws UnauthorizedException {
        try {
            parse(token);
            return 1;
        } catch (TokenExpiredException e) {
            return 0;
        } catch (JWTDecodeException | SignatureVerificationException e) {
            return -1;
        }
    }

    public static void main(String[] args) throws UnauthorizedException {
        Map<String, String> data = new HashMap<>();
        data.put("username", "123456");
        data.put("password", "123456");
        String jwtToken = TokenUtils.createToken(data);
        System.out.println(jwtToken);
        System.out.println(verify(jwtToken));
        System.out.println(parse(jwtToken).get("username").asString());
    }

}
