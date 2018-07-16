package util;

import javax.swing.JOptionPane;


public class PrintUtil {
    
    public static void printMessageError(String msg) {
            JOptionPane.showMessageDialog(null, 
                    msg,
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);        
    }

    public static void printMessageSucesso(String msg) {
            JOptionPane.showMessageDialog(null, 
                    msg,
                    "Sucesso",
                    JOptionPane.PLAIN_MESSAGE);        
    }       
}
