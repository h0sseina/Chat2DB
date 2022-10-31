package com.alibaba.dataops.server.test.domain.data.service.dialect;

import java.util.Date;

import com.alibaba.dataops.server.domain.data.api.enums.DbTypeEnum;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Component;

/**
 * h2
 *
 * @author Jiaju Zhuang
 */
@Component
public class H2DialectProperties implements DialectProperties {

    @Override
    public DbTypeEnum getDbType() {
        return DbTypeEnum.H2;
    }

    @Override
    public String getUrl() {
        return "jdbc:h2:mem:data-ops-test;MODE=MYSQL";
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getDatabaseName() {
        return "PUBLIC";
    }

    @Override
    public String getCrateTableSql(String tableName) {
        String sql = "CREATE TABLE `" + tableName + "`\n"
            + "(\n"
            + "    `ID`     bigint PRIMARY KEY AUTO_INCREMENT NOT NULL COMMENT '主键自增',\n"
            + "    `DATE`   datetime                          not null COMMENT '日期',\n"
            + "    `NUMBER` bigint COMMENT '长整型',\n"
            + "    `STRING` VARCHAR(100) default 'DATA' COMMENT '名字'"
            + ");\n";
        sql += "comment on table " + tableName + " is '测试表';\n";
        sql += "create index " + tableName + "_IDX_DATE   on " + tableName + "(DATE desc);\n";
        sql += "comment on index " + tableName + "_IDX_DATE is '日期索引';\n";
        sql += "create index " + tableName + "_UK_NUMBER   on " + tableName + "(NUMBER);\n";
        sql += "comment on index " + tableName + "_UK_NUMBER is '唯一索引';\n";
        sql += "create index " + tableName + "_IDX_NUMBER_STRING   on " + tableName + "(NUMBER, DATE);\n";
        sql += "comment on index " + tableName + "_IDX_NUMBER_STRING is '联合索引';\n";
        return sql;
    }

    @Override
    public String getDropTableSql(String tableName) {
        return "drop table " + tableName + ";";
    }

    @Override
    public String getInsertSql(String tableName, Date date, Long number, String string) {
        return "INSERT INTO `" + tableName + "` (DATE,NUMBER,STRING) VALUES ('" + DateUtil.format(date,
            DatePattern.NORM_DATETIME_MS_FORMAT) + "','" + number + "','" + string + "');";
    }

    @Override
    public String getSelectSqlById(String tableName, Long id) {
        return "select *\n"
            + "from " + tableName + "\n"
            + "where `ID` = '"+id+"';";
    }
}