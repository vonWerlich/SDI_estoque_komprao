public class Product {
    private int id;
    private String descricao;
    private float valor;
    private int qtd;

    public Product(int id, String descricao, float valor, int qtd) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.qtd = qtd;
    }
    
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public float getValor() {
        return valor;
    }

    public int getQtd() {
        return qtd;
    }

    public Boolean removeItem(){
        if (this.qtd > 0){
            this.qtd -= 1;
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return (descricao + "," + valor + "," + qtd + "\n");
    }
}