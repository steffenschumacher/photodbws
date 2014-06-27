package photodb.log;



import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Steffen Schumacher (steff@tdc.dk)
 * @version 1.00
 */
public class LogContextEventListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(LogContextEventListener.class.getName());
    private static FileHandler logFile;

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        String contextPath = contextEvent.getServletContext().getContextPath().replaceAll("/", "");
        LOG.log(Level.SEVERE, "Logging for context {0} moved to separate file"
                + " of same name in logs", contextPath);
        Level prodLvl = Level.INFO;
        Level testLvl = Level.FINEST;
        Level lvl = testLvl;
        try {
            String tomcatHome = System.getProperty("catalina.home");
            String logsPath = tomcatHome + "/logs/" + contextPath;
            logFile = new FileHandler(logsPath, 1000000, 100, true);
            logFile.setLevel(lvl);
            logFile.setFormatter(new LogFormatter());
            LogManager.getLogManager().reset();
            Logger root = LogManager.getLogManager().getLogger("");
            
            root.addHandler(logFile);
            root.setLevel(Level.INFO);
            Logger.getLogger("photodb").setLevel(testLvl);
            
        } catch (Exception e) {
            LOG.log(Level.SEVERE, 
                    "Unable to log to apache tomcat log dir for " + contextPath, 
                    e);
        }
        
        LOG.log(Level.INFO, "Log level set to {0}", Logger.getGlobal().getLevel());

        
    }
    
    public static FileHandler getFileHandler() {
        return logFile;
    }

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        final String LogMethodName = "contextDestroyed";

        LOG.log(Level.INFO, LogMethodName, "Received context destroy event.");

        // Clean up database ressources.
        logFile.close();
    }
}
