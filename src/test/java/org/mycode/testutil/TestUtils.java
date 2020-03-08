package org.mycode.testutil;

import org.apache.log4j.Logger;
import org.mycode.config.TestConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class TestUtils {
    private static final Logger log = Logger.getLogger(TestUtils.class);
    private static final ApplicationContext APPLICATION_CONTEXT;
    private static final String LINK_TO_PROPERTIES = "./src/main/resources/config.properties";
    private static final String WORK_PROPERTIES_TEXT;
    private static final String TEST_PROPERTIES_TEXT = "# JDBC\n" +
            "jdbc.driver=org.h2.Driver\n" +
            "jdbc.url=jdbc:h2:~/test\n" +
            "jdbc.user=sa\n" +
            "jdbc.password=";

    static {
        APPLICATION_CONTEXT = new AnnotationConfigApplicationContext(TestConfig.class);
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader fr = new FileReader(LINK_TO_PROPERTIES)) {
            int c;
            while ((c = fr.read()) != -1) {
                stringBuilder.append((char) c);
            }
        } catch (IOException e) {
            log.error("No properties file", e);
        }
        WORK_PROPERTIES_TEXT = stringBuilder.toString();
    }

    private TestUtils() {
    }

    public static void switchConfigToTestMode() {
        try (FileWriter fw = new FileWriter(LINK_TO_PROPERTIES, false)) {
            fw.write(TEST_PROPERTIES_TEXT);
            fw.flush();
        } catch (IOException e) {
            log.error("No properties file", e);
        }
    }

    public static void switchConfigToWorkMode() {
        try (FileWriter fw = new FileWriter(LINK_TO_PROPERTIES, false)) {
            fw.write(WORK_PROPERTIES_TEXT);
            fw.flush();
        } catch (IOException e) {
            log.error("No properties file", e);
        }
    }

    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }
}
