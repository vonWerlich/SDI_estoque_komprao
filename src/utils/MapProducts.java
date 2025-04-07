package utils;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class MapProducts {
    private Map<String, Products> mapProducts = new HashMap<String, Products>();

    public MapProducts() {}

    public boolean containsKey(String id) {
        return mapProducts.containsKey(id);
    }

    public double getTotalValueList(String id) throws NullPointerException {
        if (mapProducts.containsKey(id)) {
            return getList(id).getTotalValue();
        }

        throw new NullPointerException("Id inexistente");
    }

    public boolean newList(String id) {
        if (mapProducts.containsKey(id)) {
            return false;
        }

        mapProducts.put(id, new Products());
        return true;
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
        }else{
            throw new NullPointerException("Id: " + id + " inexistente");
        }
    }

    public void add(String id, Product item) throws NullPointerException, Exception {
        if (mapProducts.containsKey(id)) {
            Products p = mapProducts.get(id);            
            p.add(item);
        }else{
            throw new NullPointerException("Id: " + id + " inexistente");
        }
    }

    @Override
    public String toString() {
        String res = "";

        Iterator<Map.Entry<String, Products>> iterator =  mapProducts.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Products> pair = iterator.next();
            res += "cpf: " + pair.getKey() + "\nProdutos:\n" + pair.getValue() + "\n";
        }

        return res;
    }
}
