package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelos.entidades.Aluno;
import modelos.servicos.ServicoAluno;

public class TelaControleAluno implements Initializable, DataChangeListener{
	@FXML
	private ServicoAluno servico;

	@FXML
	private TableView<Aluno> tableViewAluno;	
	
	@FXML
	private TableColumn<Aluno, Integer> tableColunmId;
	
	@FXML
	private TableColumn<Aluno, String> tableColunmNome;
	
	@FXML
	private TableColumn<Aluno, String> tableColunmResponsavel;
	
	@FXML
	private TableColumn<Aluno, Integer> tableColunmTelefone;
	
	@FXML
	private TableColumn<Aluno, String> tableColunmEndereco;
	
	@FXML
	private TableColumn<Aluno, String> tableColunmEmail;
	
	@FXML
	private Button btCadAluno;
	
	@FXML
	private ObservableList<Aluno> obsList;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Aluno obj = new Aluno();
		criaCadastroAluno(obj,"/gui/CadastroAluno.fxml", parentStage);
	}
	
	public void setServicoAluno(ServicoAluno servico) {
		this.servico = servico;
	}
		
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();			
	}

	private void initializeNodes() {
		tableColunmId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColunmNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		tableColunmResponsavel.setCellValueFactory(new PropertyValueFactory<>("Responsavel"));
		tableColunmTelefone.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
		tableColunmEndereco.setCellValueFactory(new PropertyValueFactory<>("Endereco"));
		tableColunmEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewAluno.prefHeightProperty().bind(stage.heightProperty());	
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalThreadStateException("Serviço em branco");
		}
		List<Aluno> list = servico.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewAluno.setItems(obsList);
	}
	private void criaCadastroAluno(Aluno obj, String absoluteName,Stage parentStage) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			CadastroAluno controle = loader.getController();
			controle.setAluno(obj);
			controle.setServicoAluno(new ServicoAluno());
			controle.subscribeDataChangeListener(this);
			controle.updateForm();
			
			Stage formStage = new Stage();
			formStage.setTitle("Entre com os dados do aluno.");
			formStage.setScene(new Scene(pane));
			formStage.setResizable(false);
			formStage.initOwner(parentStage);
			formStage.initModality(Modality.WINDOW_MODAL);
			formStage.showAndWait();

		} 
		catch (IOException e) {
			Alerts.showAlert("IOException", "Erro no carregamento", e.getMessage(), AlertType.ERROR);
		}
		
	}

	@Override
	public void onDataChanded() {
		updateTableView();
		
	}
}