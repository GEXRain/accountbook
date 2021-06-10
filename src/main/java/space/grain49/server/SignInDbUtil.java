package space.grain49.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 * Sign_in
 */
public class SignInDbUtil extends DBUtil{

    private static SignInDbUtil sSignInDbUtil;
    private static BasicTextEncryptor textEncryptor;
    private Connection connection;
    private PreparedStatement stmt;
    private ResultSet rs;

    // 设置加密密钥
    static {
        textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("gyhgxy");
    }

    // 加密
    public static String encrypt(String textString) {
        return textEncryptor.encrypt(textString);
    }

    // 解密
    public static String decrypt(String textString) {
        return textEncryptor.decrypt(textString);
    }

    public static SignInDbUtil get(){
        if(sSignInDbUtil == null){
            sSignInDbUtil = new SignInDbUtil();
        }
        return sSignInDbUtil;
    }

    public boolean signIn(String account, String password) {
        try {
            connection = this.getConnection();
            String sql = "SELECT * FROM `sign_in` WHERE account = ?;";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, account);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return password.equals(rs.getString(2));
            }
        } catch (Exception e) {
            System.out.println("warring: server is not connect");
        } finally {
            this.close(connection, stmt, rs);
        }
        return false;
    }

    public static class SignInData {
        private static String account_number;
        private static String password;
        private static boolean isSignIn;

        public static void setAccount(String string) {
            account_number = string;
        }

        public static void setPassword(String string) {
            password = string;
        }

        public static void setisSignIn() {
            isSignIn = true;
        }

        public static String getAccount() {
            return account_number;
        }

        public static String getPassword() {
            return password;
        }

        public static boolean isSignIn() {
            return isSignIn;
        }
    }
}