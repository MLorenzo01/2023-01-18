/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.nyc;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import it.polito.tdp.nyc.model.Location;
import it.polito.tdp.nyc.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbProvider"
    private ComboBox<String> cmbProvider; // Value injected by FXMLLoader

    @FXML // fx:id="txtDistanza"
    private TextField txtDistanza; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtStringa"
    private TextField txtStringa; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtTarget"
    private ComboBox<Location> txtTarget; // Value injected by FXMLLoader

    @FXML
    void doAnalisiGrafo(ActionEvent event) {
    	txtResult.clear();
    	ArrayList<String> lista = model.getMax();
    	for(String s: lista) {
    		txtResult.appendText(s);
    	}
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	String s = txtStringa.getText();
    	Location l = txtTarget.getValue();
    	ArrayList<Location> percorso= model.trovaPercorso(s, l);
    	txtResult.appendText("\n");
    	if(percorso.isEmpty())
    		txtResult.appendText("\n Non esiste un percorso");
    	else {
    		for(Location loc: percorso)
    		txtResult.appendText("\n" + loc.toString());
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	if(txtDistanza.getText() == "") {
    		txtResult.setText("Inserire la distanza");
    		return;
    	}
    	double txt;
    	try {
	    	txt = Double.parseDouble(txtDistanza.getText());
	    	if(cmbProvider.getValue() == null) {
	    		txtResult.setText("Selezionare un provider");
	    		return;
	    	}
	    	model.CreaGrafo(cmbProvider.getValue(), txt);
	    	txtTarget.getItems().addAll(model.getLocation());


    	}catch(NumberFormatException e) {
    		txtResult.setText("Distanza inserita in modo non corretto");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProvider != null : "fx:id=\"cmbProvider\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDistanza != null : "fx:id=\"txtDistanza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStringa != null : "fx:id=\"txtStringa\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    public void setModel(Model model) {
    	this.model = model;
    	cmbProvider.getItems().addAll(model.getProvider());
    }
}
