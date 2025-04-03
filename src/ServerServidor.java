/*
 * Implementação do Servidor de caixa.
  */

  import java.rmi.Naming;
  import java.rmi.RemoteException;
  import java.rmi.server.UnicastRemoteObject;
  import java.util.HashMap;
  import java.util.Map;
  
  public class ServerServidor extends UnicastRemoteObject implements IServidor {
      private Map<String, Double> compras;
      private IEstoque estoque;
      
      public ServerServidor() throws RemoteException {
          super();
          compras = new HashMap<>();
          try {
              estoque = (IEstoque) Naming.lookup("rmi://localhost/Estoque");
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
      
      @Override
      public Boolean inicializar_venda(String cliente) throws RemoteException {
          compras.put(cliente, 0.0);
          return true;
      }
      
      @Override
      public double registrar_produto(String id) throws RemoteException {
          double preco = 5.0; // Simulação de preços
          compras.put(id, compras.getOrDefault(id, 0.0) + preco);
          return compras.get(id);
      }
      
      @Override
      public double consultar_valor_total(String cliente) throws RemoteException {
          return compras.getOrDefault(cliente, 0.0);
      }
      
      @Override
      public Boolean pagar(String cliente, double valor) throws RemoteException {
          if (compras.containsKey(cliente) && compras.get(cliente) == valor) {
              compras.remove(cliente);
              System.out.println("Pagamento realizado por " + cliente);
              return true;
          }
          return false;
      }
      
      public static void main(String[] args) {
          try {
              ServerServidor servidor = new ServerServidor();
              Naming.rebind("rmi://localhost/Servidor", servidor);
              System.out.println("Servidor de Vendas pronto!");
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
  }