package dao.BD;

import java.util.List;
import model.Clientes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Clientes;
import dao.ClienteDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class ClienteDaoBD implements ClienteDAO {

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
    public void cadastrarClientes(Clientes cliente) {

        int id = 0;

        try {
            String sql = "INSERT INTO cliente (nome, cpf, email, saldoconta, numconta, datanascimento) "
                    + "VALUES (?,?,?,?,?,?)";

            //Foi criado um novo método conectar para obter o id
            conectarObtendoId(sql);

            comando.setString(1, cliente.getNomeCliente());
            comando.setString(2, cliente.getCpfCliente());
            comando.setString(3, cliente.getEmailCliente());
            comando.setDouble(4, cliente.getSaldoConta());
            comando.setString(5, cliente.getNumConta());

            //Trabalhando com data: convertendo LocalDate -> Date
            Date dataNascimento = Date.valueOf(cliente.getDataNascimento());
            comando.setDate(6, dataNascimento);

            comando.executeUpdate();

            //Obtém o resultSet para pegar o id
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                //seta o id para o objeto
                id = resultado.getInt(1);
                cliente.setId(id);
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
    public void removerClientes(Clientes cliente) {
        try {

            String sql = "DELETE FROM cliente WHERE cpf = ?";

            this.conectar(sql);
            
            this.comando.setString(1, cliente.getCpfCliente());
            this.comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro: Problema ao deletar paciente no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            encerrarConexao();
        }

    }

    @Override
    public void atualizarDados(Clientes cliente) {
        try {
            String sql = "UPDATE cliente SET cpf=?, nome=?, email=?, datanascimento=? "
                    + "WHERE cpf=?";

            conectar(sql);
            comando.setString(1, cliente.getCpfCliente());
            comando.setString(2, cliente.getNomeCliente());
            comando.setString(3, cliente.getEmailCliente());
            //Trabalhando com data: convertendo LocalDate -> Date
            Date dataSql = Date.valueOf(cliente.getDataNascimento());
            comando.setDate(4, dataSql);
            comando.setString(5, cliente.getCpfCliente());
            
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro: Problema ao atualizar cliente no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            encerrarConexao();
        }
    }

    @Override
    public List<Clientes> listar() {
        List<Clientes> listaClientes = new ArrayList<>();

        String sql = "SELECT * FROM cliente";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nomeCliente = resultado.getString("nome");
                String cpfCliente = resultado.getString("cpf");
                //Trabalhando com data: convertendo dataSql -> LocalDate
                Date dataSql = resultado.getDate("datanascimento");
                LocalDate dataNascimento = dataSql.toLocalDate();

                Clientes clientes = new Clientes(id, nomeCliente, cpfCliente, dataNascimento);

                listaClientes.add(clientes);

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os clientes do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            encerrarConexao();
        }

        return (listaClientes);
    }

    @Override
    public Clientes procurarPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Clientes procurarPorCpf(String cpfCliente) {

        String sql = "SELECT * FROM cliente WHERE cpf = ?";

        try {

            conectar(sql);
            comando.setString(1, cpfCliente);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                int id = resultado.getInt("id");
                String nomeCliente = resultado.getString("nome");
                //Trabalhando com data: convertendo dataSql -> LocalDate
                Date dataSql = resultado.getDate("datanascimento");
                LocalDate dataNascimento = dataSql.toLocalDate();

                Clientes clientes = new Clientes(nomeCliente, cpfCliente, dataNascimento);
                // new Clientes(nome, cpf, email, numConta,  DateUtil.stringToDate(dataString)

                return clientes;

            }

        } catch (SQLException ex) {
            System.err.println("Erro: Problema ao buscar o clientes pelo CPF do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            encerrarConexao();
        }

        return (null);
    }

    @Override
    public List<Clientes> listarPorNome(String nomeCliente) {
        
        List<Clientes> listaClientes = new ArrayList<>();
        
        String sql = "SELECT * FROM cliente WHERE UPPER(nome) LIKE UPPER(?)";

        try {
            conectar(sql);
            comando.setString(1, "%" + nomeCliente + "%");
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                String cpfCliente = resultado.getString("cpf");
            
                //Trabalhando com data: convertendo dataSql -> LocalDate
                Date dataSql = resultado.getDate("datanascimento");
                LocalDate dataNascimento = dataSql.toLocalDate();

                Clientes clientes = new Clientes(id, nome, cpfCliente, dataNascimento);

                listaClientes.add(clientes);

            }

        } catch (SQLException ex) {
            System.err.println("Erro: Problema ao buscar os clientes pelo nome do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            encerrarConexao();
        }
        return (listaClientes);
   
    }

}
