package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
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
import modelos.entidades.Aluno;
import modelos.servicos.ServicoAluno;

public class ControleFormAluno implements Initializable {
	
	private Aluno entidade;
	
	private ServicoAluno servico;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtResponsavel;
	
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
			Utils.currentStage(event).close();
		}
		catch (DbException e) {
			Alerts.showAlert("Erro ao salvar objeto", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private Aluno getForm() {
		Aluno obj = new Aluno();
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setNome(txtNome.getText());
		obj.setResponsavel(txtResponsavel.getText());
		obj.setTelefone(Utils.tryParseToInt(txtTelefone.getText()));
		obj.setEndereco(txtEndereco.getText());
		obj.setEmail(txtEmail.getText());
		
		return obj;
		}

	@FXML
	private void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	public void setAluno (Aluno entidade) {
		this.entidade = entidade;
	}
	
	public void setServicoAluno (ServicoAluno servico) {
		this.servico = servico;
	}
	
	public void updateForm() {
		if (entidade == null) {
			throw new IllegalStateException("Entidade vazia");
		}
		txtId.setText(String.valueOf(entidade.getId()));
		txtNome.setText(entidade.getNome());
		txtResponsavel.setText(entidade.getResponsavel());
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
		Constraints.setTextFieldMaxLength(txtResponsavel, 40);
		Constraints.setTextFieldMaxLength(txtEndereco, 50);
		Constraints.setTextFieldMaxLength(txtEmail, 40);
	}
}