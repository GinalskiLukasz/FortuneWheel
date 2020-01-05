package model;


import java.io.File;

import org.jsoup.nodes.Element;

/**
 * Klasa odpowiedzialna za dekodowanie wiadomoœci przesy³anych przy pomocy chatu
  */
public class ChatDecoder {
	

	
public int UID;	
public int senderUID;
private String senderName;



/**
 * @param uID numeryczny identyfikator u¿ytkownika 
 * @param senderUID numeryczny identyfikator drugiego u¿ytkownika 
 * @param senderName nazwa drugiego u¿ytkownika
 */
public ChatDecoder(int uID, int senderUID, String senderName) {
	super();
	UID = uID;
	this.senderUID = senderUID;
	this.senderName = senderName;
}

/**
 * Metoda dekoduje przes³any strumieñ danych 
 * @param msg strumieñ danych 
 * @return zwraca obiekt klasy Element
 */
public String decodeUID(String msg) {
		
		
		msg = msg.substring(3);
		String[] param = msg.split("\t");
		this.senderUID = Integer.parseInt(param[0]);
		this.senderName = param[1];
		System.out.println("decode: " + msg.substring(param[0].length() + param[1].length() + 2));
		return msg.substring(param[0].length() + param[1].length() + 2);
	}

	public Element toHTML(String message) {
		System.out.println("toHTML:" + message);
		String msgClass = (UID == senderUID) ? "request" : "response";
		Element wrapper = new Element("li").attr("class", msgClass);

		
		
		Element message_div = new Element("div").attr("class", "message").appendTo(wrapper);
		new Element("div").attr("class", "content").append(message).appendTo(message_div);
		return wrapper;
	}	
	
}
