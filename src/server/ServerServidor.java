package server;
/*
 * Implementação do Servidor de caixa.
  */

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

import utils.MapProducts;


public class ServerServidor implements IServidor{
    private MapProducts mapProducts = new MapProducts();

    //   Remote Object
    private IEstoque stub;

     public ServerServidor(int port)  throws RemoteException, NotBoundException{
        String HOST = "localhost";

        Registry registry = LocateRegistry.getRegistry(HOST, port);
        stub = (IEstoque) registry.lookup("Estoque");
     }

     public void printMapProducts() {
        System.out.println(mapProducts);
     }

     public Boolean inicializar_venda(String cliente) throws RemoteException {
        if (!mapProducts.containsKey(cliente)) {
            return mapProducts.newList(cliente);
        }

        throw new RemoteException("Não é possível consultar uma venda não cadastrada");
     }

     public double registrar_produto(String cliente, String id) throws RemoteException {
        try {
            if (mapProducts.containsKey(cliente)) {
                mapProducts.add(cliente, stub.consultar_produto(id));
                System.out.println(stub.relatorio_produtos());
                return mapProducts.getTotalValueList(cliente);
            }
            
            throw new RemoteException();

        
        }catch(Exception ex) {
             throw new RemoteException();
        }
     }

     public double consultar_valor_total(String cliente) throws RemoteException {
        if (mapProducts.containsKey(cliente)) {
            return mapProducts.getTotalValueList(cliente);
        }

        throw new RemoteException("Não é possível consultar uma venda não cadastrada");
     }

     public Boolean pagar(String cliente, double valor) throws RemoteException {
        double totalValue = consultar_valor_total(cliente);

        if(totalValue == valor){
            mapProducts.removeList(cliente);
            System.out.println(stub.relatorio_produtos());
            return true;
        }

        return false;
     }

      public static void main(String[] args) {
          int PORT = 6605;
          int PORT_SERVER_ESTOQUE  = 6604;
          try {
              ServerServidor server = new ServerServidor(PORT_SERVER_ESTOQUE);
              IServidor stub = (IServidor) UnicastRemoteObject.exportObject(server, 0);
              Registry registry = LocateRegistry.createRegistry(PORT);

              registry.bind("Servidor", stub);

              System.out.println("Servidor de Caixa está Pronto");

            }catch (Exception ex) {
                ex.printStackTrace();
            }
      }
}