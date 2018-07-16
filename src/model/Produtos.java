package model;


public class Produtos {

    private String codProduto;
    private String nomeProduto;
    private double precoProduto;
    private int idProduto;

    public Produtos(String codProduto, String nomeProduto, String precoProduto) {
        this.codProduto = codProduto;
        this.nomeProduto = nomeProduto;
        this.precoProduto = Double.parseDouble(precoProduto);
    }

    public String getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(String codProduto) {
        this.codProduto = codProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(String precoProduto) {
        this.precoProduto = Double.parseDouble(precoProduto);
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }



}
