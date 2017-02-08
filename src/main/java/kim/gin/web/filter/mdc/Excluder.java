package kim.gin.web.filter.mdc;

/**
 *
 *
 * @since 2017-02-08
 */
public interface Excluder {

    Excluder DUMMY = new Excluder() {
        @Override
        public boolean decide(String key) {
            return false;
        }
    };

    /**
     *
     *
     * @param key
     * @return
     */
    boolean decide(String key);
}
