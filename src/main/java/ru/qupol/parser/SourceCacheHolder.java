package ru.qupol.parser;

import org.slf4j.Logger;
import ru.qupol.model.Price;
import ru.qupol.utils.SlfLogger;

import java.util.Date;
import java.util.List;

public class SourceCacheHolder {

    private static final Logger LOGGER = SlfLogger.getLogger();

    private SourceParcer sourceParcer;

    private Date loadDate;

    private List<Price> loadedData;


    //30min
    private long lifetime = 30 * 60 * 1000;

    public SourceCacheHolder(SourceParcer sourceParcer) {
        this.sourceParcer = sourceParcer;
    }

    public SourceCacheHolder(SourceParcer sourceParcer, long lifetime) {
        this.sourceParcer = sourceParcer;
        this.lifetime = lifetime;
    }

    public List<Price> loadData(Boolean force) {
        if (loadDate == null || loadedData == null
                || force
                || (new Date()).after(new Date(loadDate.getTime() + lifetime))) {
            LOGGER.info("loading new data: " + sourceParcer.getClass().getName());
            loadDate = new Date();
            loadedData = sourceParcer.parse();
            return loadedData;
        }

        LOGGER.info("returns existed data: " + sourceParcer.getClass().getName());
        return loadedData;
    }


}
