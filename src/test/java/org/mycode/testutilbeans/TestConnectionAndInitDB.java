package org.mycode.testutilbeans;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConnectionAndInitDB {
    private static final Logger LOG = Logger.getLogger(TestConnectionAndInitDB.class);
    private static final String LINK_TO_INIT_SCRIPT = "./src/test/resources/db/initDB.sql";
    private static final String LINK_TO_POP_SCRIPT = "./src/test/resources/db/populateDB.sql";
    private Connection connection;

    public TestConnectionAndInitDB(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOG.error("No connection to DB", e);
        }
    }

    public void reInitDB() {
        try (FileReader frInit = new FileReader(LINK_TO_INIT_SCRIPT);
             FileReader frPop = new FileReader(LINK_TO_POP_SCRIPT)) {
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.runScript(frInit);
            scriptRunner.runScript(frPop);
        } catch (IOException e) {
            LOG.error("No script files", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
