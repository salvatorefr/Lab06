package it.polito.tdp.meteo;

import java.net.URL;
import java.time.Month;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.bean.Citta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class MeteoController {
Model model;
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ChoiceBox<Month> boxMese;

	@FXML
	private Button btnCalcola;

	@FXML
	private Button btnUmidita;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCalcolaSequenza(ActionEvent event) {
		this.txtResult.clear();
		List<Citta> sequenza=model.trovaSequenza(this.boxMese.getValue().getValue());
		this.txtResult.appendText("soluzione migliore:\n");
	   for (Citta c:sequenza) {
		   this.txtResult.appendText(c.getNome()+"\n");
	   }

	}

	@FXML
	void doCalcolaUmidita(ActionEvent event) {
		this.txtResult.clear();
		Month mese= Month.of(1);
		if (this.boxMese.getValue()!=null) {
			mese=this.boxMese.getValue();
		}	
		this.txtResult.appendText(model.getUmiditaMedia(mese.getValue()));
			
			
	
		

	}

	@FXML
	void initialize() {
		assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Meteo.fxml'.";
		for (int mese=1;mese<=12;mese++) {
			this.boxMese.getItems().add(Month.of(mese));
		}
		
	}

	public void setModel(Model model) {
		this.model=model;
		
		
	}

}
