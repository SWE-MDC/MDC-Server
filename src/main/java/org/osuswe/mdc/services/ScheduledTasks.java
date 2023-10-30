package org.osuswe.mdc.services;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.osuswe.mdc.model.VerificationCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private final long codeTimeout = 30 * 60 * 1000;
    public static ConcurrentMap<String, VerificationCode> verificationCodes = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void invalidateCodes() {
        List<String> expired = new ArrayList<>();
        for (String username : verificationCodes.keySet()) {
            VerificationCode code = verificationCodes.get(username);
            if (System.currentTimeMillis() - code.getTimestamp() > codeTimeout) {
                expired.add(username);
                log.info("Code expired " + code.getCode(), dateFormat.format(new Date()));
            }
        }
        for (String username : expired) {
            verificationCodes.remove(username);
        }
    }
}