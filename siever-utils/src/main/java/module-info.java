module siever.utils {
    requires java.sql;
    requires org.apache.commons.configuration2;
    requires druid;
    requires java.naming;
    requires org.jetbrains.annotations;
    requires commons.dbutils;
    requires org.slf4j;
    requires logback.classic;
    requires logback.core;
    exports com.huanhe_tech.siever.utils;
}