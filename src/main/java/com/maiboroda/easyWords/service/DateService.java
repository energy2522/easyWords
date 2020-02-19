package com.maiboroda.easyWords.service;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DateService {
    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";
    private DateTimeFormatter dateTimeFormat;

    public DateService() {
        this.dateTimeFormat = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    }

    public LocalDateTime parseDateTime(String dateTime) {
        if (dateTime == null) {
            return null;
        }
        LocalDateTime result;
        try {
            result = (LocalDateTime) dateTimeFormat.parse(dateTime);
        } catch (DateTimeParseException ex) {
            log.error("Can't parse dateTime {}. Error is {}", dateTime, ex.getLocalizedMessage());
            result = null;
        }

        return result;
    }

    public String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        String result;
        try {
            result = dateTimeFormat.format(dateTime);
        } catch (DateTimeException ex) {
            log.error("Can't format dateTime. Error is {}", ex.getLocalizedMessage());
            result = null;
        }

        return result;
    }
}
