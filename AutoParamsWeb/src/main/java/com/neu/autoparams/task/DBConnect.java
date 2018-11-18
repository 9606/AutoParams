package com.neu.autoparams.task;

import com.neu.autoparams.util.ConfigurationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DBConnect {
    private static final Logger logger = LoggerFactory.getLogger(DBConnect.class);

    private static final String driver = ConfigurationUtil.INSTANCE.getDBDriver();
    private static final String url = ConfigurationUtil.INSTANCE.getDBUrl();
    private static final String user = ConfigurationUtil.INSTANCE.getDBUser();
    private static final String pass = ConfigurationUtil.INSTANCE.getDBPass();

    private static JdbcTemplate jdbcTemplate = null;

    public static JdbcTemplate getJdbcTemplate(){
        if (jdbcTemplate == null){
            try {
                DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
                driverManagerDataSource.setDriverClassName(driver);;
                driverManagerDataSource.setUrl(url);
                driverManagerDataSource.setUsername(user);
                driverManagerDataSource.setPassword(pass);
                jdbcTemplate = new JdbcTemplate(driverManagerDataSource);
            }catch (Exception e){
                logger.error(e.getMessage());
                return null;
            }
        }
        return jdbcTemplate;
    }
}
