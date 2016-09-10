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

import com.github.fedorchuck.developersecurity.domainmodels.ChangeEmail;
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
@Repository("changeEmailJDBCTemplate")
public class ChangeEmailJDBCTemplate {

    private Logger log = Logger.getLogger(ChangeEmailJDBCTemplate.class);
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public void addRecord(String developerToken, String oldEmail, boolean oldEmailStatus, String newEmail, boolean newEmailStatus, Long dateOfRecordCreated) {
        try {
            String SQL =
                    "INSERT INTO change_email (" +
                    "developerToken, " +
                    "old_email, old_email_confirm, " +
                    "new_email, new_email_confirm, " +
                    "dateofrecordcreated) values (?,?,?,?,?,?);";
            jdbcTemplateObject.queryForList(
                    SQL,
                    developerToken,
                    oldEmail,
                    oldEmailStatus,
                    newEmail,
                    newEmailStatus,
                    dateOfRecordCreated
            );
            log.log(Level.INFO, "added record: [ "+developerToken+"; "+oldEmail+"; "+oldEmailStatus+"; "+newEmail+"; "+newEmailStatus+"; "+dateOfRecordCreated);
        } catch (DataIntegrityViolationException ignored){

        }
    }

    public ChangeEmail getCurrent(String developerToken) {
        try {
            return jdbcTemplateObject.queryForObject(
                    "SELECT * FROM change_email WHERE developertoken=?;", new ChangeEmailRowMapper(), developerToken);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void changeStatusForNew(String developerToken) {
        try {
            String SQL =
                    "UPDATE change_email " +
                    "SET new_email_confirm=? " +
                    "WHERE developertoken=?;";
            jdbcTemplateObject.queryForList(SQL,true,developerToken);
        } catch (DataIntegrityViolationException ignored){

        }
    }

    public void changeStatusForOld(String developerToken) {
        try {
            String SQL =
                    "UPDATE change_email " +
                    "SET old_email_confirm=? " +
                    "WHERE developertoken=?;";
            jdbcTemplateObject.queryForList(SQL,true,developerToken);
        } catch (DataIntegrityViolationException ignored){

        }
    }

    public boolean delete(String developerToken) {
        try {
            jdbcTemplateObject.update("DELETE FROM change_email WHERE developertoken=?;",developerToken);
            return true;
        }
        catch (EmptyResultDataAccessException e){
            return false;
        }
    }

    private static class ChangeEmailRowMapper implements RowMapper<ChangeEmail> {
        public ChangeEmail mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ChangeEmail(
                    rs.getString("developerToken"),
                    rs.getString("old_email"),
                    rs.getBoolean("old_email_confirm"),
                    rs.getString("new_email"),
                    rs.getBoolean("new_email_confirm"),
                    rs.getLong("dateOfRecordCreated")
            );
        }
    }
}
