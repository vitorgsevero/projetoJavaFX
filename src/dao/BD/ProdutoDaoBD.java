package dao.BD;

import dao.ProdutoDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Clientes;
import model.Produtos;

public class ProdutoDaoBD implements ProdutoDAO {

    private Connection conexao;
    private PreparedStatement comando;

    public Connection conectar(String sql) throws SQLException {
        conexao = ConnectionFactory.getConnection();
        comando = conexao.prepareStatement(sql);
        return conexao;
    }

    public void conectarObtendoId(String sql) throws SQLException {
        conexao = ConnectionFactory.getConnection();
        comando = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
    }

    public void encerrarConexao() {
        try {
            if (comando != null) {
                comando.close();
            }
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao encerrar a conexao");
            throw new BDException(ex);

        }
    }

    @Override
    public void cadastrarProdutos(Produtos produto) {

        int idProduto = 1;

        try {
            String sql = "INSERT INTO produto (cod_produto, nome_produto, preco_produto) "
                    + "VALUES (?,?,?)";

            //método conectar para obter o id
            conectarObtendoId(sql);

            comando.setString(1, produto.getCodProduto());
            comando.setString(2, produto.getNomeProduto());
            comando.setDouble(3, produto.getPrecoProduto());

            comando.executeUpdate();

            //Obtém o resultSet para pegar o id
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                //seta o id para o objeto
                idProduto = resultado.getInt(1);
                produto.setIdProduto(idProduto);
            } else {
                System.err.println("Erro: Não foi possível gerar o ID!");
                throw new BDException("O ID não foi gerado!");
            }

        } catch (SQLException ex) {
            System.err.println("Erro: Problema ao salvar paciente no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            encerrarConexao();
        }

    }

    @Override
    public void removerProdutos(Produtos produto) {
        try {

            String sql = "DELETE FROM produto WHERE cod_produto = ?";

            this.conectar(sql);

            this.comando.setString(1, produto.getCodProduto());
            this.comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro: Problema ao deletar paciente no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            encerrarConexao();
        }
    }

    @Override
    public void atualizarDados(Produtos produto) {
        try {
            String sql = "UPDATE produto SET nome_produto=?, preco_produto=? "
                    + "WHERE cod_produto=?";

            conectar(sql);

            comando.setString(1, produto.getNomeProduto());
            comando.setDouble(2, produto.getPrecoProduto());

            comando.setString(3, produto.getCodProduto());

            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro: Problema ao atualizar produto no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            encerrarConexao();
        }

    }

    @Override
    public List<Produtos> listar() {
        List<Produtos> listaProdutos = new ArrayList<>();

        String sql = "SELECT * FROM produto";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id_produto");
                String codigoProduto = resultado.getString("cod_produto");
                String nomeProduto = resultado.getString("nome_produto");
                String precoProduto = resultado.getString("preco_produto");

                Produtos produto = new Produtos(codigoProduto, nomeProduto, precoProduto);

                listaProdutos.add(produto);

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os produtos do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            encerrarConexao();
        }

        return (listaProdutos);
    }

    @Override
    public Produtos procurarPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Produtos procurarPorCodProduto(String codProduto) {
        String sql = "SELECT * FROM produto WHERE cod_produto = ?";

        try {

            conectar(sql);
            comando.setString(1, codProduto);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                int idProduto = resultado.getInt("id_produto");
                String codigoProduto = resultado.getString("cod_produto");
                String nomeProduto = resultado.getString("nome_produto");
                String precoProduto = resultado.getString("preco_produto");

                Produtos produto = new Produtos(codigoProduto, nomeProduto, precoProduto);

                return produto;

            }

        } catch (SQLException ex) {
            System.err.println("Erro: Problema ao buscar o produto pelo código do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            encerrarConexao();
        }

        return (null);
    }

    @Override
    public List<Produtos> listarPorNome(String nomeProduto) {
        
        List<Produtos> listaProdutos = new ArrayList<>();
        
        String sql = "SELECT * FROM produto WHERE UPPER(nome_produto) LIKE UPPER(?)";

        try {

            conectar(sql);
            comando.setString(1, "%" + nomeProduto + "%");
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id_produto");
                String codigoProduto = resultado.getString("cod_produto");
                String nome = resultado.getString("nome_produto");
                String precoProduto = resultado.getString("preco_produto");

                Produtos produto = new Produtos(codigoProduto, nome, precoProduto);

                listaProdutos.add(produto);

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os produtos do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            encerrarConexao();
        }

        return (listaProdutos);

    }

    @Override
    public List<Produtos> listarPorMaiorPreco() {
        List<Produtos> listaProdutos = new ArrayList<>();

        String sql = "SELECT * FROM produto ORDER BY preco_produto DESC";

        try {

            conectar(sql);
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id_produto");
                String codigoProduto = resultado.getString("cod_produto");
                String nomeProduto = resultado.getString("nome_produto");
                String precoProduto = resultado.getString("preco_produto");

                Produtos produto = new Produtos(codigoProduto, nomeProduto, precoProduto);

                listaProdutos.add(produto);

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os produtos do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            encerrarConexao();
        }

        return (listaProdutos);

    }

}
