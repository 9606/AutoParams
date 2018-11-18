package com.neu.autoparams.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum ConfigurationUtil {
    INSTANCE;

    private Properties properties = null;
    private Properties dbProperties = null;

    ConfigurationUtil(){
        InputStream stream = this.getClass().getResourceAsStream("/configuration.properties");
        InputStream dbStream = this.getClass().getResourceAsStream("/database.properties");
        properties = new Properties();
        dbProperties = new Properties();
        try {
            properties.load(stream);
            dbProperties.load(dbStream);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getFilePath(){
        return properties.getProperty("filePath");
    }

    public String getAlgoParamsPath(){
        return properties.getProperty("algoParamsPath");
    }

    public String getExcelPath(){
        return properties.getProperty("excelPath");
    }

    public String getDBDriver(){
        return dbProperties.getProperty("db.driver");
    }

    public String getDBUrl(){
        return dbProperties.getProperty("db.url");
    }

    public String getDBUser(){
        return dbProperties.getProperty("db.user");
    }

    public String getDBPass(){
        return dbProperties.getProperty("db.pass");
    }

    public String getNewsPath(){
        return properties.getProperty("newsPath");
    }
}
