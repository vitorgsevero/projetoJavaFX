package dao;

import model.Produtos;
import java.util.List;

public interface ProdutoDAO {
    
    public void cadastrarProdutos(Produtos produto);
    public void removerProdutos(Produtos produto);
    public void atualizarDados(Produtos produtoSelecionado);
    public List<Produtos> listar();
    public Produtos procurarPorId(int id);
    //Adicionado
    public Produtos procurarPorCodProduto(String codProduto);
    public List<Produtos> listarPorNome(String nomeProduto);
    public List<Produtos> listarPorMaiorPreco();
}
