package model;


public class ContaCliente {

    private String numConta;
    private double saldoConta = 0;

    public ContaCliente(String numConta, double saldoConta) {
        this.numConta = numConta;
        this.saldoConta = saldoConta;
    }

    public String getNumConta() {
        return numConta;
    }

    public void setNumConta(String numConta) {
        this.numConta = numConta;
    }

    public double getSaldoConta() {
        return saldoConta;
    }

    public void setSaldoConta(double saldoConta) {
        this.saldoConta = saldoConta;
    }

    public boolean add(ContaCliente contas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
