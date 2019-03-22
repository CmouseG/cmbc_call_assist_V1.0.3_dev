package com.guiji.cloud.zuul.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.guiji.cloud.zuul.entity.WxAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Component
public class JwtConfig {
    /**
     * JWT 自定义密钥 我这里写死的
     */
    private static final String SECRET_KEY = "ed71f..8a45e5ab1fuckyoue0932!@f2444713zx";
    /**
     * JWT 过期时间值 这里写死为和小程序时间一致 7200 秒，也就是两个小时
     */
    private static long expire_time = 7200;

    /**
     * 刷新token时间，5天  60*60*24*5
     */
    private static long refresh_time = 432000;
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 根据微信用户登陆信息创建 token
     * 注 : 这里的token会被缓存到redis中,用作为二次验证
     * redis里面缓存的时间应该和jwt token的过期时间设置相同
     *
     * @param wxAccount 微信用户信息
     * @return 返回 jwt token
     */
    public String createTokenByWxAccount(WxAccount wxAccount) {
        String jwtId = UUID.randomUUID().toString();                 //JWT 随机ID,做为验证的key
        //1 . 加密算法进行签名得到token
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String token = JWT.create()
                .withClaim("userId", wxAccount.getUserId())
                .withClaim("orgCode", wxAccount.getOrgCode())
                .withClaim("isSuperAdmin", wxAccount.getSuperAdmin())
                .withClaim("isDesensitization", wxAccount.getIsDesensitization())
                .withClaim("jwt-id", jwtId)
                .withClaim("orgId", wxAccount.getOrgId())
                .withExpiresAt(new Date(System.currentTimeMillis() + expire_time*1000))  //JWT 配置过期时间的正确姿势
                .sign(algorithm);
        //2 . Redis缓存JWT, 注 : 请和JWT过期时间一致
        redisTemplate.opsForValue().set("JWT-SESSION-" + jwtId, token, expire_time, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("JWT-REFRESH-SESSION-" + jwtId, token, refresh_time, TimeUnit.SECONDS);
        return token;
    }
    /**
     * 校验token是否正确
     * 1 . 根据token解密，解密出jwt-id , 先从redis中查找出redisToken，匹配是否相同
     * 2 . 然后再对redisToken进行解密，解密成功则 继续流程 和 进行token续期
     *
     * @param token 密钥
     * @return 返回是否校验通过
     */
    public boolean verifyToken(String token) {
        try {
            //1 . 根据token解密，解密出jwt-id , 先从redis中查找出redisToken，匹配是否相同
            String redisToken = redisTemplate.opsForValue().get("JWT-SESSION-" + getJwtIdByToken(token));
            if (!redisToken.equals(token)) return false;
            //2 . 得到算法相同的JWTVerifier
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userId", getUserIdByToken(redisToken))
                    .withClaim("orgCode", getOrgCodeByToken(redisToken))
                    .withClaim("isSuperAdmin", getSuperAdminByToken(redisToken))
                    .withClaim("isDesensitization", getIsDesensitizationByToken(redisToken))
                    .withClaim("jwt-id", getJwtIdByToken(redisToken))
                    .withClaim("orgId", getOrgIdByToken(redisToken))
                    .acceptExpiresAt(System.currentTimeMillis() + expire_time*1000 )  //JWT 正确的配置续期姿势
                    .build();
            //3 . 验证token
            verifier.verify(redisToken);
            //4 . Redis缓存JWT续期
            redisTemplate.opsForValue().set("JWT-SESSION-" + getJwtIdByToken(token), redisToken, expire_time, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set("JWT-REFRESH-SESSION-" + getJwtIdByToken(token), redisToken, refresh_time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) { //捕捉到任何异常都视为校验失败
            return false;
        }
    }

	/**
     * 校验refresh token是否正确
     */
    public String verifyRefreshToken(String token) throws Exception {

            //1 . 根据token解密，解密出jwt-id , 先从redis中查找出redisToken，匹配是否相同
            String redisToken = redisTemplate.opsForValue().get("JWT-REFRESH-SESSION-" + getJwtIdByToken(token));
            if (!redisToken.equals(token)){
                throw new Exception("token not right");
            }
            //2 . 得到算法相同的JWTVerifier
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            Long userId = getUserIdByToken(redisToken);
            String orgCode = getOrgCodeByToken(redisToken);
            Boolean isSuperAdmin = getSuperAdminByToken(redisToken);
            Integer isDesensitization = getIsDesensitizationByToken(redisToken);
            String oldJwtId = getJwtIdByToken(redisToken);
            Long orgId = getOrgIdByToken(redisToken);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userId", userId)
                    .withClaim("orgCode", orgCode)
                    .withClaim("isSuperAdmin", isSuperAdmin)
                    .withClaim("jwt-id", getJwtIdByToken(redisToken))
                    .withClaim("isDesensitization", isDesensitization)
                    .withClaim("orgId", orgId)
                    .acceptExpiresAt(System.currentTimeMillis() + refresh_time*1000 )  //JWT 正确的配置续期姿势
                    .build();
            //3 . 验证token
            verifier.verify(redisToken);

            //生成新的token
            String newJwtId = UUID.randomUUID().toString();
            //1 . 加密算法进行签名得到token
            String newToken = JWT.create()
                    .withClaim("userId", userId)
                    .withClaim("orgCode", orgCode)
                    .withClaim("isSuperAdmin", isSuperAdmin)
                    .withClaim("jwt-id", newJwtId)
                    .withClaim("isDesensitization", isDesensitization)
                    .withClaim("orgId", orgId)
                    .withExpiresAt(new Date(System.currentTimeMillis() + expire_time*1000))  //JWT 配置过期时间的正确姿势
                    .sign(algorithm);
            //2 . Redis缓存JWT,
            redisTemplate.opsForValue().set("JWT-SESSION-" + newJwtId, newToken, expire_time, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set("JWT-REFRESH-SESSION-" + newJwtId, newToken, refresh_time, TimeUnit.SECONDS);

            //4 . 原来的token其实可以删掉了
            redisTemplate.delete("JWT-REFRESH-SESSION-" + oldJwtId);
            return newToken;
    }


    public void deleteToken(String token){
        redisTemplate.delete("JWT-SESSION-" + getJwtIdByToken(token));
    }
    /**
     * 根据Token获取wxOpenId(注意坑点 : 就算token不正确，也有可能解密出wxOpenId,同下)
     */
    public Long getUserIdByToken(String token) throws JWTDecodeException {
        return JWT.decode(token).getClaim("userId").asLong();
    }
    /**
     * 根据Token获取sessionKey
     */
    public String getOrgCodeByToken(String token) throws JWTDecodeException {
        return JWT.decode(token).getClaim("orgCode").asString();
    }
    /**
     * 根据Token获取sessionKey
     */
    public Boolean getSuperAdminByToken(String token) throws JWTDecodeException {
        return JWT.decode(token).getClaim("isSuperAdmin").asBoolean();
    }
    /**
     * 根据Token 获取jwt-id
     */
    private String getJwtIdByToken(String token) throws JWTDecodeException {
        return JWT.decode(token).getClaim("jwt-id").asString();
    }
    /**
     * 根据Token获取wxOpenId(注意坑点 : 就算token不正确，也有可能解密出wxOpenId,同下)
     */
    public Integer getIsDesensitizationByToken(String token) throws JWTDecodeException {
        return JWT.decode(token).getClaim("isDesensitization").asInt();
    }
    
    public Long getOrgIdByToken(String token) throws JWTDecodeException
	{
    	return JWT.decode(token).getClaim("orgId").asLong();
	}
}
