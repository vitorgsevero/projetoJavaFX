package model;


import java.time.LocalDateTime;
import util.DateTimeUtil;

public class OperacaoMonetizacao {

    private String statusCliente;
    private LocalDateTime dataHoraOperacao;
    
    public OperacaoMonetizacao(String statusCliente, LocalDateTime dataHoraOperacao){
        this.statusCliente = statusCliente;
        this.dataHoraOperacao = dataHoraOperacao;
    }



    public String getStatusCliente() {
        return statusCliente;
    }


    public LocalDateTime getDataHoraOperacao() {
        return dataHoraOperacao;
    }


}
