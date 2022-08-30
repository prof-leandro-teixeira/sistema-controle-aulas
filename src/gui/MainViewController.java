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
import modelos.servicos.ServicoProfessor;

public class MainViewController implements Initializable{
	
	
	//AULA
	@FXML
	private MenuItem menuItemAula;
	@FXML
	public void onMenuItemAulaAction() {
		System.out.println("ok");
			/*loadView("/gui/ListaAulas.fxml", (ControleListaAula controle)->{
				controle.setServicoAula(new ServicoAula());
				controle.updateTableView();
				});
				*/
	}
	
	//ALUNO
	@FXML
	private MenuItem menuItemAluno;
	@FXML
	public void onMenuItemAlunoAction() {
		loadView("/gui/ListaAlunos.fxml", (ControleListaAluno controle)->{
			controle.setServicoAluno(new ServicoAluno());
			controle.updateTableView();
			});
	}
	
	//CONTATO
	@FXML
	private MenuItem menuItemContato;
	@FXML
	public void onMenuItemContatoAction() {
		loadView("/gui/Contato.fxml", x -> {});
	}

	//DISCIPLINA
	@FXML
	private MenuItem menuItemDisciplina;
	@FXML
	public void onMenuItemDisciplinaAction() {
		loadView("/gui/ListaDisciplinas.fxml", (ControleListaDisciplina controle)->{
			controle.setServicoDisciplina(new ServicoDisciplina());
			controle.updateTableView();
			});
	}
	
	//PROFESSOR
	@FXML
	private MenuItem menuItemProfessor;
	@FXML
	public void onMenuItemProfessorAction() {
		loadView("/gui/ListaProfessores.fxml", (ControleListaProfessor controle)->{
			controle.setServicoProfessor (new ServicoProfessor());
			controle.updateTableView();
			});
	}
	
	//função
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