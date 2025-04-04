/*
 * Implementação do Servidor de caixa.
  */

import java.rmi.RemoteException;
import java.rmi.registry.*;
  
public class ServerServidor extends IServidor{
      private MapProducts mapProducts = new MapProducts();

    //   Remote Object
    IEstoque stub;

     public ServerServidor() {
        String HOST = "localhost";

        try {
            Registry registry = LocateRegistry.getRegistry(HOST, 6602);
            stub = (IEstoque) registry.lookup("Estoque");
        }catch(Exception ex) {
            ex.printStackTrace();
        }
     }

     public Boolean inicializar_venda(String cliente) throws RemoteException {
        mapProducts.newList(cliente);
     }

     public double registrar_produto(String cliente, String id) throws RemoteException {
        mapProducts.add(cliente, stub.consultar_produto(id));

        return mapProducts.getTotalValueList(cliente);
     }

     public double consultar_valor_total(String cliente) throws RemoteException {

        return mapProducts.getTotalValueList(cliente);
     }

     public Boolean pagar(String cliente, double valor) throws RemoteException {
        double totalValue = consultar_valor_total(cliente);

        if(totalValue == valor){
            mapProducts.removeList(cliente);
            stub.relatorio_produtos();
            return true;
        }

        return false;
     }

      public static void main(String[] args) {
        String HOST = "localhost";

        try {
            Registry registry = LocateRegistry.getRegistry(HOST, 6602);
            IEstoque stub = (IEstoque) registry.lookup("Estoque");

            String res = stub.relatorio_produtos();
            System.out.println("Mensagem do Servidor:\n" + res);

        }catch(Exception ex) {
            ex.printStackTrace();
        }
      }
}