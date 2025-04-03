/*
    Caixa é o programa que irá consumir as operações do Servidor.
    Caixa não pode falar diretamente com Estoque.
 */

 import java.rmi.Naming;

 public class Caixa {
     public static void main(String[] args) {
         try {
             IServidor servidor = (IServidor) Naming.lookup("rmi://localhost/Servidor");
             
             String cliente = "cliente1";
             servidor.inicializar_venda(cliente);
             
             System.out.println("Adicionando produtos...");
             servidor.registrar_produto("produto1");
             servidor.registrar_produto("produto2");
             
             double total = servidor.consultar_valor_total(cliente);
             System.out.println("Total a pagar: " + total);
             
             boolean pago = servidor.pagar(cliente, total);
             if (pago) {
                 System.out.println("Pagamento realizado com sucesso!");
             } else {
                 System.out.println("Erro no pagamento!");
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
 }
 