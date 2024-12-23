package AbtractClass;

import java.util.List;

public abstract  class WHMA <EntityType,KeyType> {
    public abstract void insert(EntityType Entity);
    public abstract void update(EntityType Entity);
    public abstract void delete(KeyType id);
    public abstract List<EntityType> selectAll();
    public abstract EntityType selectbyID(KeyType id);
    public abstract List<EntityType> selectbyID(String sql, Object...agrs);
}
