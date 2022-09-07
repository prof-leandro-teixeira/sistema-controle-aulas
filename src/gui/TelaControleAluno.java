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
import modelos.entidades.Aluno;
import modelos.servicos.ServicoAluno;

public class TelaControleAluno implements Initializable, DataChangeListener {
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
	private TableColumn<Aluno, Aluno> tableColunmEdita;

	@FXML
	private TableColumn<Aluno, Aluno> tableColunmRemove;

	@FXML
	private Button btCadAluno;

	@FXML
	private ObservableList<Aluno> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Aluno obj = new Aluno();
		criaCadastroAluno(obj, "/gui/CadastroAluno.fxml", parentStage);
	}

	public void setServicoAluno(ServicoAluno servico) {
		this.servico = servico;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColunmId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColunmNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		tableColunmResponsavel.setCellValueFactory(new PropertyValueFactory<>("Responsavel"));
		tableColunmTelefone.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
		tableColunmEndereco.setCellValueFactory(new PropertyValueFactory<>("Endereco"));
		tableColunmEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewAluno.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalThreadStateException("Serviço em branco");
		}
		List<Aluno> list = servico.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewAluno.setItems(obsList);
		BtEditar();
		BtRemove();
	}

	private void criaCadastroAluno(Aluno obj, String absoluteName, Stage parentStage) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			CadastroAluno controle = loader.getController();
			controle.setAluno(obj);
			controle.setServicoAluno(new ServicoAluno());
			controle.subscribeDataChangeListener(this);
			controle.updateForm();

			Stage formStage = new Stage();
			formStage.setTitle("Entre com os dados do aluno.");
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
		tableColunmEdita.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColunmEdita.setCellFactory(param -> new TableCell<Aluno, Aluno>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Aluno obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> criaCadastroAluno(obj, "/gui/CadastroAluno.fxml", 
								Utils.currentStage(event)));
			}
		});
	}

	private void BtRemove() {
		tableColunmRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColunmRemove.setCellFactory(param -> new TableCell<Aluno, Aluno>() {
			private final Button button = new Button("Remove");

			@Override
			protected void updateItem(Aluno obj, boolean empty) {
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

	private void removeEntity(Aluno obj) {
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
