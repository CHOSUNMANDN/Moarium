package back.springbootdeveloper.seungchan.controller.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderObject {

  public static String getCryptPassword(String password) {
    return new BCryptPasswordEncoder().encode(password);
  }
}
