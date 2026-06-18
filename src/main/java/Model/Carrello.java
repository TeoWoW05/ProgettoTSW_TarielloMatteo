package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Carrello implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public Carrello() {
		this.items = new ArrayList<>();
	}
	
	public void aggiungiProdotto(Prodotto prodotto, int quantità) {
		for(CarrelloItem item : items) {
			if(item.getProdotto().getCodiceProdotto() == prodotto.getCodiceProdotto()) {
				item.setQuantita(item.getQuantita() + quantità);
			}
		}
		
		items.add(new CarrelloItem(prodotto,quantità));
	}
	
	public void rimuoviProdotto(int codiceProdotto) {
		for(int i = 0; i<items.size(); i++) {
			if(items.get(i).getProdotto().getCodiceProdotto() == codiceProdotto) {
				items.remove(i);
				return;
			}
		}
	}
	
	public void aggiornaQuantita(int codiceProdotto,int nuovaQuantita) {
		for(CarrelloItem item : items) {
			if(item.getProdotto().getCodiceProdotto() == codiceProdotto) {
				if(nuovaQuantita <= 0) {
					rimuoviProdotto(codiceProdotto);
				}else {
					item.setQuantita(nuovaQuantita);
				}
				return;
			}
		}
	}
	
	public ArrayList<CarrelloItem> getItems(){
		return items;
	}
	
	public int getNumeroProdotti() {
		int count = 0;
		for(CarrelloItem item : items) {
			count += item.getQuantita();
		}
		return count;
	}
	
	public void svuota() {
		items.clear();
	}
	
	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	
	private ArrayList<CarrelloItem> items;
}
