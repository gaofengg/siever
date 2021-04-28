module siever.cli{
    requires TwsApi;
    requires java.sql;
    requires siever.utils;
    requires org.apache.commons.configuration2;
    requires java.naming;
    requires druid;
    requires commons.dbutils;
    requires org.jetbrains.annotations;
    exports com.huanhe_tech.cli;
    exports com.huanhe_tech.cli.connection;
    exports com.huanhe_tech.cli.queue;
    exports com.huanhe_tech.cli.reqAndHandler;
    exports com.huanhe_tech.cli.strategies;
    exports com.huanhe_tech.cli.beans;
}