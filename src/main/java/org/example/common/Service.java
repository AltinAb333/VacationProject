package org.example.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class Service {
    public void closeConnection(Connection con) throws Exception {
        if (con != null) {
            con.close();
        }
    }

    public void closePreparedStatement(PreparedStatement... ps) throws Exception {
        for (PreparedStatement p : ps)
            if (p != null) {
                p.close();
            }
    }

    public void closeResultSet(ResultSet rs) throws Exception {
        if (rs != null) {
            rs.close();
        }
    }

    public void rollBackConnection(Connection con) throws Exception {
        if (con != null) {
            con.rollback();
        }
    }
}
