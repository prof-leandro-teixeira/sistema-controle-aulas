package gui;

import java.net.URL;
import java.sql.Date;
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
import modelos.entidades.Aula;
import modelos.servicos.ServicoAula;

public class CadastroAula implements Initializable {
	
	private Aula entidade;
	
	private ServicoAula servico;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtDia;
	
	@FXML
	private TextField txtInicio;
	
	@FXML
	private TextField txtFim;
	
	@FXML
	private TextField txtAluno;
	
	@FXML
	private TextField txtDisciplina;
	
	@FXML
	private TextField txtProfessor;
	
	@FXML
	private TextField txtDuracao;
	
	@FXML
	private Label labelErro;
	
	@FXML
	private Button btSalva;
	
	@FXML
	private Button btCancela;
	
	public void setAula (Aula entidade) {
		this.entidade = entidade;
	}
	
	public void setServicoAula (ServicoAula servico) {
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

	private Aula getForm() {
		Aula obj = new Aula();
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setDia((Date) Utils.tryParseToDate(txtDia.getText()));
		obj.setInicio((Date) Utils.tryParseToDate(txtInicio.getText()));
		obj.setId(Utils.tryParseToInt(txtFim.getText()));
		obj.setAluno(txtAluno.getText());
		obj.setDisciplina(txtDisciplina.getText());
		
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
		txtDia.setText(String.valueOf(entidade.getDia()));
		txtInicio.setText(String.valueOf(entidade.getInicio()));
		txtFim.setText(String.valueOf(entidade.getFim()));
		txtAluno.setText(entidade.getAluno());
		txtDisciplina.setText(entidade.getDisciplina());
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();	
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtDia, 40);
		Constraints.setTextFieldMaxLength(txtInicio, 40);
		Constraints.setTextFieldInteger(txtFim);
		Constraints.setTextFieldMaxLength(txtAluno, 50);
		Constraints.setTextFieldMaxLength(txtDisciplina, 40);
	}
}