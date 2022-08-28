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
import modelos.entidades.Disciplina;
import modelos.servicos.ServicoDisciplina;

public class ControleListaDisciplina implements Initializable{

	private ServicoDisciplina servico;
	
	//objetos
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
	
	//atributos
	private ObservableList<Disciplina> obsListD;
	
	//métodos
	@FXML
	public void onBtNewAction() {
		System.out.println("ok");
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
		tableColunmArea.setCellValueFactory(new PropertyValueFactory<>("area"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDisciplina.prefHeightProperty().bind(stage.heightProperty());
		
	}
	public void updateTableView() {
		if (servico == null) {
			throw new IllegalThreadStateException("Serviço em branco");
		}
		List<Disciplina> list = servico.findAll();
		obsListD = FXCollections.observableArrayList(list);
		tableViewDisciplina.setItems(obsListD);
	}
}