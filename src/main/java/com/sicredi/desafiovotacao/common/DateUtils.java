package com.sicredi.desafiovotacao.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    public static LocalDateTime currentDate() {
        return LocalDateTime.now(ZoneId.of("UTC"));
    }
}
