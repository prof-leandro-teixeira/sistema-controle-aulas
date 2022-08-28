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
import modelos.entidades.Aluno;
import modelos.servicos.ServicoAluno;

public class ControleListaAluno implements Initializable{
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
	private Button btCadastro;
	
	@FXML
	private ObservableList<Aluno> obsListA;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("ok");
	}
	
	public void setServicoAluno(ServicoAluno servico) {
		this.servico = servico;
	}
		
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();			
	}

	private void initializeNodes() {
		tableColunmId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColunmNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColunmResponsavel.setCellValueFactory(new PropertyValueFactory<>("responsavel"));
		tableColunmTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		tableColunmEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		tableColunmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewAluno.prefHeightProperty().bind(stage.heightProperty());	
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalThreadStateException("Serviço em branco");
		}
		List<Aluno> list = servico.findAll();
		obsListA = FXCollections.observableArrayList(list);
		tableViewAluno.setItems(obsListA);
	}
}