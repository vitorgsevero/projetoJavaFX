package negocio;

import dao.BD.ClienteDaoBD;
import dao.ClienteDAO;
import java.util.List;
import model.Clientes;


public class ClienteNegocio {
    
    private ClienteDAO clienteDao;

    public ClienteNegocio() {
        clienteDao = new ClienteDaoBD();
    }

    public void salvar(Clientes cliente) throws NegocioException {
        this.validarCamposObrigatorios(cliente);
        this.validarCpfExistente(cliente);
        clienteDao.cadastrarClientes(cliente);
    }

    public List<Clientes> listar() {
        return (clienteDao.listar());
    }

    public void deletar(Clientes cliente) throws NegocioException {
        if (cliente == null || cliente.getCpfCliente() == null) {
            throw new NegocioException("Cliente não existe!");
        }
        clienteDao.removerClientes(cliente);
    }

    public void atualizar(Clientes cliente) throws NegocioException {
        if (cliente == null || cliente.getCpfCliente() == null) {
            throw new NegocioException("Cliente não existe!");
        }
        this.validarCamposObrigatorios(cliente);
        clienteDao.atualizarDados(cliente);
    }

    public Clientes procurarPorCpf(String cpfCliente) throws NegocioException {
        if (cpfCliente == null || cpfCliente.isEmpty()) {
            throw new NegocioException("Campo CPF não informado");
        }
        Clientes cliente = clienteDao.procurarPorCpf(cpfCliente);
        if (cliente == null) {
            throw new NegocioException("Cliente não encontrado");
        }
        return (cliente);
    }

//    public List<Clientes> procurarPorNome(String nome) throws NegocioException {
//        if (nome == null || nome.isEmpty()) {
//            throw new NegocioException("Campo nome nao informado");
//        }
//        return(clienteDao.procurarPorNome(nome));
//    }

    public boolean clienteExiste(String cpfCliente) {
        Clientes cliente = clienteDao.procurarPorCpf(cpfCliente);
        return (cliente != null);
    }

    private void validarCamposObrigatorios(Clientes cliente) throws NegocioException {
        if (cliente.getCpfCliente() == null || cliente.getCpfCliente().isEmpty()) {
            throw new NegocioException("Campo CPF não informado");
        }

        if (cliente.getNomeCliente() == null || cliente.getNomeCliente().isEmpty()) {
            throw new NegocioException("Campo nome não informado");
        }
    }

    private void validarCpfExistente(Clientes cliente) throws NegocioException {
        if (clienteExiste(cliente.getCpfCliente())) {
            throw new NegocioException("CPF já cadastrado!");
        }
    }
}
