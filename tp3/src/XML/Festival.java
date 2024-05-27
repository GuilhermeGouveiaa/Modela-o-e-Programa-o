package XML;

import java.util.ArrayList;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Classe que representa um Evento do tipo Festival.
 * 
 * @version 1.0 
 * @author Docentes da Disciplina de Modelação e Programação, LEIM, Instituto Superior de Engenharia de Lisboa
 *
 */
public class Festival extends Evento {

	private static final int MAX_EVENTOS = 20;
	
	private Evento[] eventos = new Evento[MAX_EVENTOS];

	public Festival(String nome) {
		super(nome);
	}
	
	/**
	 * Devolve todos os bilhetes existentes no Festival (somando e devolvendo todos os bilhetes dos seus Eventos).
	 * @return o número de bilhetes existentes no Festival.
	 */
	public int getNumBilhetes() {
		int n = 0;

		for (Evento envento : eventos) {

			if (envento == null){
				continue;
			}
			n += envento.getNumBilhetes();

		}
		return n;
	}

	/**
	 * Retorna o número de actuaçõoes de um determinado artista.
	 * @param artista o nome do artista.
	 * @Override 
	 */
	@Override
	public int numActuacoes(String artista) {
		int n=0;

		for (Evento envento : eventos) {
			if (envento == null)
				continue;

			if (envento instanceof Espetaculo){
				for (String pessoa : envento.getArtistas()) {
					if (pessoa.equals(artista))
						n++;
				}
			}
			if (envento instanceof Festival){
				n += envento.numActuacoes(artista);
			}
		}
		return n;
	}
	
	/**
	 *  Devolve uma string representativa do Festival.
	 *  Nota: Ver o ficheiro OutputPretendido/OutputPretendido.txt
	 */
	public String toString() {
		String s = super.toString();

		for (Evento evento : eventos) {

			if (evento == null){continue;}

			s = s + "\n";
			if (evento instanceof Espetaculo) {
				s = s + evento;
			}
			if (evento instanceof Festival) {
				s = s + evento;
			}
		}
		return s;
	}
	
	/**
	 * Devolve um array contendo todos, de forma não repetida, os nomes de todos os artistas quer irão 
	 * actuar no Festival.
	 * @return um array contendo os nomes dos artistas. 
	 */
	public String[] getArtistas() {
		ArrayList<String> artistas = new ArrayList<>();

		for (Evento evento : eventos) {
			if (evento == null){
				continue;
			}
			for (String nome : evento.getArtistas()) {
				if (!artistas.contains(nome)){
					artistas.add(nome);
				}
			}
		}
		String[] artistasFestival = new String[artistas.size()];
		for (int i = 0; i < artistas.size(); i++) {
			artistasFestival[i] = artistas.get(i);
		}
		return artistasFestival;
	}
	
	/**
	 * Retorna a profundidade maxima da "árvore" que contém Festivais dentro de Festivais. O próprio Festival não conta.
	 * @return a profundidade máxima.
	 */
	public int getDeepFestival() {
		int profundidade = 0, n1;
		for (Evento evento : this.eventos) {
			if (evento==null || evento instanceof Espetaculo)
				continue;

			if (evento instanceof Festival){
				n1 = 1 + ((Festival)evento).getDeepFestival();
				if (profundidade < n1){
					profundidade = n1;
				}
			}
		}
		return profundidade;
	}
	
	/**
	 * Adiciona um novo Evento ao Festival, caso para nenhum dos artistas do novo evento se verifique que o seu número de atuações no
	 * novo evento (a adicionar) supere em mais de duas o número de atuações no festival corrente.
	 * @param evento
	 * @return verdadeiro, se o novo Evento foi adicionado.
	 */
	public boolean addEvento(Evento evento) {

		if (this.equals(evento)) {return false;}

		for (Evento eventoFetival : eventos) {
			if (eventoFetival == null ||
					eventoFetival instanceof Espetaculo){continue;}

			if (eventoFetival.equals(evento)) {return false;}
		}

		String[] artistas;
		int n1, n2 = 0;
		boolean add = true;
		if (evento == null){
			return false;
		}

		artistas = evento.getArtistas();

		for (String artista : artistas) {

			n1 = this.numActuacoes(artista);

			evento.numActuacoes(artista);

			if (n1 + 2 < n2){
				add = false;
			}
		}
		if (add){
			for (int i = 0; i < eventos.length; i++) {
				Evento eventoFetival = eventos[i];
				if (eventoFetival == null) {
					eventos[i] = evento;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Remove um evento em qualquer profundidade do Festival corrente.
	 * @param nomeEvento nome do Evento a remover.
	 * @return verdadeiro, se o Evento foi removido.
	 */
	public boolean delEvento(String nomeEvento) {
		boolean del = false;

		for (Evento evento : eventos) {
			if (evento instanceof Espetaculo){
				if ((evento.getNome()).equals(nomeEvento)){
					evento = null;
					del = true;
				}
			}
			if (evento instanceof Festival){
				((Festival) evento).delEvento(nomeEvento);
			}
		}
		return del;
	}
	
	/**
	 * Imprime na consola informações sobre o Festival.
	 * Nota: Ver o output pretendido em OutputPretendido/OutputPretendido.txt.
	 * @param prefix o prefixo para identar o Festival de acordo com a sua profundidade.
	 */
	public void print(String prefix) {

		for (Evento evento : eventos) {
			if (evento == null) {
				continue;
			}
			System.out.println(prefix + evento);
		}
	}
	
	/**
	 * Constroi um novo Festival a partir de um nó contendo as infomações lidas do documento XML. 
	 * @param nNode o nó associado ao Festival
	 * @return um novo Festival
	 */
	public static Festival build(Node nNode) { 

		Element efestival = (Element) nNode;

		String nome = efestival.getElementsByTagName("Nome").item(0).getTextContent();
		Festival festival = new Festival(nome);

		NodeList listaEventos = efestival.getElementsByTagName("Eventos").item(0).getChildNodes();

		for(int i = 0; i < listaEventos.getLength(); i++) {

			Node nodeEvento = listaEventos.item(i);
			festival.addEvento(Evento.build(nodeEvento));
		}
		return festival;
	}

	/**
	 * Cria um novo Elemento quer irá representar, no documento XML, o Festival associado ao Festival corrente.
	 * @param doc o Documento que irá ser usado para gerar o novo Element.
	 */
	public Element createElement(Document doc) {

		Element eFestival = doc.createElement("Festival");

		Element eNome = doc.createElement("Nome");
		eNome.appendChild(doc.createTextNode(this.getNome()));
		eFestival.appendChild(eNome);

		Element eEventos = doc.createElement("Eventos");

		for (int i = 0; i < MAX_EVENTOS; i++) {
			Evento evento = eventos[i];

			if (evento != null) {

				Element eEvento = evento.createElement(doc);
				eEventos.appendChild(eEvento);
			}
		}
		eFestival.appendChild(eEventos);

		return eFestival;
	}
	
	/**
	 * Método main que gera no output o que está no ficheiro OutputPretendido/OutputPretendido.txt e cria um novo
	 * documento XML/Eventos.xml, com a mesma estrutura que o documento OutputPretendido/Eventos.xml.
	 * @param args
	 */
	public static void main(String[] args) {
		//
	      try {

	    	 File inputFile = new File("src/XML/BaseDados.xml");

	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	         
	     	 XPath xpath = XPathFactory.newInstance().newXPath();
			 String expression = "/BaseDados/Eventos/*";
			 NodeList nList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
	        
	         Node nNode = nList.item(0);
	         Evento evento = Evento.build(nNode);     
	         if(evento != null) evento.print("");

	
	    	 Festival fNovo = new Festival("Bollywood Music Festival"); 
			
			 Espetaculo e1_1 = new Espetaculo("Suna Hai", "Sines", 500);
			 e1_1.addArtista("Suna Hai");
			 fNovo.addEvento(e1_1);
			
			 Espetaculo e1_2 = new Espetaculo("Rait Zara", "Sines", 400);
			 e1_2.addArtista("Rait Zara");
			 fNovo.addEvento(e1_2);
		
			 if(evento instanceof Festival) {
				
				 Festival festival = (Festival)evento;
				 festival.addEvento(fNovo);
				
				 // root elements
				 Document newDoc = dBuilder.newDocument();
				 Element rootElement = newDoc.createElement("Eventos");
				 
				 rootElement.appendChild(festival.createElement(newDoc));
				 
				 newDoc.appendChild(rootElement);
				 	
				 FileOutputStream output = new FileOutputStream("src/XML/Eventos.xml");
		         writeXml(newDoc, output);

			 }
	        
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		
	}

	/**
	 * Escreve, para o OutputStream, o documento doc.
	 * @param doc o documento contendo os Elementos a gravar on ficheiro output
	 * @param output o ficheiro de saída.
	 */
	private static void writeXml(Document doc, OutputStream output) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        
        // pretty print XML
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);
    }
}








