package org.lpw.ranch.chrome;

import org.lpw.ranch.logger.LoggerService;
import org.lpw.tephra.ctrl.validate.ValidateWrapper;
import org.lpw.tephra.ctrl.validate.ValidatorSupport;
import org.lpw.tephra.util.Converter;
import org.lpw.tephra.util.TimeUnit;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

/**
 * @author lpw
 */
@Controller(ChromeService.VALIDATOR_MEMORY)
public class MemoryValidatorImpl extends ValidatorSupport {
    @Inject
    private Converter converter;
    @Inject
    private LoggerService loggerService;
    private long lastLogTime = 0L;

    @Override
    public boolean validate(ValidateWrapper validate, String parameter) {
        long max = Runtime.getRuntime().maxMemory();
        long free = Runtime.getRuntime().freeMemory();
        if (free >= max >> 2)
            return true;

        if (System.currentTimeMillis() - lastLogTime > 10 * TimeUnit.Minute.getTime()) {
            loggerService.create(getDefaultFailureMessageKey(), converter.toBitSize(max), converter.toBitSize(free));
            lastLogTime = System.currentTimeMillis();
        }

        return false;
    }

    @Override
    protected String getDefaultFailureMessageKey() {
        return ChromeModel.NAME + ".memory.not-enough";
    }
}