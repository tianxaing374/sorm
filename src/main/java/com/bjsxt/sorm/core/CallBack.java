package com.bjsxt.sorm.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface CallBack {
    Object doExecuteQuery(Connection connection, PreparedStatement ps, ResultSet rs);
}
