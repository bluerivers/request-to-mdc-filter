package kim.gin.web.filter.mdc.excluder;

import kim.gin.web.filter.mdc.Excluder;

import java.util.HashSet;
import java.util.Set;

/**
 * @since 2017-02-08
 */
public class SimpleWordsExcluder implements Excluder {

    private Set<String> wordsToExclude;

    public SimpleWordsExcluder() {
        wordsToExclude = new HashSet<>();
    }

    public SimpleWordsExcluder(String... words) {
        this();
        setWordsToExclude(words);
    }

    public void setWordsToExclude(String... words) {
        for (String word : words) {
            wordsToExclude.add(word.trim().toLowerCase());
        }
    }

    @Override
    public boolean decide(String key) {
        return wordsToExclude.contains(key);
    }
}
