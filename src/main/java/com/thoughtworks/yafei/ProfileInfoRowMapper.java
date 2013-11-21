package com.thoughtworks.yafei;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileInfoRowMapper implements RowMapper<ProfileInfo> {

    @Override
    public ProfileInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
