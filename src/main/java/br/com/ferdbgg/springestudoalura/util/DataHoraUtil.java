package br.com.ferdbgg.springestudoalura.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class DataHoraUtil {

    public static final ZoneId BRASILIA = ZoneId.of("America/Sao_Paulo");

    public static final ZoneOffset SAO_PAULO_OFFSET = ZoneOffset.ofHours(-3);

    private DataHoraUtil() {
    }

    public static LocalDate converterParaLocalDate(OffsetDateTime dateTime) {

        if (dateTime == null) {
            return null;
        }

        return dateTime.withOffsetSameInstant(SAO_PAULO_OFFSET).toLocalDate();

    }

    public static LocalDate converterParaLocalDate(Instant instant) {

        if (instant == null) {
            return null;
        }

        return instant.atZone(BRASILIA).toLocalDate();

    }

    public static LocalTime converterParaLocalTime(OffsetDateTime dateTime) {

        if (dateTime == null) {
            return null;
        }

        return dateTime.withOffsetSameInstant(SAO_PAULO_OFFSET).toLocalTime();

    }

    public static LocalTime converterParaLocalTime(Instant instant) {

        if (instant == null) {
            return null;
        }

        return instant.atZone(BRASILIA).toLocalTime();

    }

    public static OffsetDateTime converterParaOffsetDateTime(LocalDateTime dateTime) {

        if (dateTime == null) {
            return null;
        }

        return dateTime.toInstant(ZoneOffset.UTC).atZone(BRASILIA).toOffsetDateTime();

    }

    public static OffsetDateTime converterParaOffsetDateTime(LocalDate date, LocalTime time) {

        if (date == null || time == null) {
            return null;
        }

        return OffsetDateTime.of(date, time, SAO_PAULO_OFFSET);

    }

}
