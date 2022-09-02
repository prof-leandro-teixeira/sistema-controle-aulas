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
import modelos.servicos.ServicoDisciplina;
import modelos.servicos.ServicoProfessor;

public class MainViewController implements Initializable{
	
	/*
	//AULA
	@FXML
	private MenuItem menuItemAula;
	@FXML
	public void onMenuItemAulaAction() {
		loadView("/gui/CadAulas.fxml", (ControleCadAula controle)->{
			controle.setServicoAula(new ServicoAula());
			controle.updateTableView();
			});
	}	
	
	@FXML
	private MenuItem menuItemListaAula;
	
	@FXML
	public void onMenuItemListaAulaAction() {
		loadViewLista("/gui/ListaAulas.fxml", (ControleListaAula controle)->{
			controle.setServicoAula(new ServicoAula());
			controle.updateTableView();
			});	
	}*/
	
	@FXML
	private MenuItem menuItemCadDisciplina;
	
	@FXML
	private MenuItem menuItemCadProfessor;
	
	@FXML
	private MenuItem menuItemCadAluno;
		
	@FXML
	public void onMenuItemCadDisciplinaAction() {
		loadView("/gui/CadDisciplinas.fxml", (ControleCadDisciplina controle)->{
			controle.setServicoDisciplina(new ServicoDisciplina());
			controle.updateTableView();
			});
	}
		
	@FXML
	public void onMenuItemCadProfessorAction() {
		loadView("/gui/CadProfessores.fxml", (ControleCadProfessor controle)->{
			controle.setServicoProfessor(new ServicoProfessor());
			controle.updateTableView();
			});
	}
	
	@FXML
	public void onMenuItemCadAlunoAction() {
		loadView("/gui/CadAlunos.fxml", (ControleCadAluno controle)->{
			controle.setServicoAluno(new ServicoAluno());
			controle.updateTableView();
			});
	}
	
	@FXML
	private MenuItem menuItemContato;
	@FXML
	public void onMenuItemContatoAction() {
		loadView("/gui/Contato.fxml", x -> {});
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