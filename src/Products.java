import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Products {
    private double totalValue;
    private List<Product> products = new List<Product>();

    public Products() {
        totalValue = 0;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void add(Product p) throws Exception{
        if(p.getValor() > 0) {
            int index = products.indexOf(p);
            if(index != -1){
                Product p_aux = products.get(index);
                p_aux.setQtd(p.getQtd() + 1);
            }else{
                products.add(p);
            }
    
            totalValue += p.getValor();
        }

        throw new Exception("O produto não pode ter o valor negativo");

    }

}
