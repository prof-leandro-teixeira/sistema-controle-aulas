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
import modelos.entidades.Disciplina;
import modelos.servicos.ServicoDisciplina;

public class CadastroDisciplina implements Initializable {
	
	private Disciplina entidade;
	private ServicoDisciplina servico;
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtArea;
	
	@FXML
	private Label labelErroNome;
	
	@FXML
	private Label labelErroArea;
	
	@FXML
	private Button btSalvaDisciplina;
	
	@FXML
	private Button btCancelaDisciplina;
	
	public void setDisciplina (Disciplina entidade) {
		this.entidade = entidade;
	}
	
	public void setServicoDisciplina (ServicoDisciplina servico) {
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

	private Disciplina getForm() {
		Disciplina obj = new Disciplina();
		
		ValidationException exception = new ValidationException("Erro de Validação");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if (txtNome.getText()== null || txtNome.getText().trim().equals("")){
			exception.addError("nome", "Campo não pode ficar vazio");
		}
		obj.setNome(txtNome.getText());
				
		if (txtArea.getText()== null || txtArea.getText().trim().equals("")){
			exception.addError("area", "Campo não pode ficar vazio");
		}
		obj.setArea(txtArea.getText());
		
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
		Constraints.setTextFieldMaxLength(txtArea, 50);
	}
	public void updateForm() {
		if (entidade == null) {
			throw new IllegalStateException("Entidade vazia");
		}
		txtId.setText(String.valueOf(entidade.getId()));
		txtNome.setText(entidade.getNome());
		txtArea.setText(entidade.getArea());
	}
	
	private void setErrorMessagens(Map<String, String> errors) {
		Set<String> campos = errors.keySet();
		
		if (campos.contains("nome")) {
			labelErroNome.setText(errors.get("nome"));
		}
		if (campos.contains("area")) {
			labelErroArea.setText(errors.get("area"));
		}
	}
}
