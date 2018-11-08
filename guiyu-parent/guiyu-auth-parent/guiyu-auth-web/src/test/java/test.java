import com.guiji.auth.util.AuthUtil;
import com.guiji.user.dao.entity.SysUser;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by ty on 2018/11/5.
 */
public class test {
    @Test
    public void test89() {


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密
        String encodedPassword = AuthUtil.encrypt("1");
        System.out.println(encodedPassword);
    }

}
