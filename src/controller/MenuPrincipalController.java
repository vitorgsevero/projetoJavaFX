package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MenuPrincipalController implements Initializable {

    @FXML
    private AnchorPane painelMenuPrincipal; 
    @FXML
    private MenuBar menuBarPrincipal;
    @FXML
    private Menu menuClientes;
    @FXML
    private Menu menuProdutos;
    @FXML
    private Menu menuSistema;
    @FXML
    private MenuItem menuItemClientes;
    @FXML
    private MenuItem menuItemProdutos;
    @FXML
    private MenuItem menuItemSobre;
    @FXML
    private MenuItem menuItemSair;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void tratarMenuClientes(ActionEvent evetn) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClienteController.class.getResource("/controller/PainelTabelaCliente.fxml"));
        VBox vbox = (VBox) loader.load();

        //CRIANDO UM ESTÁGIO DE DIÁLOGO - STAGE DIALOG
        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("MENU DE CLIENTES");
        Scene scene = new Scene(vbox);
        dialogStage.setScene(scene);

        dialogStage.showAndWait();
    }
   
    public void tratarMenuProdutos(ActionEvent evetn) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClienteController.class.getResource("/controller/PainelTabelaProduto.fxml"));
        VBox vbox = (VBox) loader.load();

        //CRIANDO UM ESTÁGIO DE DIÁLOGO - STAGE DIALOG
        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("MENU DE PRODUTOS");
        Scene scene = new Scene(vbox);
        dialogStage.setScene(scene);

        dialogStage.showAndWait();
    }
    
        public void tratarMenuSobre(ActionEvent evetn) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClienteController.class.getResource("/controller/PainelMenuSobre.fxml"));
        AnchorPane pane = (AnchorPane) loader.load();

        //CRIANDO UM ESTÁGIO DE DIÁLOGO - STAGE DIALOG
        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("SOBRE");
        Scene scene = new Scene(pane);
        dialogStage.setScene(scene);

        dialogStage.showAndWait();
    }
    
        public void tratarMenuSair(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelMenuPrincipal.getScene().getWindow();
        stage.close();

    }
}
