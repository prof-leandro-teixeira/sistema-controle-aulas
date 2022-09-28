package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.exceptions.ValidationException;
import modelos.entidades.Aluno;
import modelos.servicos.ServicoAluno;

public class CadastroAluno implements Initializable {

	private Aluno entidade;
	private ServicoAluno servico;
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtAno;

	@FXML
	private TextField txtTelefone;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtResponsavel;

	@FXML
	private TextField txtEndereco;

	@FXML
	private TextField txtEscola;
	
	@FXML
	private Label labelErroNome;
	
	@FXML
	private Label labelErroAno;
	
	@FXML
	private Label labelErroTelefone;
	
	@FXML
	private Label labelErroEmail;
	
	@FXML
	private Label labelErroResponsavel;
	
	@FXML
	private Label labelErroEndereco;
	
	@FXML
	private Label labelErroEscola;


	@FXML
	private Button btSalvaAluno;

	@FXML
	private Button btCancelaAluno;

	public void setAluno(Aluno entidade) {
		this.entidade = entidade;
	}

	public void setServicoAluno(ServicoAluno servico) {
		this.servico = servico;
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

	private Aluno getForm() {
		Aluno obj = new Aluno();

		ValidationException exception = new ValidationException("Erro de Validação");

		obj.setId(Utils.tryParseToInt(txtId.getText()));

		if (txtNome.getText()== null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "Campo não pode ficar vazio");
		}
		obj.setNome(txtNome.getText());

		if (txtAno.getText() == null || txtAno.getText().trim().equals("")) {
			exception.addError("ano", "Campo não pode ficar vazio");
		}
		obj.setAno(txtAno.getText());

		if (txtTelefone.getText() == null || txtTelefone.getText().trim().equals("")) {
			exception.addError("telefone", "Campo não pode ficar vazio");
		}
		obj.setTelefone(txtTelefone.getText());

		if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
			exception.addError("email", "Campo não pode ficar vazio");
		}
		obj.setEmail(txtEmail.getText());

		if (txtResponsavel.getText() == null || txtResponsavel.getText().trim().equals("")) {
			exception.addError("responsavel", "Campo não pode ficar vazio");
		}
		obj.setResponsavel(txtResponsavel.getText());

		if (txtEndereco.getText() == null || txtEndereco.getText().trim().equals("")) {
			exception.addError("endereco", "Campo não pode ficar vazio");
		}
		obj.setEndereco(txtEndereco.getText());

		if (txtEscola.getText() == null || txtEscola.getText().trim().equals("")) {
			exception.addError("escola", "Campo não pode ficar vazio");
		}
		obj.setEscola(txtEscola.getText());

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
		Constraints.setTextFieldMaxLength(txtNome, 60);
		Constraints.setTextFieldMaxLength(txtAno, 2);
		Constraints.setTextFieldMaxLength(txtTelefone, 12);
		Constraints.setTextFieldMaxLength(txtEmail, 40);
		Constraints.setTextFieldMaxLength(txtResponsavel, 60);
		Constraints.setTextFieldMaxLength(txtEndereco, 60);
		Constraints.setTextFieldMaxLength(txtEscola, 20);

	}

	public void updateForm() {
		if (entidade == null) {
			throw new IllegalStateException("Entidade vazia");
		}
		txtId.setText(String.valueOf(entidade.getId()));
		txtNome.setText(entidade.getNome());
		txtAno.setText(entidade.getAno());
		txtTelefone.setText(entidade.getTelefone());
		txtEmail.setText(entidade.getEmail());
		txtResponsavel.setText(entidade.getResponsavel());
		txtEndereco.setText(entidade.getEndereco());
		txtEscola.setText(entidade.getEscola());

	}
	private void setErrorMessagens(Map<String, String> errors) {
		Set<String> campos = errors.keySet();

		if (campos.contains("nome")) {
			labelErroNome.setText(errors.get("nome"));
		}
		if (campos.contains("telefone")) {
			labelErroTelefone.setText(errors.get("telefone"));
		}
		if (campos.contains("ano")) {
			labelErroAno.setText(errors.get("ano"));
		}
		if (campos.contains("email")) {
			labelErroEmail.setText(errors.get("email"));
		}
		if (campos.contains("responsavel")) {
			labelErroResponsavel.setText(errors.get("responsavel"));
		}
		if (campos.contains("endereco")) {
			labelErroEndereco.setText(errors.get("endereco"));
		}
		if (campos.contains("escola")) {
			labelErroEscola.setText(errors.get("escola"));
		}
		
		
	}
}