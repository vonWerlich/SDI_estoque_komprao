package server;

/*
 * O servidor deve oferecer:
 * - Operações com a base de dados (implementando IDatabase)
  */

 
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ClientFornecedor;
import utils.EstoqueDatabase;
import utils.Product;

import java.rmi.registry.*;

public class ServerEstoque  implements IEstoque{
    private final String PATH = "database/estoque.csv";
    private Map<String, ClientFornecedor> fornecedores = new HashMap<String,ClientFornecedor>();

    private EstoqueDatabase est;

    public ServerEstoque(Map<String, String> fornecedores) throws Exception {
        est = new EstoqueDatabase(PATH);

        for (Map.Entry<String, String> f : fornecedores.entrySet()){
            String key = f.getKey();
            String url = f.getValue();
            ClientFornecedor forn = new ClientFornecedor(url);

            this.fornecedores.put(key, forn);
        }
    }

    public Product consultar_produto(String id) throws RemoteException {
        Product p = est.getItem(Integer.valueOf(id));
        return p;
    }

    public Integer remover_produto(String id) throws RemoteException, NullPointerException {
        if (est.removeItem(Integer.valueOf(id))) {
            return est.qtdItem(Integer.valueOf(id));
        } 

        return -1;
    }

    public boolean comprar_produtos_fornecedor(String fornecedor, List<Product> products) {
        if(fornecedores.containsKey(fornecedor)){
            List<Integer> list_products_id = new ArrayList<Integer>();
            for(Product p : products) {
                list_products_id.add(p.getId());
            }

            double value = fornecedores.get(fornecedor).comprar_produtos(list_products_id);

            return fornecedores.get(fornecedor).pagar_produtos(value);
        }

        return false;
    }

    public String relatorio_produtos() throws RemoteException {
        return est.toString();
    }

    public static void main(String[] args) {
        Map<String, String> fornecedores = new HashMap<String, String>();
        fornecedores.put("f1", "http://127.0.0.1:9876/WSFornecedor?wsdl");

        List<Product> products = new ArrayList<Product>();

        products.add(new Product(1000, null, 0, 0));
        products.add(new Product(1001, null, 0, 0));
        products.add(new Product(1002, null, 0, 0));

        int PORT= 6600;
        try {
            ServerEstoque server = new ServerEstoque(fornecedores);
            IEstoque stub = (IEstoque) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.createRegistry(PORT);

            registry.bind("Estoque", stub);

            System.out.println("Servidor de Estoque está Pronto");

            System.out.println(server.comprar_produtos_fornecedor("f1", products));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
