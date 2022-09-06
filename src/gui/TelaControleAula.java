package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
import modelos.entidades.Aula;
import modelos.servicos.ServicoAula;

public class TelaControleAula implements Initializable, DataChangeListener{
	@FXML
	private ServicoAula servico;

	@FXML
	private TableView<Aula> tableViewAula;	
	
	@FXML
	private TableColumn<Aula, Integer> tableColunmId;
	
	@FXML
	private TableColumn<Aula, Date> tableColunmDia;
	
	@FXML
	private TableColumn<Aula, Date> tableColunmInicio;
	
	@FXML
	private TableColumn<Aula, Date> tableColunmFim;
	
	@FXML
	private TableColumn<Aula, String> tableColunmAluno;
	
	@FXML
	private TableColumn<Aula, String> tableColunmDisciplina;
	
	@FXML
	private TableColumn<Aula, String> tableColunmProfessor;
	
	@FXML
	private TableColumn<Aula, String> tableColunmDuracao;
	
	@FXML
	private Button btCadAula;
	
	@FXML
	private ObservableList<Aula> obsList;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Aula obj = new Aula();
		criaCadastroAula(obj,"/gui/CadastroAula.fxml", parentStage);
	}
	
	public void setServicoAula(ServicoAula servico) {
		this.servico = servico;
	}
		
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();			
	}

	private void initializeNodes() {
		tableColunmId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColunmDia.setCellValueFactory(new PropertyValueFactory<>("Dia"));
		tableColunmInicio.setCellValueFactory(new PropertyValueFactory<>("Inicio"));
		tableColunmFim.setCellValueFactory(new PropertyValueFactory<>("Fim"));
		tableColunmAluno.setCellValueFactory(new PropertyValueFactory<>("Aluno"));
		tableColunmDisciplina.setCellValueFactory(new PropertyValueFactory<>("Disciplina"));
		tableColunmProfessor.setCellValueFactory(new PropertyValueFactory<>("Professor"));
		tableColunmDuracao.setCellValueFactory(new PropertyValueFactory<>("Duracao"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewAula.prefHeightProperty().bind(stage.heightProperty());	
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalThreadStateException("Serviço em branco");
		}
		List<Aula> list = servico.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewAula.setItems(obsList);
	}
	private void criaCadastroAula(Aula obj, String absoluteName,Stage parentStage) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			CadastroAula controle = loader.getController();
			controle.setAula(obj);
			controle.setServicoAula(new ServicoAula());
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