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
import modelos.entidades.Disciplina;
import modelos.servicos.ServicoDisciplina;

public class ControleCadDisciplina implements Initializable{
	@FXML
	private ServicoDisciplina servico;

	@FXML
	private TableView<Disciplina> tableViewDisciplina;	
	
	@FXML
	private TableColumn<Disciplina, Integer> tableColunmId;
	
	@FXML
	private TableColumn<Disciplina, String> tableColunmNome;
	
	@FXML
	private TableColumn<Disciplina, String> tableColunmArea;
	
	@FXML
	private Button btCadDisciplina;
	
	@FXML
	private ObservableList<Disciplina> obsListA;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Disciplina obj = new Disciplina();
		criaFormDisciplina(obj,"/gui/FormDisciplina.fxml", parentStage);
	}
	
	public void setServicoDisciplina(ServicoDisciplina servico) {
		this.servico = servico;
	}
		
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();			
	}

	private void initializeNodes() {
		tableColunmId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColunmNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColunmArea.setCellValueFactory(new PropertyValueFactory<>("Área"));
				
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDisciplina.prefHeightProperty().bind(stage.heightProperty());	
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalThreadStateException("Serviço em branco");
		}
		List<Disciplina> list = servico.findAll();
		obsListA = FXCollections.observableArrayList(list);
		tableViewDisciplina.setItems(obsListA);
	}
	
	private void criaFormDisciplina(Disciplina obj, String absoluteName,Stage parentStage) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			ControleFormDisciplina controle = loader.getController();
			controle.setDisciplina(obj);
			controle.setServicoDisciplina(new ServicoDisciplina());
			controle.updateForm();
			
			
			
			Stage formStage = new Stage();
			formStage.setTitle("Entre com os dados da disciplina.");
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