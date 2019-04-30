package Main;
// @author Radames

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ControleDaListaProduto {

    private List<Produto> lista = new ArrayList<>();
//esse comparator será usado para ordenação e busca binária
    private Comparator<Produto> comparator = new Comparator<Produto>() {
        @Override
        public int compare(Produto c1, Produto c2) {
            return Integer.valueOf(c1.getId()).compareTo(Integer.valueOf(c2.getId()));
        }
    };

    public List<Produto> getLista() {
        return lista;
    }

    public void inserir(Produto elemento) {
        lista.add(elemento);
        Collections.sort(lista, comparator);//após incluir, ordena a lista por ID
    }

    public Produto buscarComPesquisaBinaria(Produto elemento) {
        //tem que ordenar antes de pesquisar
        //o método chamado de pequisa binária ou busca binária é mais eficiente que pesquisa sequencial. Mas, para funcionar a lista tem que estar ordenada.
        //ordenaPorId(lista); //essa linha é obrigatória se nao ordenar ao inserir - (ordena por id)

        int indice = Collections.binarySearch(lista, elemento, comparator);
        if (indice >= 0) {
            return lista.get(indice);
        } else {
            return null;
        }
    }

    public void excluir(Produto elemento) {
        lista.remove(elemento);
    }

    public void alterar(Produto elementoOriginal, Produto elementoAlterado) {
        //usa o original para localizar na lista e substitui pelo alterado
        lista.set(lista.indexOf(elementoOriginal), elementoAlterado);
    }

    public List<Produto> abrirArquivo(String caminho) {

        File arq = new File(caminho);
        if (arq.exists()) {
            try {
                //OpenFile
                FileReader arquivo = new FileReader(caminho);
                BufferedReader conteudoDoArquivo = new BufferedReader(arquivo);
                String linha = conteudoDoArquivo.readLine();
                String aux[];
                Produto elemento;
                while (linha != null) {
                    aux = linha.split(";");
                    elemento = new Produto(Integer.valueOf(aux[0]), aux[1], Double.valueOf(aux[2]), Integer.valueOf(aux[3]));
                    lista.add(elemento);
                    linha = conteudoDoArquivo.readLine();
                }
                conteudoDoArquivo.close();
            } catch (Exception e) {//Catch exception if any
                System.err.println("Error: " + e.getMessage());
            }
        }
        return lista;
    }

    public int salvarArquivo(String caminho) {
        try {
            // Create file 
            FileWriter arquivo = new FileWriter(caminho);
            BufferedWriter conteudoDoArquivo = new BufferedWriter(arquivo);
            for (int i = 0; i < lista.size(); i++) {
                conteudoDoArquivo.write(lista.get(i).toStringCSV() + System.getProperty("line.separator"));//+ System.getProperty("line.separator")); // 
            }
            conteudoDoArquivo.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
            return 1; //houve erro
        }
        return 0;
    }
}
