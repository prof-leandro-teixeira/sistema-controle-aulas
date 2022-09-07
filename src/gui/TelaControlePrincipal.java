package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import modelos.servicos.ServicoAluno;
import modelos.servicos.ServicoAula;
import modelos.servicos.ServicoDisciplina;
import modelos.servicos.ServicoMelhoria;
import modelos.servicos.ServicoProfessor;

public class TelaControlePrincipal implements Initializable{

	@FXML
	private MenuItem menuItemCadastroAula;
		
	@FXML
	private MenuItem menuItemCadastroDisciplina;
	
	@FXML
	private MenuItem menuItemCadastroProfessor;
	
	@FXML
	private MenuItem menuItemCadastroAluno;
	
	@FXML
	private MenuItem menuItemCadastroMelhoria;
	
	@FXML
	private MenuItem menuItemContato;
	
	@FXML
	public void onMenuItemCadastroAlunoAction() {
		loadView("/gui/TelaAlunos.fxml", (TelaControleAluno controle)->{
			controle.setServicoAluno(new ServicoAluno());
			controle.updateTableView();
			});
	}
	
	@FXML
	public void onMenuItemAulaAction() {
		loadView("/gui/TelaAulas.fxml", (TelaControleAula controle)->{
			controle.setServicoAula(new ServicoAula());
			controle.updateTableView();
			});
	}	
		
	@FXML
	public void onMenuItemCadastroDisciplinaAction() {
		loadView("/gui/TelaDisciplinas.fxml", (TelaControleDisciplina controle)->{
			controle.setServicoDisciplina(new ServicoDisciplina());
			controle.updateTableView();
			});
	}
	
	@FXML
	public void onMenuItemCadastroMelhoriaAction() {
		loadView("/gui/TelaMelhorias.fxml", (TelaControleMelhoria controle)->{
			controle.setServicoMelhoria(new ServicoMelhoria());
			controle.updateTableView();
			});
	}
		
	@FXML
	public void onMenuItemCadastroProfessorAction() {
		loadView("/gui/TelaProfessores.fxml", (TelaControleProfessor controle)->{
			controle.setServicoProfessor(new ServicoProfessor());
			controle.updateTableView();
			});
	}
	
	@FXML
	public void onMenuItemContatoAction() {
		loadView("/gui/TelaContato.fxml", x -> {});
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {	
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controle = loader.getController();
			initializingAction.accept(controle);
		}
			catch (IOException e) {
				Alerts.showAlert("IO Exception", "Erro no carregamento da página", e.getMessage(), AlertType.ERROR);
			}
	}
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		}
}