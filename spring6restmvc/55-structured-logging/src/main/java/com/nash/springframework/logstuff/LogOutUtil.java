package com.nash.springframework.logstuff;



import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


//@Slf4j
@Component
public class LogOutUtil implements CommandLineRunner {

    private static final Logger log=org.slf4j.LoggerFactory.getLogger(LogOutUtil.class);

    @Override
    public void run(String... args) throws Exception {
        log.trace("Trace Log");
        log.debug("Debugging Log");
        log.info("Info Log");
        log.warn("Hey, this is a warning");
        log.error("Oops! We have an error ");

    }
}
