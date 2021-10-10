package com.danko.provider.tag;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The custom Date tag.
 */
public class DateTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();
    private static final String DATE_FORMAT_PATTERN = "dd-MM-yyyy HH:mm";
    private static final String LOCAL_DATE_FORMAT_PATTERN = "dd-MM-yyyy";
    private static final DateTimeFormatter dateTimeFormatterShort = DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT_PATTERN);
    private static final DateTimeFormatter dateTimeFormatterFull = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
    private LocalDateTime localDateTime;
    private boolean isFullFormat;

    /**
     * Sets local date time.
     *
     * @param localDateTime the local date time
     */
    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    /**
     * Sets full format.
     *
     * @param fullFormat the full format
     */
    public void setFullFormat(String fullFormat) {
        isFullFormat = Boolean.parseBoolean(fullFormat);
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            String data = "";
            if (!isFullFormat) {
                data = localDateTime.toLocalDate().format(dateTimeFormatterShort);
            } else {
                data = localDateTime.format(dateTimeFormatterFull);
            }
            pageContext.getOut().write(data);
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error while writing to stream");
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}