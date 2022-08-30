package gui;

import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
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
import modelos.entidades.Aula;
import modelos.servicos.ServicoAula;

public class ControleListaAula2 implements Initializable{
	@FXML
	private ServicoAula servico;

	@FXML
	private TableView<Aula> tableViewAula;	
	
	@FXML
	private TableColumn<Aula, Integer> tableColunmId;
	
	@FXML
	private TableColumn<Aula, Date> tableColunmData;
	
	@FXML
	private TableColumn<Aula, Date> tableColunmHoraInicio;
	
	@FXML
	private TableColumn<Aula, Date> tableColunmHoraFim;
	
	@FXML
	private TableColumn<Aula, String> tableColunmAluno;
	
	@FXML
	private TableColumn<Aula, String> tableColunmDisciplina;
	
	@FXML
	private TableColumn<Aula, String> tableColunmProfessor;
	@FXML
	private Button btCadastro;
	
	@FXML
	private ObservableList<Aula> obsListA;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("ok");
	}
	
	public void setServicoAula(ServicoAula servico) {
		this.servico = servico;
	}
		
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();			
	}

	private void initializeNodes() {
		tableColunmId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColunmData.setCellValueFactory(new PropertyValueFactory<>("data"));
		tableColunmHoraInicio.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
		tableColunmHoraFim.setCellValueFactory(new PropertyValueFactory<>("horaFim"));
		tableColunmAluno.setCellValueFactory(new PropertyValueFactory<>("aluno"));
		tableColunmDisciplina.setCellValueFactory(new PropertyValueFactory<>("disciplina"));
		tableColunmProfessor.setCellValueFactory(new PropertyValueFactory<>("professor"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewAula.prefHeightProperty().bind(stage.heightProperty());	
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalThreadStateException("Serviço em branco");
		}
		List<Aula> list = null;
		try {
			list = servico.findAll();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		obsListA = FXCollections.observableArrayList(list);
		tableViewAula.setItems(obsListA);
	}
}