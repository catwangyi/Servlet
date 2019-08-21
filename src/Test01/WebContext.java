package Test01;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wang
 * @create 2019-08-11 18:49
 * @desc
 **/
public class WebContext {
    private List<Entity> entities = null;
    private List<Mapping> mappings = null;

    private Map<String,String> EntityMap = new HashMap<>();
    private Map<String,String> MappingMap = new HashMap<>();
    public WebContext(List<Entity> entities, List<Mapping> mappings) {
        this.entities = entities;
        this.mappings = mappings;
        for (Entity e:entities){
            EntityMap.put(e.getName(),e.getClz());
        }
        for (Mapping mapping:mappings){
            for (String pattern:mapping.getPatterns()){
                MappingMap.put(pattern,mapping.getName());
            }
        }
    }

    /**
     * 通过url路径找到对应的class
     * @param pattern
     * @return
     */
    public String getClz(String pattern){
        return EntityMap.get(MappingMap.get(pattern));
    }
}
