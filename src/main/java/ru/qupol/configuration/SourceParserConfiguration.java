package ru.qupol.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.qupol.parser.*;
import ru.qupol.parser.actual.FunpayRuEuActualSourceParser;
import ru.qupol.parser.actual.PwlvlRuActualSourceParser;

@Configuration
public class SourceParserConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceParserConfiguration.class);

    static {
        LOGGER.info("SourceParserConfiguration is loaded");
    }

    @Bean
    public PlayerAuctionsSourceParser getPlayerAuctionsSourceParser() {
        return new PlayerAuctionsSourceParser();
    }

    @Bean
    @Primary
    public PwlvlEuSourceParcer getPwlvlEuSourceParcer() {
        return new PwlvlEuSourceParcer();
    }

    @Bean
    public PwlvlRuSourceParser getPwlvlRuSourceParser() {
        return new PwlvlRuSourceParser();
    }

    @Bean
    @Primary
    public FunpayEuSourceParcer getFunpayEuSourceParcer() {
        return new FunpayEuSourceParcer();
    }

    @Bean
    public FunpayRuSourceParser getFunpayRuSourceParser() {
        return new FunpayRuSourceParser();
    }


    //actual
    @Bean
    public FunpayRuEuActualSourceParser getFunpayRuEuActualSourceParser(){
        return new FunpayRuEuActualSourceParser();
    }

    @Bean
    public PwlvlRuActualSourceParser getPwlvlRuActualSourceParser(){
        return new PwlvlRuActualSourceParser();
    }

}
