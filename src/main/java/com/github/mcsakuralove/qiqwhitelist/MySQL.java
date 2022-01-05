//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.github.mcsakuralove.qiqwhitelist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {
    private String Host;
    private String Port;
    private String Database;
    private String User;
    private String Password;
    private String Table;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    public MySQL() {
    }

    public void savesql(String ip1, String port1, String database1, String user1, String password1, String table1) {
        this.Host = ip1;
        this.Port = port1;
        this.Database = database1;
        this.User = user1;
        this.Password = password1;
        this.Table = table1;
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String DB_URL = "jdbc:mysql://" + this.Host + ":" + this.Port + "/" + this.Database + "?useUnicode=true&characterEncoding=utf-8&useSSL=false";
            this.conn = DriverManager.getConnection(DB_URL, this.User, this.Password);
            this.stmt = this.conn.createStatement();
        } catch (SQLException var2) {
            var2.printStackTrace();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void createtable() {
        try {
            this.stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + this.Database + " default charset utf8 COLLATE utf8_general_ci; ");
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

        String createtable = "CREATE TABLE if not exists " + this.Table + "(id INT primary key AUTO_INCREMENT,username VARCHAR(255) not null)";

        try {
            this.stmt.executeUpdate(createtable);
            if (0 == this.stmt.executeUpdate(createtable)) {
                System.out.println("创建表成功！");
            } else {
                System.out.println("创建表失败！");
            }
        } catch (SQLException var3) {
            var3.printStackTrace();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void closeConn() {
        try {
            if (this.rs != null) {
                this.rs.close();
                this.rs = null;
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        try {
            if (this.stmt != null) {
                this.stmt.close();
                this.stmt = null;
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        try {
            if (this.conn != null) {
                this.conn.close();
                this.conn = null;
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public void AddData(String name) {
        try {
            String new_member = "insert into " + this.Table + " (username) values ('" + name + "')";
            this.stmt.executeUpdate(new_member);
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    public void delete(String name) {
        try {
            String new_member = "delete from " + this.Table + " where username='" + name + "'";
            this.stmt.executeUpdate(new_member);
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    public boolean exists(String name) {
        try {
            this.rs = this.stmt.executeQuery("select 1 from " + this.Table + " where username='" + name + "'");
            return this.rs.next();
        } catch (SQLException var3) {
            var3.printStackTrace();
            return false;
        }
    }
}
