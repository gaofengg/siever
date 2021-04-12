module siever.cli{
    requires TwsApi;
    requires java.sql;
    requires siever.utils;
    requires org.apache.commons.configuration2;
    exports com.huanhe_tech.cli;
    exports com.huanhe_tech.cli.connection;
    exports com.huanhe_tech.cli.queue;
    exports com.huanhe_tech.cli.req;
}