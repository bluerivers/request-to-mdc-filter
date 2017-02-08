package kim.gin.web.filter.mdc.extractor;

import kim.gin.web.filter.mdc.Excluder;

/**
 * @since 2017-02-08
 */
public abstract class BaseHttpMapExtractor extends BaseHttpExtractor {

    private Excluder excluder = Excluder.DUMMY;

    /**
     *
     * @param excluder
     */
    public void setExcluder(Excluder excluder) {
        this.excluder = excluder;
    }

    public boolean excludes(String name) {
        return excluder.decide(name);
    }
}
