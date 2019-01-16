package com.emate.shop.business.vo.ofvo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cards")
public class Cards {


	public Cards() {
		super();
	}

	public Cards(List<Card> cards) {
		super();
		this.cards = cards;
	}

	private List<Card> cards;
	
	@XmlElement(name = "card")
	public List<Card> getCards() {
		return cards;
	}
	
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
	@Override
	public String toString() {
		return "Cards [cards=" + cards + "]";
	}
}
