package gui;

import java.io.IOException;
import java.net.URL;
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
import modelos.entidades.Disciplina;
import modelos.servicos.ServicoDisciplina;

public class TelaControleDisciplina implements Initializable, DataChangeListener {
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
	private TableColumn<Disciplina, Disciplina> tableColumnEdita;

	@FXML
	private TableColumn<Disciplina, Disciplina> tableColumnRemove;

	@FXML
	private Button btCadDisciplina;

	@FXML
	private ObservableList<Disciplina> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Disciplina obj = new Disciplina();
		criaCadastroDisciplina(obj, "/gui/CadastroDisciplina.fxml", parentStage);
	}

	public void setServicoDisciplina(ServicoDisciplina servico) {
		this.servico = servico;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColunmId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColunmNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		tableColunmArea.setCellValueFactory(new PropertyValueFactory<>("Area"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDisciplina.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalThreadStateException("Serviço em branco");
		}
		List<Disciplina> list = servico.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDisciplina.setItems(obsList);
		BtEditar();
		BtRemove();
	}

	private void criaCadastroDisciplina(Disciplina obj, String absoluteName, Stage parentStage) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			CadastroDisciplina controle = loader.getController();
			controle.setDisciplina(obj);
			controle.setServicoDisciplina(new ServicoDisciplina());
			controle.subscribeDataChangeListener(this);
			controle.updateForm();

			Stage formStage = new Stage();
			formStage.setTitle("Entre com os dados da disciplina.");
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
		tableColumnEdita.setCellFactory(param -> new TableCell<Disciplina, Disciplina>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Disciplina obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> criaCadastroDisciplina(obj, "/gui/CadastroDisciplina.fxml",
						Utils.currentStage(event)));
			}
		});
	}

	private void BtRemove() {
		tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemove.setCellFactory(param -> new TableCell<Disciplina, Disciplina>() {
			private final Button button = new Button("Remove");

			@Override
			protected void updateItem(Disciplina obj, boolean empty) {
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

	private void removeEntity(Disciplina obj) {
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