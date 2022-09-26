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
			exception.addError("Nome", "Campo não pode ficar vazio");
		}
		obj.setNome(txtNome.getText().toUpperCase());

		if (txtAno.getText() == null || txtAno.getText().trim().equals("")) {
			exception.addError("Ano", "Campo não pode ficar vazio");
		}
		obj.setAno(txtAno.getText());

		if (txtTelefone.getText() == null || txtTelefone.getText().trim().equals("")) {
			exception.addError("Telefone", "Campo não pode ficar vazio");
		}
		obj.setTelefone(txtTelefone.getText());

		if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
			exception.addError("Email", "Campo não pode ficar vazio");
		}
		obj.setEmail(txtEmail.getText().toLowerCase());

		if (txtResponsavel.getText() == null || txtResponsavel.getText().trim().equals("")) {
			exception.addError("Responsavel", "Campo não pode ficar vazio");
		}
		obj.setResponsavel(txtResponsavel.getText().toUpperCase());

		if (txtEndereco.getText() == null || txtEndereco.getText().trim().equals("")) {
			exception.addError("Endereco", "Campo não pode ficar vazio");
		}
		obj.setEndereco(txtEndereco.getText().toUpperCase());

		if (txtEscola.getText() == null || txtEscola.getText().trim().equals("")) {
			exception.addError("Escola", "Campo não pode ficar vazio");
		}
		obj.setEscola(txtEscola.getText().toUpperCase());

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

		labelErroNome.setText(((campos.contains("Nome") ? errors.get("Nome") : "")));
		labelErroAno.setText(((campos.contains("Ano") ? errors.get("Ano") : "")));
		labelErroTelefone.setText(((campos.contains("Telefone") ? errors.get("Telefone") : "")));
		labelErroEmail.setText(((campos.contains("Email") ? errors.get("Email") : "")));
		labelErroResponsavel.setText(((campos.contains("Responsavel") ? errors.get("Responsavel") : "")));
		labelErroEndereco.setText(((campos.contains("Endereco") ? errors.get("Endereco") : "")));
		labelErroEscola.setText(((campos.contains("Escola") ? errors.get("Escola") : "")));
		
		
	}
}