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
import modelos.entidades.Professor;

public class ControleListaProfessor implements Initializable{

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
		tableColunmDisciplina.setCellValueFactory(new PropertyValueFactory<>("disciplina"));
		tableColunmTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		tableColunmEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		tableColunmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewProfessor.prefHeightProperty().bind(stage.heightProperty());
		
	}
}
