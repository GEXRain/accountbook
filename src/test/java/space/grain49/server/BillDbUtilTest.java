package space.grain49.server;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;

import space.grain49.server.model.Bill;

public class BillDbUtilTest {
    private BillDbUtil sBillDbUtil = BillDbUtil.get();
    @Test
    public void testSelectAll(){
        List<Bill> bills = sBillDbUtil.select("13096931652");
        System.out.println(!bills.isEmpty());
        for (Bill bill : bills) {
            System.out.println(bill.getMoney() + bill.getAccountIn() + bill.getAccountOut() + bill.getTimestamp());
        }
    }
    @Test
    public void testInsert() {
        java.util.Date uData = new java.util.Date();
        Timestamp timestamp = new Timestamp(uData.getTime());
        Bill bill = new Bill(DBUtil.getUUID32(), -200.0, "吃饭", "支付宝", null, null, timestamp, "13096931652");
        sBillDbUtil.insert(bill);
    }
}
