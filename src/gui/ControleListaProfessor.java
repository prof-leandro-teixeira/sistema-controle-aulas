package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelos.entidades.Professor;
import modelos.servicos.ServicoProfessor;

public class ControleListaProfessor implements Initializable{
	
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
	private Button btCadastro;
	
	//atributos
	private ObservableList<Professor> obsListP;
		
	@FXML
	public void onBtNewAction() {
		System.out.println("ok");
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
			obsListP = FXCollections.observableArrayList(list);
			tableViewProfessor.setItems(obsListP);
		}
	}