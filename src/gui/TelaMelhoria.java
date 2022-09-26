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
import modelos.entidades.Melhoria;
import modelos.servicos.ServicoMelhoria;

public class TelaMelhoria implements Initializable, DataChangeListener {
	@FXML
	private ServicoMelhoria servico;

	@FXML
	private TableView<Melhoria> tableViewMelhoria;

	@FXML
	private TableColumn<Melhoria, Integer> tableColunmId;

	@FXML
	private TableColumn<Melhoria, String> tableColunmTipo;

	@FXML
	private TableColumn<Melhoria, String> tableColunmDescricao;

	@FXML
	private TableColumn<Melhoria, Melhoria> tableColumnEdita;

	@FXML
	private TableColumn<Melhoria, Melhoria> tableColumnRemove;

	@FXML
	private Button btCadMelhoria;	

	@FXML
	private ObservableList<Melhoria> obsListMelhoria;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Melhoria obj = new Melhoria();
		criaCadastroMelhoria(obj, "/gui/CadastroMelhoria.fxml", parentStage);
	}

	public void setServicoMelhoria(ServicoMelhoria servico) {
		this.servico = servico;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColunmId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColunmTipo.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
		tableColunmDescricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewMelhoria.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalThreadStateException("Serviço em branco");
		}
		List<Melhoria> list = servico.findAll();
		obsListMelhoria = FXCollections.observableArrayList(list);
		tableViewMelhoria.setItems(obsListMelhoria);
		BtEditar();
		BtRemover();
	}

	private void criaCadastroMelhoria(Melhoria obj, String absoluteName, Stage parentStage) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			CadastroMelhoria controle = loader.getController();
			controle.setMelhoria(obj);
			controle.setServicoMelhoria(new ServicoMelhoria());
			controle.subscribeDataChangeListener(this);
			controle.updateForm();

			Stage formStage = new Stage();
			formStage.setTitle("Entre com a sugestão desejada.");
			formStage.setScene(new Scene(pane));
			formStage.setResizable(false);
			formStage.initOwner(parentStage);
			formStage.initModality(Modality.WINDOW_MODAL);
			formStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IOException", "Erro no carregamento", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanded() {
		updateTableView();
	}

	private void BtEditar() {
		tableColumnEdita.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEdita.setCellFactory(param -> new TableCell<Melhoria, Melhoria>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Melhoria obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> criaCadastroMelhoria(obj, "/gui/CadastroMelhoria.fxml",
						Utils.currentStage(event)));
			}
		});
	}

	private void BtRemover() {
		tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemove.setCellFactory(param -> new TableCell<Melhoria, Melhoria>() {
			private final Button button = new Button("Remove");

			@Override
			protected void updateItem(Melhoria obj, boolean empty) {
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

	private void removeEntity(Melhoria obj) {
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