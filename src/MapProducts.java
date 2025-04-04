import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapProducts {
    private Map<String, Products> mapProducts = new HashMap<String, Products>();

    public MapProducts() {}

    public double getTotalValueList(String id) throws NullPointerException {
        if (mapProducts.containsKey(id)) {
            return getList(id).getTotalValue();
        }

        throw new NullPointerException("Id inexistente");
    }

    public void newList(String id) throws Exception{
        if (mapProducts.containsKey(id)) {
            throw new Exception("Id já existente");
        }

        mapProducts.put(id, new Products());
    }

    public Products getList(String id) throws NullPointerException {
        if (mapProducts.containsKey(id)) {
            return mapProducts.get(id);
        }

        throw new NullPointerException("Id inexistente");
    }

    public void removeList(String id) throws NullPointerException {
        if (mapProducts.containsKey(id)) {
            mapProducts.remove(id);
        }

        throw new NullPointerException("Id inexistente");
    }

    public void add(String id, Product item) throws NullPointerException, Exception {
        if (mapProducts.containsKey(id)) {
            Products p = mapProducts.get(id);
            
            p.add(item);
        }

        throw new NullPointerException("Id inexistente");
    }




}
