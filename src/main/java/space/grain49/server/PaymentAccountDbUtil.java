package space.grain49.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import space.grain49.server.model.PaymentAccount;

public class PaymentAccountDbUtil extends DBUtil {
    private static PaymentAccountDbUtil sPaymentAccountDbUtil;

    private Connection connection;
    private PreparedStatement stmt;
    private ResultSet rs;

    public static PaymentAccountDbUtil get() {
        if (sPaymentAccountDbUtil == null) {
            sPaymentAccountDbUtil = new PaymentAccountDbUtil();
        }
        return sPaymentAccountDbUtil;
    }

    /**
     * @author GRain
     * @return 所有的账单
     */
    public List<PaymentAccount> select(String userID) {
        List<PaymentAccount> paymentAccounts = new ArrayList<PaymentAccount>();
        try {
            connection = this.getConnection();
            String sql = "select * from paymentAccount where userID = ?;";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                PaymentAccount paymentAccount = new PaymentAccount(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getDouble(4), rs.getString(5), rs.getString(6));
                paymentAccounts.add(paymentAccount);
            }
            return paymentAccounts;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close(connection, stmt, rs);
        }
        return paymentAccounts;
    }



    public int updatePaymentAccount(PaymentAccount paymentAccount){
        int line = 0;
        try {
            connection = this.getConnection();
            String sql = "update paymentAccount set ID = ?, name = ?, type = ?, money = ?, note = ? where userID = ? and ID = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, paymentAccount.getUuid());
            stmt.setString(2, paymentAccount.getName());
            stmt.setString(3, paymentAccount.getType());
            stmt.setDouble(4, paymentAccount.getMoney());
            stmt.setString(5, paymentAccount.getNote());
            stmt.setString(6, paymentAccount.getUserID());
            stmt.setString(7, paymentAccount.getUuid());
            line = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return line;
    }

    public int insert(PaymentAccount paymentAccount) {
        int line = 0;
        try {
            String sql = "insert into paymentAccount values(?,?,?,?,?,?);";
            connection = this.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, paymentAccount.getUuid());
            stmt.setString(2, paymentAccount.getName());
            stmt.setString(3, paymentAccount.getType());
            stmt.setDouble(4, paymentAccount.getMoney());
            stmt.setString(5, paymentAccount.getNote());
            stmt.setString(6, paymentAccount.getUserID());
            line = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close(connection, stmt, rs);
        }
        return line;
    }
}
