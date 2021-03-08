package com.jsql.view.swing.console;

import static org.apache.logging.log4j.core.layout.PatternLayout.createDefaultLayout;

import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.stream.Stream;

import javax.swing.SwingUtilities;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.spi.StandardLevel;

import com.jsql.model.exception.IgnoreMessageException;
import com.jsql.view.swing.util.UiUtil;

/**
 * Log4j2
 * LOGGER.info(e)
 * => No query string
 * LOGGER.info(e.getMessage())
 * => com.jsql.model.exception.InjectionFailureException: No query string
 * LOGGER.info(e, e)
 * => com.jsql.model.exception.InjectionFailureException: No query string + Stacktrace
 */
@Plugin(
    name = "JTextPaneAppender",
    category = Core.CATEGORY_NAME,
    elementType = Appender.ELEMENT_TYPE,
    printObject = true
)
public class JTextPaneAppender extends AbstractAppender {
    
    // Main console
    private static SimpleConsoleAdapter consoleTextPane;
    
    // Java console
    private static JavaConsoleAdapter javaTextPane;

    public static final SimpleAttributeSet ERROR = new SimpleAttributeSet();
    public static final SimpleAttributeSet WARN = new SimpleAttributeSet();
    public static final SimpleAttributeSet INFO = new SimpleAttributeSet();
    public static final SimpleAttributeSet DEBUG = new SimpleAttributeSet();
    public static final SimpleAttributeSet TRACE = new SimpleAttributeSet();
    public static final SimpleAttributeSet ALL = new SimpleAttributeSet();
    
    static {
        Stream
        .of(
            new AbstractMap.SimpleEntry<>(ERROR, Color.RED),
            new AbstractMap.SimpleEntry<>(WARN, Color.RED),
            new AbstractMap.SimpleEntry<>(INFO, Color.BLUE),
            new AbstractMap.SimpleEntry<>(DEBUG, UiUtil.COLOR_GREEN),
            new AbstractMap.SimpleEntry<>(TRACE, Color.BLACK),
            new AbstractMap.SimpleEntry<>(ALL, Color.BLACK)
        )
        .forEach(entry -> {
            
            StyleConstants.setFontFamily(entry.getKey(), UiUtil.FONT_NAME_MONO_NON_ASIAN);
            StyleConstants.setFontSize(entry.getKey(), UiUtil.FONT_SIZE_MONO_NON_ASIAN);
            StyleConstants.setForeground(entry.getKey(), entry.getValue());
        });
    }
    
    private JTextPaneAppender(String name, Layout<?> layout, Filter filter, boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions, Property.EMPTY_ARRAY);
    }

    @SuppressWarnings("unused")
    @PluginFactory
    public static JTextPaneAppender createAppender(
        @PluginAttribute("name") String name,
        @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
        @PluginElement("Layout") Layout<?> layout,
        @PluginElement("Filters") Filter filter
    ) {
        if (name == null) {
            
            LOGGER.error("No name provided for JTextAreaAppender");
            return null;
        }

        if (layout == null) {
            
            layout = createDefaultLayout();
        }
        
        return new JTextPaneAppender(name, layout, filter, ignoreExceptions);
    }

    @Override
    public void append(LogEvent event) {
        
        // Avoid errors which might occur in headless mode
        // or logging that occurs before consoles are available
        if (consoleTextPane == null || javaTextPane == null) {
            
            return;
        }
        
        String[] m = new String[] { "" };
        try {
            m[0] = new String(this.getLayout().toByteArray(event), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            javaTextPane.append(e.toString(), WARN);
        }
        Throwable throwableInformation = event.getThrown();
        int level = event.getLevel().intLevel();
        
        SwingUtilities.invokeLater(() -> {
            
            String message = m[0];
            
            if (level == StandardLevel.TRACE.intLevel()) {
                
                if (throwableInformation == null || !(throwableInformation instanceof IgnoreMessageException)) {

                    consoleTextPane.append(message, TRACE);
                }
                
            } else if (level == StandardLevel.ERROR.intLevel()) {
                
                javaTextPane.append(message, WARN);
                javaTextPane.getProxy().setCaretPosition(javaTextPane.getProxy().getDocument().getLength());

                if (throwableInformation != null && throwableInformation.getStackTrace() != null) {

                    for (StackTraceElement rep : throwableInformation.getStackTrace()) {

                        javaTextPane.append(rep.toString(), ERROR);
                    }
                }
                
            } else if (level == StandardLevel.WARN.intLevel()) {
                
                consoleTextPane.append(message, WARN);
                
            } else if (level == StandardLevel.INFO.intLevel()) {
                
                consoleTextPane.append(message, INFO);
                
            } else if (level == StandardLevel.DEBUG.intLevel()) {
                
                consoleTextPane.append(message, DEBUG);
                
            } else if (level == StandardLevel.FATAL.intLevel()) {
                
                // Ignore exception
                
            } else {
                
                consoleTextPane.append(message, ALL);
            }
        });
    }
    
    /**
     * Register the java console.
     * @param javaConsole
     */
    public static void register(JavaConsoleAdapter javaConsole) {
        JTextPaneAppender.javaTextPane = javaConsole;
    }

    /**
     * Register the default console.
     * @param consoleColored
     */
    public static void register(SimpleConsoleAdapter consoleColored) {
        JTextPaneAppender.consoleTextPane = consoleColored;
    }
}