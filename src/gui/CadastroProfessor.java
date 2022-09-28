package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import modelo.exceptions.ValidationException;
import modelos.entidades.Disciplina;
import modelos.entidades.Professor;
import modelos.servicos.ServicoDisciplina;
import modelos.servicos.ServicoProfessor;



public class CadastroProfessor implements Initializable {

	private Professor entidade;
	private ServicoProfessor servico;
	private ServicoDisciplina servicoDisciplina;
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtTelefone;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField doubleHoraAula;

	@FXML
	private DatePicker dpDataNascimento;

	@FXML
	private ComboBox<Disciplina> cbDisciplina;

	@FXML
	private Label labelErroNome;

	@FXML
	private Label labelErroTelefone;

	@FXML
	private Label labelErroEmail;

	@FXML
	private Label labelErroHoraAula;

	@FXML
	private Label labelErroDataNascimento;

	@FXML
	private Button btSalvaProfessor;

	@FXML
	private Button btCancelaProfessor;

	private ObservableList<Disciplina> obsList;

	public void setProfessor(Professor entidade) {
		this.entidade = entidade;
	}

	public void setServicos(ServicoProfessor servico, ServicoDisciplina servicoDisciplina) {
		this.servico = servico;
		this.servicoDisciplina = servicoDisciplina;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@FXML
	private void onBtSaveAction(ActionEvent event) {
		if (entidade == null) {
			throw new IllegalStateException("Entrada vazia.");
		}
		if (servico == null) {
			throw new IllegalStateException("Serviço vazio.");
		}
		try {
			entidade = getForm();
			servico.salvaOuAtualiza(entidade);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessagens(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Erro ao salvar objeto", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanded();
		}
	}

	private Professor getForm() {
		Professor obj = new Professor();

		ValidationException exception = new ValidationException("Erro de Validação");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "Campo não pode ficar vazio");
		}
		obj.setNome(txtNome.getText());

		if (txtTelefone.getText() == null || txtTelefone.getText().trim().equals("")) {
			exception.addError("telefone", "Campo não pode ficar vazio");
		}
		obj.setTelefone(txtTelefone.getText());

		if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
			exception.addError("email", "Campo não pode ficar vazio");
		}
		obj.setEmail(txtEmail.getText());

		if (doubleHoraAula.getText() == null || doubleHoraAula.getText().trim().equals("")) {
			exception.addError("horaAula", "Campo não pode ficar vazio");
		}
		obj.setHoraAula(Utils.tryParseToDouble(doubleHoraAula.getText()));
		
		if (dpDataNascimento.getValue() == null ) {
			exception.addError("dataNascimento", "Campo não pode ficar vazio");
		}
		else {
			Instant instant = Instant.from(dpDataNascimento.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setDataNascimento(Date.from(instant));
		}
		obj.setDisciplina(cbDisciplina.getValue());
		
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	@FXML
	private void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 50);
		Constraints.setTextFieldInteger(txtTelefone);
		Constraints.setTextFieldMaxLength(txtEmail, 50);
		Constraints.setTextFieldDouble(doubleHoraAula);
		Utils.formatDatePicker(dpDataNascimento, "dd/MM/yyyy");
		
		carregaComboboxDisciplina();

	}

	public void updateForm() {
		if (entidade == null) {
			throw new IllegalStateException("Entidade vazia");
		}
		txtId.setText(String.valueOf(entidade.getId()));
		txtNome.setText(entidade.getNome());
		txtTelefone.setText(String.valueOf(entidade.getTelefone()));
		txtEmail.setText(entidade.getEmail());
		Locale.setDefault(Locale.US);
		doubleHoraAula.setText(String.format("%.2f", entidade.getHoraAula()));
		
		
		if (entidade.getDataNascimento() != null) {
			dpDataNascimento.setValue(LocalDate.ofInstant(entidade.getDataNascimento().toInstant(), ZoneId.systemDefault()));
		}
		if (entidade.getDisciplina() == null) {
			cbDisciplina.getSelectionModel().selectFirst();
		}
		else {
			cbDisciplina.setValue(entidade.getDisciplina());
		}
	}

	public void carregaObjetosAssociados() {
		if (servicoDisciplina == null) {
			throw new IllegalStateException("Serviço Disciplina não pode ser nulo");
		}
		List<Disciplina> list = servicoDisciplina.findAll();
		obsList = FXCollections.observableArrayList(list);
		cbDisciplina.setItems(obsList);
	}
	
	private void setErrorMessagens(Map<String, String> errors) {
		Set<String> campos = errors.keySet();

		if (campos.contains("nome")) {
			labelErroNome.setText(errors.get("nome"));
		}
		if (campos.contains("telefone")) {
			labelErroTelefone.setText(errors.get("telefone"));
		}
		if (campos.contains("email")) {
			labelErroEmail.setText(errors.get("email"));
		}
		
		if (campos.contains("horaAula")) {
			labelErroHoraAula.setText(errors.get("horaAula"));
		}
		
		
		if (campos.contains("dataNascimento")) {
			labelErroDataNascimento.setText(errors.get("dataNascimento"));
		}
		
	}

	private void carregaComboboxDisciplina() {
		Callback<ListView<Disciplina>, ListCell<Disciplina>> factory = lv -> new ListCell<Disciplina>() {
			@Override
			protected void updateItem(Disciplina item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};
		cbDisciplina.setCellFactory(factory);
		cbDisciplina.setButtonCell(factory.call(null));
	}

	
}