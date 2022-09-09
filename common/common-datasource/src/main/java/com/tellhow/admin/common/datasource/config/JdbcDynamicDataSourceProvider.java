package com.tellhow.admin.common.datasource.config;

import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.tellhow.admin.common.datasource.support.DataSourceConstants;
import org.jasypt.encryption.StringEncryptor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/7 10:36
 * @descripe 从数据源中获取 配置信息
 */
public class JdbcDynamicDataSourceProvider extends AbstractJdbcDataSourceProvider {

    private final DataSourceProperties properties;

    private final StringEncryptor stringEncryptor;

    public JdbcDynamicDataSourceProvider(StringEncryptor stringEncryptor, DataSourceProperties properties) {
        super(properties.getDriverClassName(), properties.getUrl(), properties.getUsername(), properties.getPassword());
        this.stringEncryptor = stringEncryptor;
        this.properties = properties;
    }

    @Override
    protected Map<String, DataSourceProperty> executeStmt(Statement statement) throws SQLException {

        ResultSet  resultSet = statement.executeQuery(properties.getQueryDsSql());
        Map<String ,DataSourceProperty> map = new HashMap<>(8);
        while (resultSet.next()){
            String name = resultSet.getString(DataSourceConstants.DS_NAME);
            String username = resultSet.getString(DataSourceConstants.DS_USER_NAME);
            String password = resultSet.getString(DataSourceConstants.DS_USER_PWD);
            String url = resultSet.getString(DataSourceConstants.DS_JDBC_URL);
            DataSourceProperty dataSourceProperty = new DataSourceProperty();
            dataSourceProperty.setUsername(username);
            dataSourceProperty.setPassword(password);
            dataSourceProperty.setUrl(url);
            dataSourceProperty.setLazy(true);
            map.put(name,dataSourceProperty);

        }
        // 添加默认主数据源
        DataSourceProperty property = new DataSourceProperty();
        property.setUsername(properties.getUsername());
        property.setPassword(properties.getPassword());
        property.setUrl(properties.getUrl());
        property.setLazy(true);
        map.put(DataSourceConstants.DS_MASTER, property);
        return map;
    }
}
