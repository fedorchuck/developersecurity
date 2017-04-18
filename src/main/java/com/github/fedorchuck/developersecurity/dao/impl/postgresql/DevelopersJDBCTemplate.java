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

import com.github.fedorchuck.developersecurity.domainmodels.Developer;
import com.github.fedorchuck.developersecurity.utils.Constants;
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
import java.util.Arrays;
import java.util.List;

@Transactional
@Repository("developersJDBCTemplate")
public class DevelopersJDBCTemplate {

    private Logger log = Logger.getLogger(DevelopersJDBCTemplate.class);
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public void save(Developer developer) {
        try {
            String SQL =
                    "INSERT INTO developers (" +
                    "developertoken, " +
                    "email, password, " +
                    "name, enabled, " +
                    "dateofrecordcreated, dateoflastused) values (?,?,?,?,?,?,?);" +
                    "INSERT INTO user_roles(email,role) values (?,?)";
            jdbcTemplateObject.queryForList(
                    SQL,
                    developer.getDeveloperToken(),
                    developer.getEmail(),
                    developer.getPassword(),
                    developer.getName(),
                    developer.getEnabled(),
                    developer.getDateOfRecordCreated(),
                    developer.getDateOfLastUsed(),
                    developer.getEmail(),
                    developer.getRole()
            );
            log.log(Level.DEBUG, "added record: "+developer.toString());
        } catch (DataIntegrityViolationException ignored){

        }
    }

    /**
     * @return true if token exist.
     * */
    public boolean tokenExist(String token){
        String SQL = "SELECT developertoken FROM developers WHERE developertoken=?";
        try {
            return 0 < jdbcTemplateObject.queryForObject(SQL,String.class,token).length();
        } catch (EmptyResultDataAccessException ex){
            return false;
        }
    }

    /**
     * @return true if email exist.
     * */
    public boolean emailExist(String email){
        String SQL = "SELECT email FROM developers WHERE email=?";
        try {
            return 0 < jdbcTemplateObject.queryForObject(SQL,String.class,email).length();
        } catch (EmptyResultDataAccessException ex){
            return false;
        }
    }

    public void updateEnabled(String developerToken) {
        try {
            String SQL =
                    "UPDATE developers " +
                    "SET enabled='true'" +
                    "WHERE developertoken=?;";
            jdbcTemplateObject.queryForList(SQL,developerToken);
        } catch (DataIntegrityViolationException ignored){

        }
    }

    public void updateDateOfLastUsed(String developerToken, Long date) {
        try {
            String SQL =
                    "UPDATE developers " +
                    "SET dateoflastused=?" +
                    "WHERE developertoken=?;";
            jdbcTemplateObject.queryForList(SQL,date,developerToken);
        } catch (DataIntegrityViolationException ignored){

        }
    }

    public Developer getDeveloper(String developerData, Constants type) {
        String SQL;
        switch (type) {
            case developerEmail:
                SQL =
                    "SELECT developers.developerId, developers.developertoken, developers.email, developers.password, " +
                            "developers.name, developers.enabled, user_roles.role, developers.dateofrecordcreated, " +
                            "developers.dateoflastused " +
                            "FROM developers LEFT JOIN user_roles ON developers.email=user_roles.email " +
                            "WHERE developers.email=?;";
                break;
            case developerToken:
                SQL =
                    "SELECT developers.developerId, developers.developertoken, developers.email, developers.password, " +
                            "developers.name, developers.enabled, user_roles.role, developers.dateofrecordcreated, " +
                            "developers.dateoflastused " +
                            "FROM developers LEFT JOIN user_roles ON developers.email=user_roles.email " +
                            "WHERE developers.developertoken=?;";
                break;
            default:
                throw new IllegalAccessError("Bad data. Developer mistake. stacktrace: "
                        + Arrays.toString(new Error().getStackTrace()));
        }
        try {
            return jdbcTemplateObject.queryForObject(SQL, new DeveloperRowMapper(), developerData);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public List<Developer> getAll() {
        String SQL =
                "SELECT developers.developerId, developers.developertoken, " +
                        "developers.email, developers.password, " +
                        "developers.name, developers.enabled, user_roles.role " +
                "FROM developers LEFT JOIN user_roles ON developers.email=user_roles.email; ";
        try {
            return jdbcTemplateObject.query(SQL, new DeveloperRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void setPassword(String developerToken, String newPassword) {
        try {
            String SQL =
                    "UPDATE developers " +
                    "SET password=?" +
                    "WHERE developertoken=?;";
            jdbcTemplateObject.queryForList(SQL,newPassword,developerToken);
        } catch (DataIntegrityViolationException ignored){

        }
    }

    public void setEmail(String developerToken, String newEmail) {
        String currentEmail = getDeveloper(developerToken,Constants.developerToken).getEmail();
        try {
            jdbcTemplateObject.update(
                    "UPDATE developers SET email=? WHERE email=?;",newEmail,currentEmail);

            jdbcTemplateObject.update(
                    "UPDATE user_roles SET email=? WHERE email=?;",newEmail,currentEmail);
        } catch (DataIntegrityViolationException ignored){

        }
    }

    public Boolean delete(String email) {
        try {
            jdbcTemplateObject.update("DELETE FROM developers WHERE email=?;",email);
            jdbcTemplateObject.update("DELETE FROM user_roles WHERE email=?;",email);
            return true;
        }
        catch (EmptyResultDataAccessException e){
            return false;
        }
    }

    private static class DeveloperRowMapper implements RowMapper<Developer> {
        public Developer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Developer(
                    rs.getLong("developerId"),
                    rs.getString("developerToken"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getBoolean("enabled"),
                    rs.getString("role"),
                    rs.getLong("dateofrecordcreated"),
                    rs.getLong("dateoflastused")
            );
        }
    }
}
