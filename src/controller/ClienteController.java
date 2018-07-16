package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Clientes;
import negocio.ClienteNegocio;
import negocio.NegocioException;
import util.DateUtil;
import util.PrintUtil;
import model.Clientes;



/**
 * FXML Controller class
 *
 * @author User
 */
public class ClienteController implements Initializable {

    //TELA INICIAL COM A TABELA
    @FXML
    private VBox painelTabelaCliente;
    @FXML
    private ToolBar toolBarMenu;
    @FXML
    private Button buttonCadastrar;
    @FXML
    private Button buttonEditar;
    @FXML
    private Button buttonRemover;
    @FXML
    private TableView<Clientes> tableViewClientes;
    @FXML
    private TableColumn<Clientes, String> tableColumnNome;
    @FXML
    private TableColumn<Clientes, String> tableColumnCPF;
    @FXML
    //Formato de data dd/MM/yyyy
    private TableColumn<Clientes, String> tableColumnDataNascimento;

    //TELA DE CADASTRO
    @FXML
    private AnchorPane painelFormularioCliente;
    @FXML
    private GridPane gridPaneFormularioCliente;
    @FXML
    private TextField textFieldNomeCliente;
    @FXML
    private TextField textFieldCpfCliente;
    @FXML
    private TextField textFieldEmailCliente;
    @FXML
    private TextField textFieldSaldoContaCliente;
    @FXML
    private TextField textFieldNumeroContaCliente;
    @FXML
    private DatePicker datePickerDataNascimentoCliente;
    @FXML 
    private Button buttonCancelar;
    @FXML
    private Button buttonSalvar;

    private List<Clientes> listaClientes;
    private Clientes clienteSelecionado;

    private ObservableList<Clientes> observableListaClientes;
    private ClienteNegocio clienteNegocio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteNegocio = new ClienteNegocio();

        if (tableViewClientes != null) {
            carregarTableViewClientes();
        }
    }

    private void carregarTableViewClientes() {

        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        tableColumnCPF.setCellValueFactory(new PropertyValueFactory<>("cpfCliente"));
        //Formato de data dd/MM/yyyy - precisa de um callback para converter o dado.
        tableColumnDataNascimento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Clientes, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Clientes, String> cell) {
                final Clientes cliente = cell.getValue();
                final SimpleObjectProperty<String> simpleObject = new SimpleObjectProperty(
                        DateUtil.dateToString(cliente.getDataNascimento()
                        )
                );
                return simpleObject;
            }

        });

        listaClientes = clienteNegocio.listar();

        observableListaClientes = FXCollections.observableArrayList(listaClientes);
        tableViewClientes.setItems(observableListaClientes);
    }
    

    @FXML
    public void tratarBotaoCadastrar(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClienteController.class.getResource("/controller/PainelFormularioCliente.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        
        //CRIANDO UM ESTÁGIO DE DIÁLOGO - STAGE DIALOG
        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("CADASTRO DE CLIENTES");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        dialogStage.showAndWait();
        this.carregarTableViewClientes();
        
    }

    @FXML
    public void tratarBotaoEditar(ActionEvent event) throws IOException {
        Clientes clienteSelec = tableViewClientes.getSelectionModel().getSelectedItem();
        if (clienteSelec != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/PainelFormularioCliente.fxml"));
            Parent root = (Parent) loader.load();

            ClienteController controller = (ClienteController) loader.getController();
            controller.setClienteSelecionado(clienteSelec);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("EDITAR CLIENTES");
            dialogStage.setScene(new Scene(root));
            
            dialogStage.showAndWait();
            carregarTableViewClientes();
        } else {
            PrintUtil.printMessageError("Precisa selecionar um cliente para editar.");
        }
    }

    @FXML
    public void tratarBotaoRemover(ActionEvent event) throws IOException {
        Clientes clienteSelec = tableViewClientes.getSelectionModel().getSelectedItem();
        if (clienteSelec != null) {
            try {
                clienteNegocio.deletar(clienteSelec);
                PrintUtil.printMessageSucesso("Cliente removido com sucesso!");
                this.carregarTableViewClientes();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
        } else {
            PrintUtil.printMessageError("Precisa selecionar um cliente para deletar.");
        }
    }

    @FXML
    public void tratarBotaoSalvar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioCliente.getScene().getWindow();

        if (clienteSelecionado == null) //Se for cadastrar
        {
            try {
                clienteNegocio.salvar(new Clientes(
                        textFieldNomeCliente.getText(),
                        textFieldCpfCliente.getText(),
                        textFieldEmailCliente.getText(),
                        textFieldSaldoContaCliente.getText(),
                        textFieldNumeroContaCliente.getText(),
                        datePickerDataNascimentoCliente.getValue()
                ));
                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }

        } else //Se for editar
        {
            try {
                clienteSelecionado.setNomeCliente(textFieldNomeCliente.getText());
                clienteSelecionado.setEmailCliente(textFieldEmailCliente.getText());
                clienteSelecionado.setSaldoConta(textFieldSaldoContaCliente.getText());
                clienteSelecionado.setDataNascimento(
                        datePickerDataNascimentoCliente.getValue()
                );
                clienteNegocio.atualizar(clienteSelecionado);
                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }

        }
    }

    @FXML
    public void tratarBotaoCancelar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioCliente.getScene().getWindow();
        stage.close();

    }

    public Clientes getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Clientes clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
        textFieldCpfCliente.setText(clienteSelecionado.getCpfCliente());
        textFieldCpfCliente.setEditable(false);
        textFieldNomeCliente.setText(clienteSelecionado.getNomeCliente());
        textFieldEmailCliente.setText(clienteSelecionado.getEmailCliente());
        //textField
        datePickerDataNascimentoCliente.setValue(clienteSelecionado.getDataNascimento());
        //implementar o resto dos textfield
    }
}
