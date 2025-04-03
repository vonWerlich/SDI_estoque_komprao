
/*
 * O servidor deve oferecer:
 * - Operações com a base de dados (implementando IDatabase)
  */

  import java.rmi.Naming;
  import java.rmi.RemoteException;
  import java.rmi.server.UnicastRemoteObject;
  import java.util.HashMap;
  import java.util.Map;
  
  // Implementação do controle de estoque
  public class ServerEstoque extends UnicastRemoteObject implements IEstoque {
      private Map<String, Integer> estoque;
      
      public ServerEstoque() throws RemoteException {
          super();
          estoque = new HashMap<>();
          estoque.put("produto1", 10);
          estoque.put("produto2", 20);
          estoque.put("produto3", 15);
      }
      
      @Override
      public synchronized Integer remover_produto(String id) throws RemoteException {
          if (estoque.containsKey(id) && estoque.get(id) > 0) {
              estoque.put(id, estoque.get(id) - 1);
              return estoque.get(id);
          }
          return -1; // Indica que o produto está esgotado
      }
      
      @Override
      public String relatorio_produtos() throws RemoteException {
          StringBuilder relatorio = new StringBuilder("Relatório de Estoque:\n");
          for (Map.Entry<String, Integer> entry : estoque.entrySet()) {
              relatorio.append(entry.getKey()).append(": ").append(entry.getValue()).append(" unidades\n");
          }
          return relatorio.toString();
      }
      
      public static void main(String[] args) {
          try {
              ServerEstoque estoque = new ServerEstoque();
              Naming.rebind("rmi://localhost/Estoque", estoque);
              System.out.println("Servidor de Estoque pronto!");
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
  }
