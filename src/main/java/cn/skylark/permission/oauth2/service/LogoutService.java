package cn.skylark.permission.oauth2.service;

import cn.skylark.permission.oauth2.OauthConfig;
import cn.skylark.permission.oauth2.mapper.RefreshTokenMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yaomianwei
 * @since 2025/11/3
 */
@Service
@SuppressWarnings("deprecation")
public class LogoutService {
  @Resource
  private RefreshTokenMapper refreshTokenMapper;
  @Resource
  private OauthConfig oauthConfig;

  /**
   * logout: delete refresh token
   * extract information from JWT access token, then delete the corresponding refresh token
   *
   * @param accessToken JWT access token
   * @return whether the refresh token is deleted successfully
   */
  public boolean logout(String accessToken) {
    if (accessToken == null || accessToken.isEmpty()) {
      return false;
    }

    // parse JWT token，extract all possible token IDs
    Claims claims = parseJwtToken(accessToken);
    if (claims == null) {
      return false;
    }

    // try to delete refresh token by priority
    // 1. try to delete refresh token by jti (JWT ID)
    String jti = claims.getId();
    if (tryDeleteRefreshToken(jti)) {
      return true;
    }

    // 2. try to delete refresh token by ati (Access Token Identifier)
    String ati = claims.get("ati", String.class);
    if (tryDeleteRefreshToken(ati)) {
      return true;
    }

    return false;
  }

  /**
   * parse JWT token，extract Claims
   *
   * @param accessToken JWT access token
   * @return Claims, if the token is invalid, expired or format error, return null
   */
  private Claims parseJwtToken(String accessToken) {
    try {
      return Jwts.parser()
              .setSigningKey(oauthConfig.getSigningKey())
              .parseClaimsJws(accessToken)
              .getBody();
    } catch (Exception e) {
      // Token is invalid, expired or format error
      return null;
    }
  }

  /**
   * try to delete refresh token by token id
   *
   * @param tokenId token ID
   * @return
   */
  private boolean tryDeleteRefreshToken(String tokenId) {
    if (tokenId == null || tokenId.isEmpty()) {
      return false;
    }
    int deleted = refreshTokenMapper.deleteByTokenId(tokenId);
    return deleted > 0;
  }

  /**
   * delete refresh token by token id directly
   *
   * @param tokenId refresh token id
   * @return whether the refresh token is deleted successfully
   */
  public boolean logoutByTokenId(String tokenId) {
    if (tokenId == null || tokenId.isEmpty()) {
      return false;
    }
    int deleted = refreshTokenMapper.deleteByTokenId(tokenId);
    return deleted > 0;
  }
}
