package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;

import it.polito.tdp.meteo.db.MeteoDAO;


public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private List<Citta> soluzioneCandidata;
	private List <Citta> tutteLeCitta;
	private double costo=0.0;
	private int soluzioni=0;
	
	public Model() {
		MeteoDAO dao=new MeteoDAO();
		tutteLeCitta= dao.getTutteCitta();
		}


	public List<Citta> trovaSequenza(int mese) {
		//carico i rilevamenti del mese nella città
		MeteoDAO dao= new MeteoDAO();
		for (Citta c: tutteLeCitta) {
		List<Rilevamento> rilevamentoCitta=	dao.getAllRilevamentiLocalitaMese(mese, c.getNome());
		c.setRilevamenti(rilevamentoCitta);
			}
		
		//avvio della funzione ricorsiva
		
		 cerca(new ArrayList<Citta>(), 0);
		
		 return soluzioneCandidata;
		}
		
	
				
		

	private void cerca(ArrayList<Citta> parziale, int livello) {
	
		//condizione finale
		if(parziale.size()==NUMERO_GIORNI_TOTALI) 
		{
			
			if (soluzioni==0) 
			{
				soluzioneCandidata=new ArrayList<Citta>();
				soluzioneCandidata.addAll(parziale);
					
				costo= calcolaCosto(parziale);
				soluzioni++;
				
			return;
				}
				
			if (calcolaCosto(parziale)<costo)
			{
				soluzioneCandidata=new ArrayList<Citta>();
				soluzioneCandidata.addAll(parziale);
				costo=calcolaCosto(soluzioneCandidata);
				System.out.println(soluzioneCandidata+" costo "+costo);
				return;
				}
			 return;
		}	
		
		 
			
			for(Citta c:tutteLeCitta) {
				
		
		    if (aggiungere(parziale,c)) {
		    	
		 
				
				parziale.add(c);
				cerca(parziale,livello+1);
				parziale.remove(parziale.size()-1);
			
				}   }
					
					
					
		 }
					
					
					
			
			
			
			
			
			
			
			
			
			
			
		
			
			
			
		
		



	

	
	
	

	private boolean aggiungere(ArrayList<Citta> parziale, Citta c) {
				
		//se è il primo elemento lo aggiungo sempre;
		if(parziale.size()==0) 
		{
			return true;}
		
		
		//cerco le condizioni in cui aggiungo l'elemento
		
		if (parziale.size()==1||parziale.size()==2)
			{
					if (!parziale.get(parziale.size()-1).equals(c)) {
						return false;
						}
					
				}
		if(parziale.size()>3) {

		
			
		if (!parziale.get(parziale.size()-1).equals(c))  //se il precedente è diverso, devono essere uguali i tre precedenti
		{
			//se il precedente è diverso dal suo precedente da falso
			if (!parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-2)))
				{
				return false;
				}
			//se il precedende del precedente è diverso dal suo precedente ritorna falso
			if (!parziale.get(parziale.size()-2).equals(parziale.get(parziale.size()-3)))
				{
							return false;
				}
		}
		else {	
			
			if (conta(parziale,c)>=NUMERO_GIORNI_CITTA_MAX) {
			return false;
					} 
			}
		
		
		
		}
		return true;
			
		
		
		}
		
		
		
		
		
		
		
		
		
		
		
		
		

	


	private int conta(ArrayList<Citta> parziale, Citta c) {
	int numeroCitta=0;
	for(Citta cit:parziale) {
		if (c.equals(cit)) {
			numeroCitta++;
		}
	}
	return numeroCitta;
	}


	private double calcolaCosto(List<Citta> parziale) {
	int giorno=0;
	double costo=0.0;
	for (Citta c:parziale) {
		giorno++;
		if (giorno==1) {
			costo+=c.getRilevamenti().get(giorno-1).getUmidita();
		}
		if (giorno>1) {
			if(c.equals(parziale.get(giorno-2))) {
				costo+=100;
				costo+=c.getRilevamenti().get(giorno-1).getUmidita();
			}
			else {
				costo+=c.getRilevamenti().get(giorno-1).getUmidita();
			}
		}
		
	}

		return costo;
	}


	

	
	
	
	
	
	
	
	public String getUmiditaMedia(int mese) {
	MeteoDAO md= new MeteoDAO();
	String cittaMedia= md.getAllMedia(mese);
		return cittaMedia;
	}
		
		

     }
	
	

