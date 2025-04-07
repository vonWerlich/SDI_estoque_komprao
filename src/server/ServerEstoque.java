package server;

/*
 * O servidor deve oferecer:
 * - Operações com a base de dados (implementando IDatabase)
  */

 
import java.rmi.*;
import java.rmi.server.*;

import utils.EstoqueDatabase;
import utils.Product;

import java.rmi.registry.*;

public class ServerEstoque  implements IEstoque{
    private final String PATH = "database/estoque.csv";

    private EstoqueDatabase est;

    public ServerEstoque() {
        est = new EstoqueDatabase(PATH);
    }

    public Product consultar_produto(String id) throws RemoteException {
        Product p = est.getItem(Integer.valueOf(id));
        p.setQtd(1);

        return p;
    }

    public Integer remover_produto(String id) throws RemoteException, NullPointerException {
        if (est.removeItem(Integer.valueOf(id))) {
            return est.qtdItem(Integer.valueOf(id));
        } 

        return -1;
    }

    public String relatorio_produtos() throws RemoteException {
        return est.toString();
    }

    public static void main(String[] args) {
        int port = 6604;
        try {
            ServerEstoque server = new ServerEstoque();
            IEstoque stub = (IEstoque) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.createRegistry(port);

            registry.bind("Estoque", stub);

            System.out.println("Servidor de Estoque está Pronto");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
