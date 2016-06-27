package lab;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import com.mysql.jdbc.Driver;
import org.slf4j.Logger;
import com.typesafe.config.*;
/**
 * Created by dozen.zhang on 2016/4/11.
 */
public class C3p0Test {

    private static C3p0Test dbPool;
    private ComboPooledDataSource dataSource;

    static {
        dbPool = new C3p0Test();
    }

    public C3p0Test() {
        try {
            dataSource = new ComboPooledDataSource();
            dataSource.setUser("colamachine");
            dataSource.setPassword("123456");
            dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/colamachine?Unicode=true&characterEncoding=utf8");
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
            dataSource.setInitialPoolSize(2);
            dataSource.setMinPoolSize(1);
            dataSource.setMaxPoolSize(10);
            dataSource.setMaxStatements(50);
            dataSource.setMaxIdleTime(60);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }

    public final static C3p0Test getInstance() {
        return dbPool;
    }

    public final Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("无法从数据源获取连接 ", e);
        }
    }
    class A{
        A(){

        }
        private  A(String s){
            print(s);
        }
        private void  heieh(){
            print("123123");
        }
    }
    private void print(String s){

    }

    public static void main(String[] args) throws SQLException {
        Connection con = null;
        try {
            con = C3p0Test.getInstance().getConnection();
            Thread.sleep(1000000);
        } catch (Exception e) {
        } finally {
            if (con != null)
                con.close();
        }
    }
}

