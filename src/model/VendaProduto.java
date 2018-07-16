package model;


public class VendaProduto {

    private String codVenda;
    private int qtdeProdutos = 0;
    private double totalVenda = 0;
    
    public VendaProduto(String codVenda, int qtdeProdutos, double totalVendas){
        this.codVenda = codVenda;
        this.qtdeProdutos = qtdeProdutos;
        this.totalVenda = totalVenda;
    }
    
    public double calcularTotalVenda() {
        return getTotalVenda();
    }

    public void finalizarVenda() {

    }

    public String getCodVenda() {
        return codVenda;
    }

    public int getQtdeProdutos() {
        return qtdeProdutos;
    }

    public double getTotalVenda() {
        return totalVenda;
    }

}
