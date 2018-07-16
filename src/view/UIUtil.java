package view;

import util.Console;

public class UIUtil {

    public static final String SIM = "sim";
    public static final String NAO = "nao";
    
    public static void mostrarErro(Object msgErro) {
        System.err.println(msgErro);
    }
    
    public static boolean getConfirmacao(Object msg) {
        String confirmacao = "nao";
        do {
            confirmacao = Console.scanString(msg+"(Informe SIM ou NAO): ");
            if (confirmacao.equalsIgnoreCase(SIM)) {
                return true;
            }
            else if(confirmacao.equalsIgnoreCase(NAO)){
                return false;
            }
            else{
                System.out.println("A opção informada é inválida!");
            }                
        }while(confirmacao.equalsIgnoreCase(SIM) || 
                confirmacao.equalsIgnoreCase(NAO));
        return false;
    } 
    
}
