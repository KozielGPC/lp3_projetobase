package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

public class GUIProduto extends JFrame {

    ImageIcon iconeCreate = new ImageIcon(getClass().getResource("/icones/create.png"));
    ImageIcon iconeRetrieve = new ImageIcon(getClass().getResource("/icones/retrieve.png"));
    ImageIcon iconeUpdate = new ImageIcon(getClass().getResource("/icones/update.png"));
    ImageIcon iconeDelete = new ImageIcon(getClass().getResource("/icones/delete.png"));
    ImageIcon iconeSave = new ImageIcon(getClass().getResource("/icones/save.png"));
    ImageIcon iconeCancel = new ImageIcon(getClass().getResource("/icones/cancel.png"));
    ImageIcon iconeListar = new ImageIcon(getClass().getResource("/icones/list.png"));
    JButton btnCreate = new JButton(iconeCreate);
    JButton btnRetrieve = new JButton(iconeRetrieve);
    JButton btnUpdate = new JButton(iconeUpdate);
    JButton btnDelete = new JButton(iconeDelete);
    JButton btnSave = new JButton(iconeSave);
    JButton btnCancel = new JButton(iconeCancel);
    JButton btnList = new JButton(iconeListar);
    JLabel labelId = new JLabel("Id");
    JTextField textFieldId = new JTextField(0);
    JLabel labelNome = new JLabel("Nome");
    JTextField textFieldNome = new JTextField(40);
    JLabel labelPreco = new JLabel("Preco");
    JTextField textFieldPreco = new JTextField(0);
    JLabel labelQuantidade = new JLabel("Quantidade");
    JTextField textFieldQuantidade = new JTextField(0);
    JPanel aviso = new JPanel();
    JLabel labelAviso = new JLabel("");
    String acao = "";//variavel para facilitar insert e update
    ControleDaListaProduto cl = new ControleDaListaProduto();
    Produto produto;
    Produto produtoOriginal;

    private void atvBotoes(boolean c, boolean r, boolean u, boolean d) {
        btnCreate.setEnabled(c);
        btnRetrieve.setEnabled(r);
        btnUpdate.setEnabled(u);
        btnDelete.setEnabled(d);
        btnList.setEnabled(r);
    }

    public void mostrarBotoes(boolean visivel) {
        btnCreate.setVisible(visivel);
        btnRetrieve.setVisible(visivel);
        btnUpdate.setVisible(visivel);
        btnDelete.setVisible(visivel);
        btnList.setVisible(visivel);
        btnSave.setVisible(!visivel);
        btnCancel.setVisible(!visivel);
    }

    private void habilitarAtributos(boolean id, boolean nome, boolean preco, boolean quantidade) {
        if (id) {
            textFieldId.requestFocus();
            textFieldId.selectAll();
        }
        textFieldId.setEnabled(id);
        textFieldId.setEditable(id);
        textFieldNome.setEditable(nome);
        textFieldPreco.setEditable(preco);
        textFieldQuantidade.setEditable(quantidade);
    }

    public void zerarAtributos() {
        textFieldNome.setText("");
        textFieldPreco.setText("");
        textFieldQuantidade.setText("");
    }

    public GUIProduto() {
        setTitle("Produto");
        try {
            File arq = new File("Produto.dat"); //tenta abrir o arquivo
            if (arq.exists()) { //se o arquivo já existe, abre e lê os dados
                cl.abrirArquivo("Produto.dat");
            }
        } catch (Exception e) {
            System.out.println("arquivo não encontrado");
        }
        setSize(600, 400);//tamanho da janela
        setLayout(new BorderLayout());//informa qual gerenciador de layout será usado
        setBackground(Color.CYAN);//cor do fundo da janela
        Container cp = getContentPane();//container principal, para adicionar nele os outros componentes

        atvBotoes(false, true, false, false);
        habilitarAtributos(true, false, false, false);
        btnCreate.setToolTipText("Inserir novo registro");
        btnRetrieve.setToolTipText("Pesquisar por chave");
        btnUpdate.setToolTipText("Alterar");
        btnDelete.setToolTipText("Excluir");
        btnList.setToolTipText("Listar todos");
        btnSave.setToolTipText("Salvar");
        btnCancel.setToolTipText("Cancelar");
        JToolBar Toolbar1 = new JToolBar();
        Toolbar1.add(labelId);
        Toolbar1.add(textFieldId);
        Toolbar1.add(btnRetrieve);
        Toolbar1.add(btnCreate);
        Toolbar1.add(btnUpdate);
        Toolbar1.add(btnDelete);
        Toolbar1.add(btnSave);
        Toolbar1.add(btnCancel);
        Toolbar1.add(btnList);
        btnSave.setVisible(false);
        btnCancel.setVisible(false);  //atributos
        JPanel centro = new JPanel();
        centro.setLayout(new GridLayout(5, 2));
        centro.add(labelId);
        centro.add(textFieldId);
        centro.add(labelNome);
        centro.add(textFieldNome);
        centro.add(labelPreco);
        centro.add(textFieldPreco);
        centro.add(labelQuantidade);
        centro.add(textFieldQuantidade);
        aviso.add(labelAviso);
        aviso.setBackground(Color.yellow);
        cp.add(Toolbar1, BorderLayout.NORTH);
        cp.add(centro, BorderLayout.CENTER);
        cp.add(aviso, BorderLayout.SOUTH);
        textFieldId.requestFocus();
        textFieldId.selectAll();
        textFieldId.setBackground(Color.GREEN);
        labelAviso.setText("Digite uma placa e clic [Pesquisar]");
        // setLocationRelativeTo(null); // posiciona no centro da tela principal
        setLocation(300, 200);
        setVisible(true);//faz a janela ficar visível  

// Listeners
        btnRetrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                produto = new Produto();
                textFieldId.setText(textFieldId.getText().trim());//caso tenham sido digitados espaços

                if (textFieldId.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Deve ser informado um valor para esse campo");
                    textFieldId.requestFocus();
                    textFieldId.selectAll();
                } else {
                    produto.setId(Integer.valueOf(textFieldId.getText()));
                    produto = cl.buscarComPesquisaBinaria(produto);
                    if (produto != null) { //se encontrou na lista
                        textFieldNome.setText(produto.getNome());
                        textFieldPreco.setText(
                                String.valueOf(produto.getPreco()));
                        textFieldQuantidade.setText(String.valueOf(produto.getQuantidade()));
                        atvBotoes(false, true, true, true);
                        habilitarAtributos(true, false, false, false);
                        labelAviso.setText("Encontrou - clic [Pesquisar], [Alterar] ou [Excluir]");
                        acao = "encontrou";
                        produtoOriginal = produto;
                    } else {
                        atvBotoes(true, true, false, false);
                        zerarAtributos();
                        labelAviso.setText("Não cadastrado - clic [Inserir] ou digite outra id [Pesquisar]");
                    }
                }
            }
        });

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                zerarAtributos();
                habilitarAtributos(false, true, true, true);
                textFieldNome.requestFocus();
                mostrarBotoes(false);
                labelAviso.setText("Preencha os campos e clic [Salvar] ou clic [Cancelar]");
                acao = "insert";
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (acao.equals("insert")) {
                    produto = new Produto();
                    produto.setId(Integer.valueOf(textFieldId.getText()));
                    produto.setNome(textFieldNome.getText());
                    produto.setPreco(Double.valueOf(textFieldPreco.getText()));
                    produto.setQuantidade(Integer.valueOf(textFieldQuantidade.getText()));
                    cl.inserir(produto);
                    habilitarAtributos(true, false, false, false);
                    mostrarBotoes(true);
                    atvBotoes(false, true, false, false);
                    labelAviso.setText("Registro inserido...");
                } else {  //acao = update
                    produto.setId(Integer.valueOf(textFieldId.getText()));
                    produto.setNome(textFieldNome.getText());
                    produto.setPreco(Double.valueOf(textFieldPreco.getText()));
                    produto.setQuantidade(Integer.valueOf(textFieldQuantidade.getText()));
                    cl.alterar(produtoOriginal, produto);
                    mostrarBotoes(true);
                    habilitarAtributos(true, false, false, false);
                    atvBotoes(false, true, false, false);
                    labelAviso.setText("Registro atualizado...");
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                zerarAtributos();
                atvBotoes(false, true, false, false);
                habilitarAtributos(true, false, false, false);
                mostrarBotoes(true);
            }
        });
        btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                acao = "list";
                GUIListagemProduto guiListagem = new GUIListagemProduto(cl.getLista());
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                acao = "update";
                mostrarBotoes(false);
                habilitarAtributos(false, true, true, true);
            }
        });
//---------------------------------------------------------
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
                        "Confirma a exclusão do registro <ID = " + produto.getId() + ">?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                    labelAviso.setText("Registro excluído...");
                    cl.excluir(produto);
                    zerarAtributos();
                    textFieldId.requestFocus();
                    textFieldId.selectAll();
                }
            }
        });
        textFieldId.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                textFieldId.setBackground(Color.GREEN);
                if (acao != "encontrou") {
                    labelAviso.setText("Digite uma Id e clic [Pesquisar]");
                }
            }

            @Override
            public void focusLost(FocusEvent fe) {
                textFieldId.setBackground(Color.white);
            }
        });
        textFieldId.addFocusListener(new FocusListener() { //ao receber o foco, fica verde
            @Override
            public void focusGained(FocusEvent fe) {
                textFieldId.setBackground(Color.GREEN);
            }

            @Override
            public void focusLost(FocusEvent fe) { //ao perder o foco, fica branco
                textFieldId.setBackground(Color.white);
            }
        });
        textFieldNome.addFocusListener(new FocusListener() { //ao receber o foco, fica verde
            @Override
            public void focusGained(FocusEvent fe) {
                textFieldNome.setBackground(Color.GREEN);
            }

            @Override
            public void focusLost(FocusEvent fe) { //ao perder o foco, fica branco
                textFieldNome.setBackground(Color.white);
            }
        });
        textFieldPreco.addFocusListener(new FocusListener() { //ao receber o foco, fica verde
            @Override
            public void focusGained(FocusEvent fe) {
                textFieldPreco.setBackground(Color.GREEN);
            }

            @Override
            public void focusLost(FocusEvent fe) { //ao perder o foco, fica branco
                textFieldPreco.setBackground(Color.white);
            }
        });
        textFieldQuantidade.addFocusListener(new FocusListener() { //ao receber o foco, fica verde
            @Override
            public void focusGained(FocusEvent fe) {
                textFieldQuantidade.setBackground(Color.GREEN);
            }

            @Override
            public void focusLost(FocusEvent fe) { //ao perder o foco, fica branco
                textFieldQuantidade.setBackground(Color.white);
            }
        });
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //antes de sair do sistema, grava os dados da lista em disco
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //antes de sair, salvar a lista em disco
                cl.salvarArquivo("Produto.dat");
                // Sai do sistema  
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new GUIProduto();
    }
}
