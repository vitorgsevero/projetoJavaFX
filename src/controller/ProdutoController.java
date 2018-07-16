package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Produtos;
import negocio.NegocioException;
import negocio.ProdutoNegocio;
import util.PrintUtil;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ProdutoController implements Initializable {
//TELA INICIAL COM A TABELA

    @FXML
    private VBox painelTabelaProduto;
    @FXML
    private ToolBar toolBarMenuProduto;
    @FXML
    private Button buttonCadastrar;
    @FXML
    private Button buttonEditar;
    @FXML
    private Button buttonRemover;
    @FXML
    private TableView<Produtos> tableViewProdutos;
    @FXML
    private TableColumn<Produtos, String> tableColumnCodigo;
    @FXML
    private TableColumn<Produtos, String> tableColumnNome;
    @FXML
    private TableColumn<Produtos, String> tableColumnPreco;

    //FORMULÁRIO
    @FXML
    private AnchorPane painelFormularioProduto;
    @FXML
    private GridPane gridPaneFormularioProduto;
    @FXML
    private TextField textFieldCodigoProduto;
    @FXML
    private TextField textFieldNomeProduto;
    @FXML
    private TextField textFieldPrecoProduto;
    @FXML 
    private Button buttonCancelar;
    @FXML
    private Button buttonSalvar;

    private List<Produtos> listaProdutos;
    private Produtos produtoSelecionado;

    private ObservableList<Produtos> observableListaProdutos;
    private ProdutoNegocio produtoNegocio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produtoNegocio = new ProdutoNegocio();

        if (tableViewProdutos != null) {
            this.carregarTableViewProdutos();
        }
    }

    private void carregarTableViewProdutos() {

        tableColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codProduto"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("precoProduto"));

        listaProdutos = produtoNegocio.listar();

        observableListaProdutos = FXCollections.observableArrayList(listaProdutos);
        tableViewProdutos.setItems(observableListaProdutos);
    }

    @FXML
    public void tratarBotaoCadastrar(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClienteController.class.getResource("/controller/PainelFormularioProduto.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        //CRIANDO UM ESTÁGIO DE DIÁLOGO - STAGE DIALOG
        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("CADASTRO DE PRODUTOS");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        dialogStage.showAndWait();
        this.carregarTableViewProdutos();

    }

    @FXML
    public void tratarBotaoEditar(ActionEvent event) throws IOException {
        Produtos produtoSelec = tableViewProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelec != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/PainelFormularioProduto.fxml"));
            Parent root = (Parent) loader.load();

            ProdutoController controller = (ProdutoController) loader.getController();
            controller.setProdutoSelecionado(produtoSelec);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("EDITAR PRODUTOS");
            dialogStage.setScene(new Scene(root));

            dialogStage.showAndWait();
            carregarTableViewProdutos();
        } else {
            PrintUtil.printMessageError("Precisa selecionar um produto para editar.");
        }
    }

    @FXML
    public void tratarBotaoRemover(ActionEvent event) throws IOException {
        Produtos produtoSelec = tableViewProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelec != null) {
            try {
                produtoNegocio.deletar(produtoSelec);
                PrintUtil.printMessageSucesso("Produto removido com sucesso!");
                this.carregarTableViewProdutos();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
        } else {
            PrintUtil.printMessageError("Precisa selecionar um produto para remover.");
        }
    }

    @FXML
    public void tratarBotaoSalvar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioProduto.getScene().getWindow();

        if (produtoSelecionado == null) //Se for cadastrar
        {
            try {
                produtoNegocio.salvar(new Produtos(
                        textFieldCodigoProduto.getText(),
                        textFieldNomeProduto.getText(),
                        textFieldPrecoProduto.getText()
                ));
                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }

        } else //Se for editar
        {
            try {
                produtoSelecionado.setCodProduto(textFieldCodigoProduto.getText());
                produtoSelecionado.setNomeProduto(textFieldNomeProduto.getText());
                produtoSelecionado.setPrecoProduto(textFieldPrecoProduto.getText());

                produtoNegocio.atualizar(produtoSelecionado);

                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }

        }

    }

    @FXML
    public void tratarBotaoCancelar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioProduto.getScene().getWindow();
        stage.close();

    }

    public Produtos getProdutoSelecionado() {
        return produtoSelecionado;
    }

    public void setProdutoSelecionado(Produtos produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;

        textFieldCodigoProduto.setText(produtoSelecionado.getCodProduto());
        textFieldCodigoProduto.setEditable(false);
        textFieldNomeProduto.setText(produtoSelecionado.getNomeProduto());
        //textFieldPrecoProduto.setText(produtoSelecionado.getPrecoProduto());

    }

}
