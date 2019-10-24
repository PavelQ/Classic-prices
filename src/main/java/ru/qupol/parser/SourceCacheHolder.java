package ru.qupol.parser;

import ru.qupol.model.Price;

import java.util.Date;
import java.util.List;

public class SourceCacheHolder {

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
                || loadDate.after(new Date(loadDate.getTime() + lifetime))) {
            loadDate = new Date();
            loadedData = sourceParcer.parse();
            return loadedData;
        }

        return loadedData;
    }


}
