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

import com.github.fedorchuck.developersecurity.domainmodels.Application;
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
import java.util.List;

@Transactional
@Repository("applicationsJDBCTemplate")
public class ApplicationsJDBCTemplate {

    private Logger log = Logger.getLogger(ApplicationsJDBCTemplate.class);
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public void save(Application application) {
        try {
            String SQL =
                    "INSERT INTO applications (" +
                            "developerId, developerToken, " +
                            "applicationToken, shortName, " +
                            "response, message, documentationUrl, " +
                            "dateofrecordcreated, dateoflastusedbyapi) values (?,?,?,?,?,?,?,?,?);";
            jdbcTemplateObject.queryForList(
                    SQL,
                    application.getDeveloperId(),
                    application.getDeveloperToken(),
                    application.getApplicationToken(),
                    application.getShortName(),
                    application.getResponse(),
                    application.getMessage(),
                    application.getDocumentationUrl(),
                    application.getDateOfRecordCreated(),
                    application.getDateOfLastUsedByApi()
            );
            log.log(Level.DEBUG, "added record: "+application.toString());
        } catch (DataIntegrityViolationException ignored){

        }
    }

    /**
     * @return true if token exist.
     * */
    public boolean tokenExist(String token){
        String SQL = "SELECT developertoken FROM applications WHERE applicationtoken=?";
        try {
            return 0 < jdbcTemplateObject.queryForObject(SQL,String.class,token).length();
        } catch (EmptyResultDataAccessException ex){
            return false;
        }
    }

    public List<Application> getApplications(String developerToken) {
        try {
            return jdbcTemplateObject.query(
                    "SELECT * FROM applications WHERE developertoken=?;", new ApplicationRowMapper(), developerToken);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public List<Application> getAll() {
        try {
            return jdbcTemplateObject.query("SELECT * FROM applications;", new ApplicationRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void updateApplication(String shortName, String response, String message, Long date, String applicationToken){
        try {
            String SQL =
                    "UPDATE applications " +
                            "SET shortName=?,response=?,message=?,dateofrecordcreated=? " +
                            "WHERE applicationToken=?;";
            jdbcTemplateObject.queryForList(SQL,shortName,response,message,date,applicationToken);
        } catch (DataIntegrityViolationException ignored){

        }
    }

    public void updateDateOfLastUsedByApi(String applicationToken, Long date) {
        try {
            String SQL =
                    "UPDATE applications " +
                            "SET dateoflastusedbyapi=? " +
                            "WHERE applicationToken=?;";
            jdbcTemplateObject.update(SQL,date,applicationToken);
        } catch (DataIntegrityViolationException ignored){

        }
    }

    public Boolean deleteApplications(String developerToken) {
        try {
            return 0<jdbcTemplateObject.update("DELETE FROM applications WHERE developerToken=?;",developerToken);
        }
        catch (EmptyResultDataAccessException e){
            return false;
        }
    }

    public Boolean deleteApplication(String applicationToken) {
        try {
            return 0<jdbcTemplateObject.update("DELETE FROM applications WHERE applicationtoken=?;",applicationToken);
        }
        catch (EmptyResultDataAccessException e){
            return false;
        }
    }

    private static class ApplicationRowMapper implements RowMapper<Application> {
        public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Application(
                    rs.getLong("developerId"),
                    rs.getString("developerToken"),
                    rs.getLong("applicationId"),
                    rs.getString("applicationToken"),
                    rs.getString("shortName"),
                    rs.getString("response"),
                    rs.getString("message"),
                    rs.getString("documentationUrl"),
                    rs.getLong("dateOfRecordCreated"),
                    rs.getLong("dateOfLastUsedByApi")
            );
        }
    }
}
