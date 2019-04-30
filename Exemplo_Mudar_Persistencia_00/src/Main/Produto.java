package Main;// @author Radames
public class Produto {private int id;private String nome;private double preco;private int quantidade;
public Produto(){

}public Produto(int id,String nome,double preco,int quantidade){this.id = id;this.nome = nome;this.preco = preco;this.quantidade = quantidade;}

public int getId(){return id;
}
public String getNome(){return nome;
}
public double getPreco(){return preco;
}
public int getQuantidade(){return quantidade;
}
public void setId(int id){this.id = id;
}
public void setNome(String nome){this.nome = nome;
}
public void setPreco(double preco){this.preco = preco;
}
public void setQuantidade(int quantidade){this.quantidade = quantidade;
}    public String toStringCSV() {return this.id+";"+this.nome+";"+this.preco+";"+this.quantidade;}}