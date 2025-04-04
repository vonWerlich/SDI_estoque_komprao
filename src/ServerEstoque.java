
/*
 * O servidor deve oferecer:
 * - Operações com a base de dados (implementando IDatabase)
  */

 
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class ServerEstoque  implements IEstoque{
    private final String PATH = "../database/estoque.csv";

    private EstoqueDatabase est;

    public ServerEstoque() {
        est = new EstoqueDatabase(PATH);
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
        try {
            ServerEstoque server = new ServerEstoque();
            IEstoque stub = (IEstoque) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.createRegistry(6600);

            registry.bind("Estoque", stub);

            System.out.println("Servidor pronto");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
