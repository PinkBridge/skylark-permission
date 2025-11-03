package cn.skylark.permission.jwt;

/**
 * todo
 *
 * @author yaomianwei
 * @since 17:38 2025/10/31
 **/

import cn.skylark.permission.jwt.exception.JwtException;
import cn.skylark.permission.jwt.exception.JwtExceptionEnum;
import cn.skylark.permission.jwt.models.JwtRequestModel;
import cn.skylark.permission.jwt.models.JwtResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// @RestController
// @CrossOrigin
public class JwtController {
  @Autowired
  private JwtUserDetailsService userDetailsService;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private TokenManager tokenManager;

  @PostMapping("/api/auth/login")
  public JwtResponseModel<String> createToken(@RequestBody JwtRequestModel request) throws JwtException {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
      );
    } catch (DisabledException e) {
      throw new JwtException(JwtExceptionEnum.USER_DISABLED);
    } catch (BadCredentialsException e) {
      throw new JwtException(JwtExceptionEnum.INVALID_CREDENTIALS);
    }
    final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
    final String jwtToken = tokenManager.generateJwtToken(userDetails);
    return JwtResponseModel.data(jwtToken);
  }
}
