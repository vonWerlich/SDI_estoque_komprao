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

     public Boolean inicializar_venda(String cliente) throws RemoteException {
         if(mapProducts.part_key_exists(cliente)){
             throw new RemoteException();
         }

        if (!mapProducts.contains_key(cliente)) {
            return mapProducts.new_list(cliente);
        }

        return true;
    }

     public double registrar_produto(String cliente, String id) throws RemoteException {
        try {
            if (mapProducts.contains_key(cliente)) {
                mapProducts.add(cliente, stub.consultar_produto(id));
                return mapProducts.get_total_value_list(cliente);
            }
            
            throw new RemoteException();

        
        }catch(Exception ex) {
             throw new RemoteException();
        }
     }

     public double consultar_valor_total(String cliente) throws RemoteException {
        if (mapProducts.contains_key(cliente)) {
            return mapProducts.get_total_value_list(cliente);
        }

        throw new RemoteException("Não é possível consultar uma venda não cadastrada");
     }

     public Boolean pagar(String cliente, double valor) throws RemoteException {
        double totalValue = consultar_valor_total(cliente);

        if(totalValue == valor){
            mapProducts.remove_list(cliente);
            System.out.println(stub.relatorio_produtos());
            return true;
        }

        return false;
     }

      public static void main(String[] args) {
          int PORT = 6601;
          int PORT_SERVER_ESTOQUE  = 6600;
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