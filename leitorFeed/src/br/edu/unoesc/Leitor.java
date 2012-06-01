package br.edu.unoesc;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class Leitor {

	private static final String TITULO = "title";
	private static final String DESCRICAO = "description";
	private final URL url;

	public Leitor(String feedUrl) {
		try {
			this.url = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public void lerFeed() throws XMLStreamException, IOException {
		// Criando uma fabrica XMLInputFactory
		XMLInputFactory fabrica = XMLInputFactory.newInstance();
		// Setando um novo leitor de Evento eventReader
		InputStream entrada = url.openStream();
		XMLEventReader leitorEvento = fabrica.createXMLEventReader(entrada);
		// Lendo o documento XML
		while (leitorEvento.hasNext()) {
			XMLEvent evento = leitorEvento.nextEvent();
			if (evento.isStartElement()) {
				if (evento.asStartElement().getName().getLocalPart() == TITULO) {
					evento = leitorEvento.nextEvent();
					String titulo = evento.asCharacters().getData();
					System.out.println("\n\nTitulo: " + titulo);
					continue;
				}
				if (evento.asStartElement().getName().getLocalPart() == DESCRICAO) {
					evento = leitorEvento.nextEvent();
					String descricao = evento.asCharacters().getData();
					System.out.println("Descricao: " + descricao);
					continue;
				}
			} 
		}
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws XMLStreamException 
	 */
	public static void main(String[] args) throws XMLStreamException, IOException {
		Leitor parser = new Leitor("http://agosti.blog.br/feed");
		parser.lerFeed();
	}

}
