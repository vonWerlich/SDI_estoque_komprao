/*
 * Implementação do Servidor de caixa.
  */

import java.rmi.registry.*;
  
public class ServerServidor {
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