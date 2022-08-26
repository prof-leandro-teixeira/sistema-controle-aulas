package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelos.entidades.Disciplina;

public class ControleListaDisciplina implements Initializable{

	@FXML
	private TableView<Disciplina> tableViewDisciplina;	
	
	@FXML
	private TableColumn<Disciplina, Integer> tableColunmId;
	
	@FXML
	private TableColumn<Disciplina, String> tableColunmNome;
	
	@FXML
	private TableColumn<Disciplina, String> tableColunmArea;
	
	
	
	@FXML
	private Button btCadastro;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("ok");
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();		
		
	}


	private void initializeNodes() {
		tableColunmId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColunmNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColunmArea.setCellValueFactory(new PropertyValueFactory<>("area"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDisciplina.prefHeightProperty().bind(stage.heightProperty());
		
	}
}
