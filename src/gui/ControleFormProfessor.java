package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setNome(txtNome.getText());
		obj.setDisciplina(txtDisciplina.getText());
		obj.setTelefone(Utils.tryParseToInt(txtTelefone.getText()));
		obj.setEndereco(txtEndereco.getText());
		obj.setEmail(txtEmail.getText());
		
		return obj;
		}
	
	@FXML
	private void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
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

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 40);
		Constraints.setTextFieldMaxLength(txtDisciplina, 40);
		Constraints.setTextFieldInteger(txtTelefone);
		Constraints.setTextFieldMaxLength(txtEndereco, 50);
		Constraints.setTextFieldMaxLength(txtEmail, 40);
		
	}

}
