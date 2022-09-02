package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
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
import modelos.entidades.Professor;
import modelos.servicos.ServicoProfessor;

public class ControleCadProfessor implements Initializable{
	@FXML
	private ServicoProfessor servico;

	@FXML
	private TableView<Professor> tableViewProfessor;	
	
	@FXML
	private TableColumn<Professor, Integer> tableColunmId;
	
	@FXML
	private TableColumn<Professor, String> tableColunmNome;
	
	@FXML
	private TableColumn<Professor, String> tableColunmDisciplina;
	
	@FXML
	private TableColumn<Professor, Integer> tableColunmTelefone;
	
	@FXML
	private TableColumn<Professor, String> tableColunmEndereco;
	
	@FXML
	private TableColumn<Professor, String> tableColunmEmail;
	
	@FXML
	private Button btCadProfessor;
	
	@FXML
	private ObservableList<Professor> obsListA;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		criaFormProfessor("/gui/FormProfessor.fxml", parentStage);
	}
	
	public void setServicoProfessor(ServicoProfessor servico) {
		this.servico = servico;
	}
		
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();			
	}

	private void initializeNodes() {
		tableColunmId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColunmNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColunmDisciplina.setCellValueFactory(new PropertyValueFactory<>("disciplina"));
		tableColunmTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		tableColunmEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		tableColunmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewProfessor.prefHeightProperty().bind(stage.heightProperty());	
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalThreadStateException("Serviço em branco");
		}
		List<Professor> list = servico.findAll();
		obsListA = FXCollections.observableArrayList(list);
		tableViewProfessor.setItems(obsListA);
	}

private void criaFormProfessor(String absoluteName,Stage parentStage) {
	
	try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		Pane pane = loader.load();
		
		Stage formStage = new Stage();
		formStage.setTitle("Entre com os dados do professor.");
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
}