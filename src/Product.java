public class Product {
    private int id;
    private String descricao;
    private double value;
    private int qtd;

    public Product(int id, String descricao, double value, int qtd) {
        this.id = id;
        this.descricao = descricao;
        this.value = value;
        this.qtd = qtd;
    }
    
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getvalue() {
        return value;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public Boolean removeItem(){
        if (this.qtd > 0){
            this.qtd -= 1;
            return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.id == this.id && obj.descricao == this.descricao && obj.value == this.value;
    }

    @Override
    public String toString() {
        return (descricao + "," + value + "," + qtd + "\n");
    }
}