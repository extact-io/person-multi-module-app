package io.extact.sample.person.webapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class PersonApplicationMain {
    private static final Logger LOG = LoggerFactory.getLogger(PersonApplicationMain.class);
    public static void main(String[] args) {
        // java.util.loggingの出力をSLF4Jへdelegate
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        // Helidonの起動
        io.helidon.microprofile.cdi.Main.main(args);
        LOG.info("Person Application Started!");
    }
}
