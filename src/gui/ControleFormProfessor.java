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
import modelos.entidades.Professor;
import modelos.servicos.ServicoProfessor;

public class ControleFormProfessor implements Initializable {
	
	private Professor entidade;
	
	private ServicoProfessor servico;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtDisciplina;
	
	@FXML
	private TextField txtTelefone;
	
	@FXML
	private TextField txtEndereco;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private Label labelErro;
	
	@FXML
	private Button btSalva;
	
	@FXML
	private Button btCancela;
	
	public void setProfessor (Professor entidade) {
		this.entidade = entidade;
	}
	
	public void setServicoProfessor (ServicoProfessor servico) {
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
		}
		catch (ValidationException e) {
			setErrorMessagens(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Erro ao salvar objeto", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners){
			listener.onDataChanded();
		}		
	}

	private Professor getForm() {
		Professor obj = new Professor();
		
ValidationException exception = new ValidationException("Erro de Validação");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if (txtNome.getText()==null || txtNome.getText().trim().equals("")){
			exception.addError("Nome", "Campo não pode ficar vazio");
		}
		obj.setNome(txtNome.getText());
		
		
		if (txtDisciplina.getText()== null || txtDisciplina.getText().trim().equals("")){
			exception.addError("Disciplina", "Campo não pode ficar vazio");
		}
		obj.setDisciplina(txtDisciplina.getText());
		
		
		if (txtTelefone.getText()== null || txtTelefone.getText().trim().equals("")){
			exception.addError("Telefone", "Campo não pode ficar vazio");
		}
		obj.setTelefone(Utils.tryParseToInt(txtTelefone.getText()));
		
		if (txtEndereco.getText()== null || txtEndereco.getText().trim().equals("")){
			exception.addError("Endereco", "Campo não pode ficar vazio");
		}
		obj.setEndereco(txtEndereco.getText());
		
		
		if (txtEmail.getText()== null || txtEmail.getText().trim().equals("")){
			exception.addError("Endereco", "Campo não pode ficar vazio");
		}
		obj.setEmail(txtEmail.getText());
		
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
		Constraints.setTextFieldMaxLength(txtDisciplina, 50);
		Constraints.setTextFieldMaxLength(txtTelefone, 50);
		Constraints.setTextFieldMaxLength(txtEndereco, 50);
		Constraints.setTextFieldMaxLength(txtEmail, 50);
	}
	public void updateForm() {
		if (entidade == null) {
			throw new IllegalStateException("Entidade vazia");
		}
		txtId.setText(String.valueOf(entidade.getId()));
		txtNome.setText(entidade.getNome());
		txtDisciplina.setText(entidade.getDisciplina());
		txtTelefone.setText(String.valueOf(entidade.getTelefone()));
		txtEndereco.setText(entidade.getEndereco());
		txtEmail.setText(entidade.getEmail());
	}
	
	private void setErrorMessagens(Map<String, String> errors) {
		Set<String> campos = errors.keySet();
		
		if (campos.contains("Nome")) {
			labelErro.setText(errors.get("Nome"));
		}
	}
}
