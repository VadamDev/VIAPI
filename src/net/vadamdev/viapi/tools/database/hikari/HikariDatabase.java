package net.vadamdev.viapi.tools.database.hikari;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author VadamDev
 * @since 24/08/2023
 */
public class HikariDatabase extends AbstractHikariDatabase {
    public HikariDatabase(Logger logger, int maxPoolSize) {
        super(logger, maxPoolSize);
    }

    @Override
    public void set(String a, String b, Object object, String column, String table) {
        executeUpdate("UPDATE " + table + " SET " + column + " = " + object + " WHERE " + a + " = " + b);
    }

    @Nullable
    @Override
    public Object get(String a, String b, String column, String table) {
        return executeQuery("SELECT " + column + " FROM " + table + " WHERE " + a + " = " + b, rs -> {
            try {
                return rs.next() ? rs.getObject(column) : null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    @Override
    public List<Object> takeAll(String column, String table) {
        return executeQuery("SELECT * FROM " + table, rs -> {
            List<Object> list = new ArrayList<>();

            try {
                while(rs.next()) {
                    list.add(rs.getObject(column));
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }

            return list;
        });
    }
}
