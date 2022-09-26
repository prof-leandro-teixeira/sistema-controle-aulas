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
import modelos.entidades.Melhoria;
import modelos.servicos.ServicoMelhoria;

public class CadastroMelhoria implements Initializable {
	
	private Melhoria entidade;
	
	private ServicoMelhoria servico;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtTipo;
	
	@FXML
	private TextField txtDescricao;
	
	@FXML
	private Label labelErroTipo;
	
	@FXML
	private Label labelErroDescricao;
	
	@FXML
	private Button btSalvaMelhoria;
	
	@FXML
	private Button btCancelaMelhoria;
	
	public void setMelhoria (Melhoria entidade) {
		this.entidade = entidade;
	}
	
	public void setServicoMelhoria (ServicoMelhoria servico) {
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

	private Melhoria getForm() {
		Melhoria obj = new Melhoria();
		
		ValidationException exception = new ValidationException("Erro de Validação");
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if (txtTipo.getText()== null || txtTipo.getText().trim().equals("")){
			exception.addError("Tipo", "Campo não pode ficar vazio");
		}
		obj.setTipo(txtTipo.getText().toUpperCase());
		
		if (txtDescricao.getText()== null || txtDescricao.getText().trim().equals("")){
			exception.addError("Descricao", "Campo não pode ficar vazio");
		}
		obj.setDescricao(txtDescricao.getText().toUpperCase());
		
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
		Constraints.setTextFieldMaxLength(txtTipo, 30);
		Constraints.setTextFieldMaxLength(txtDescricao, 60);
	}

	public void updateForm() {
		if (entidade == null) {
			throw new IllegalStateException("Entidade vazia");
		}
		txtId.setText(String.valueOf(entidade.getId()));
		txtTipo.setText(entidade.getTipo());
		txtDescricao.setText(entidade.getDescricao());
	}
	
	private void setErrorMessagens(Map<String, String> errors) {
		Set<String> campos = errors.keySet();
		
		if (campos.contains("Tipo")) {
			labelErroTipo.setText(errors.get("Tipo"));
		}
		if (campos.contains("Descricao")) {
			labelErroDescricao.setText(errors.get("Descricao"));
		}
	}
}
