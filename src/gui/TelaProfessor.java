package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelos.entidades.Professor;
import modelos.servicos.ServicoDisciplina;
import modelos.servicos.ServicoProfessor;

public class TelaProfessor implements Initializable, DataChangeListener {
	@FXML
	private ServicoProfessor servico;

	@FXML
	private TableView<Professor> tableViewProfessor;

	@FXML
	private TableColumn<Professor, Integer> tableColunmId;

	@FXML
	private TableColumn<Professor, String> tableColunmNome;
	
	@FXML
	private TableColumn<Professor, Integer> tableColunmDisciplina;

	@FXML
	private TableColumn<Professor, Integer> tableColunmTelefone;

	@FXML
	private TableColumn<Professor, String> tableColunmEmail;

	@FXML
	private TableColumn<Professor, Double> tableColunmHoraAula;

	@FXML
	private TableColumn<Professor, Date> tableColunmDataNascimento;

	@FXML
	private TableColumn<Professor, Professor> tableColumnEdita;

	@FXML
	private TableColumn<Professor, Professor> tableColumnRemove;

	@FXML
	private Button btCadProfessor;

	private ObservableList<Professor> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Professor obj = new Professor();
		criaCadastroProfessor(obj, "/gui/CadastroProfessor.fxml", parentStage);
	}

	public void setServicoProfessor(ServicoProfessor servico) {
		this.servico = servico;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColunmId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColunmNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		tableColunmDisciplina.setCellValueFactory(new PropertyValueFactory<>("Disciplina"));
		tableColunmTelefone.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
		tableColunmEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
		tableColunmHoraAula.setCellValueFactory(new PropertyValueFactory<>("HoraAula"));
		Utils.formatTableColumnDouble(tableColunmHoraAula,2);
		tableColunmDataNascimento.setCellValueFactory(new PropertyValueFactory<>("DataNascimento"));
		Utils.formatTableColumnDate(tableColunmDataNascimento, "dd/MM/yyyy");
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewProfessor.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalThreadStateException("Serviço em branco");
		}
		List<Professor> list = servico.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewProfessor.setItems(obsList);
		BtEditar();
		BtRemover();
	}

	private void criaCadastroProfessor(Professor obj, String absoluteName, Stage parentStage) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			CadastroProfessor controle = loader.getController();
			controle.setProfessor(obj);
			controle.setServicos(new ServicoProfessor(), new ServicoDisciplina());
			controle.carregaObjetosAssociados();
			controle.subscribeDataChangeListener(this);
			controle.updateForm();

			Stage formStage = new Stage();
			formStage.setTitle("Entre com os dados do professor.");
			formStage.setScene(new Scene(pane));
			formStage.setResizable(false);
			formStage.initOwner(parentStage);
			formStage.initModality(Modality.WINDOW_MODAL);
			formStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IOException", "Erro no carregamento", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanded() {
		updateTableView();
	}

	private void BtEditar() {
		tableColumnEdita.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEdita.setCellFactory(param -> new TableCell<Professor, Professor>() {

			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Professor obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> criaCadastroProfessor(obj, "/gui/CadastroProfessor.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void BtRemover() {
		tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemove.setCellFactory(param -> new TableCell<Professor, Professor>() {

			private final Button button = new Button("Remover");

			@Override
			protected void updateItem(Professor obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Professor obj) {
		Optional<ButtonType> resultado = Alerts.showConfirmation("AÇÃO NÃO PODE SER DESFEITA!",
				"Você está certo desta ação?");
		if (resultado.get() == ButtonType.OK) {
			if (servico == null) {
				throw new IllegalStateException("Serviço não pode ficar vazio.");
			}
			try {
				servico.deleta(obj);
				updateTableView();
			} catch (DbIntegrityException e) {
				Alerts.showAlert("Erro ao remover objeto", null, e.getMessage(), AlertType.ERROR);

			}
		}
	}
}