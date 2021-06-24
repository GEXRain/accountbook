package space.grain49.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import space.grain49.server.model.Bill;

public class BillDbUtil extends DBUtil {
    private static BillDbUtil sBillDbUtil;

    private Connection connection;
    private PreparedStatement stmt;
    private ResultSet rs;

    public static BillDbUtil get() {
        if (sBillDbUtil == null) {
            sBillDbUtil = new BillDbUtil();
        }
        return sBillDbUtil;
    }

    /**
     * @author GRain
     * @return 所有的账单
     */
    public List<Bill> select(String userID) {
        List<Bill> bills = new ArrayList<Bill>();
        try {
            connection = this.getConnection();
            String sql = "select * from bill where userID = ?;";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Bill bill = new Bill(rs.getString(1), rs.getDouble(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getTimestamp(7), rs.getString(8));
                bills.add(bill);
            }
            return bills;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close(connection, stmt, rs);
        }
        return bills;
    }

        /**
     * @author GRain
     * @return 所有的账单
     */
    public List<Bill> selectByDate(String userID, String begin, String end) {
        List<Bill> bills = new ArrayList<Bill>();
        try {
            connection = this.getConnection();
            String sql = "select * from bill where userID = ? and time > ? AND time <?;";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userID);
            stmt.setString(2, begin + " 00:00:00");
            stmt.setString(3, end + " 00:00:00");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Bill bill = new Bill(rs.getString(1), rs.getDouble(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getTimestamp(7), rs.getString(8));
                bills.add(bill);
            }
            return bills;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close(connection, stmt, rs);
        }
        return bills;
    }

    /**
     * @author GRain
     * @param bill
     * @return 受影响的行数
     */
    public int insert(Bill bill) {
        int line = 0;
        try {
            String sql = "insert into bill values(?,?,?,?,?,?,?,?);";
            connection = this.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, bill.getUUID());
            stmt.setDouble(2, bill.getMoney());
            stmt.setString(3, bill.getType());
            stmt.setString(4, bill.getAccountOut());
            stmt.setString(5, bill.getAccountIn());
            stmt.setString(6, bill.getNote());
            stmt.setTimestamp(7, bill.getTimestamp());
            stmt.setString(8, bill.getUserID());
            line = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close(connection, stmt, rs);
        }
        return line;
    }
}
