package negocio;

import dao.BD.ProdutoDaoBD;
import dao.ProdutoDAO;
import java.util.List;
import model.Produtos;


public class ProdutoNegocio {
    
    private ProdutoDAO produtoDao;

    public ProdutoNegocio() {
        produtoDao = new ProdutoDaoBD();
    }

    public void salvar(Produtos produto) throws NegocioException {
        this.validarCamposObrigatorios(produto);
        this.validarCodProdutoExistente(produto);
       produtoDao.cadastrarProdutos(produto);
    }

    public List<Produtos> listar() {
        return (produtoDao.listar());
    }

    public void deletar(Produtos produto) throws NegocioException {
        if (produto == null || produto.getCodProduto() == null) {
            throw new NegocioException("Produto não existe!");
        }
        produtoDao.removerProdutos(produto);
    }

    public void atualizar(Produtos produtoSelecionado) throws NegocioException {
        if (produtoSelecionado == null || produtoSelecionado.getCodProduto() == null) {
            throw new NegocioException("Produto não existe!");
        }
        this.validarCamposObrigatorios(produtoSelecionado);
        produtoDao.atualizarDados(produtoSelecionado);
    }

    public Produtos procurarPorCodigo(String codigoProduto) throws NegocioException {
        if (codigoProduto == null || codigoProduto.isEmpty()) {
            throw new NegocioException("Campo Código de Produto não informado");
        }
        Produtos produto = produtoDao.procurarPorCodProduto(codigoProduto);
        if (produto == null) {
            throw new NegocioException("Produto não encontrado!");
        }
        return (produto);
    }

    public List<Produtos> procurarPorNome(String nomeProduto) throws NegocioException {
        if (nomeProduto == null || nomeProduto.isEmpty()) {
            throw new NegocioException("Campo nome nao informado!");
        }
        return produtoDao.listarPorNome(nomeProduto);
    }

    public boolean produtoExiste(String codProduto) {
        Produtos produto = produtoDao.procurarPorCodProduto(codProduto);
        return (produto != null);
    }

    private void validarCamposObrigatorios(Produtos produto) throws NegocioException {
        if (produto.getCodProduto() == null || produto.getCodProduto().isEmpty()) {
            throw new NegocioException("Campo Código de produto não informado");
        }

        if (produto.getNomeProduto() == null || produto.getNomeProduto().isEmpty()) {
            throw new NegocioException("Campo nome do produto não informado");
        }
    }

    private void validarCodProdutoExistente(Produtos produto) throws NegocioException {
        if (produtoExiste(produto.getCodProduto())) {
            throw new NegocioException("Código de produto já cadastrado!");
        }
    }
}

