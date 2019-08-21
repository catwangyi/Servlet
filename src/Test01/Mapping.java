package Test01;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wang
 * @create 2019-08-11 18:18
 * @desc
 **/
public class Mapping {
    private String name;
    private Set<String> patterns;

    public Mapping() {
        patterns = new HashSet<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPatterns() {
        return patterns;
    }

    public void setPatterns(Set<String> patterns) {
        this.patterns = patterns;
    }


    /**
     * 添加URL的方法
     * @param pattern
     */
    public void addPattern(String pattern){
        this.patterns.add(pattern);
    }
}
