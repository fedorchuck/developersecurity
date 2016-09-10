/*
 * Copyright 2016 Volodymyr Fedorchuk <vl.fedorchuck@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.fedorchuck.developersecurity.dao.impl.postgresql;

import com.github.fedorchuck.developersecurity.domainmodels.ResetPassword;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Transactional
@Repository("resetPasswordJDBCTemplate")
public class ResetPasswordJDBCTemplate {

    private Logger log = Logger.getLogger(ResetPasswordJDBCTemplate.class);
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public void save(ResetPassword resetPassword) {
        try {
            String SQL =
                    "INSERT INTO reset_password (token, email) values (?,?);";
            jdbcTemplateObject.queryForList(
                    SQL,
                    resetPassword.getToken(),
                    resetPassword.getEmail()
            );
            log.log(Level.DEBUG, "added record: "+resetPassword.toString());
        } catch (DataIntegrityViolationException ignored){

        }
    }

    public ResetPassword getRecord(String token) {
        String SQL = "SELECT * FROM reset_password WHERE token=?";
        try {
            return jdbcTemplateObject.queryForObject(SQL, new ResetPasswordRowMapper(), token);
        } catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    /**
     * @return true if token exist.
     * */
    public boolean tokenExist(String token){
        String SQL = "SELECT token FROM reset_password WHERE token=?";
        try {
            jdbcTemplateObject.queryForObject(SQL,String.class,token);
            return true;
        } catch (EmptyResultDataAccessException ex){
            return false;
        }
    }

    public boolean delete(String token) {
        try {
            jdbcTemplateObject.update("DELETE FROM reset_password WHERE token=?;",token);
            return true;
        }
        catch (EmptyResultDataAccessException e){
            return false;
        }
    }

    private static class ResetPasswordRowMapper implements RowMapper<ResetPassword> {
        public ResetPassword mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ResetPassword(
                    rs.getString("token"),
                    rs.getString("email")
            );
        }
    }
}
