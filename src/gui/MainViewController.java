package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

public class MainViewController implements Initializable{
	
	//AGENDA
	@FXML
	private MenuItem menuItemAgenda;
	
	@FXML
	public void onMenuItemAgendaAction() {
		}
	
	//ALUNO
	@FXML
	private MenuItem menuItemAluno;
	
	@FXML
	public void onMenuItemAlunoAction() {
		loadView("/gui/ListaAlunos.fxml");
	}
	
	//PROFESSOR
	@FXML
	private MenuItem menuItemProfessor;
	
	@FXML
	public void onMenuItemProfessorAction() {
		loadView("/gui/ListaProfessores.fxml");
	}
	
	//DISCIPLINA
	@FXML
	private MenuItem menuItemDisciplina;
	
	@FXML
	public void onMenuItemDisciplinaAction() {
		loadView("/gui/ListaDisciplinas.fxml");
	}

	//CONTATO
	@FXML
	private MenuItem menuItemContato;
	
	@FXML
	public void onMenuItemContatoAction() {
		loadView("/gui/Contato.fxml");
	}
	private void loadView(String absoluteName) {
		try {	
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
	}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro no carregamento da página", e.getMessage(), AlertType.ERROR);
		}
	}
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		}
}
