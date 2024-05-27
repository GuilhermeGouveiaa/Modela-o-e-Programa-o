package XML;

import java.util.Arrays;

import org.w3c.dom.*;

/**
 * Classe que representa um Evento do tipo Espetaculo.
 * 
 * @version 1.0 
 * @author Docentes da Disciplina de Modelação e Programação, LEIM, Instituto Superior de Engenharia de Lisboa
 *
 */
public class Espetaculo extends Evento {
	
	private static final int MAX_ARTISTAS = 10;
	private String[] artistas = new String[MAX_ARTISTAS];
	private int numBilhetes;
	private String localidade;
			
	/**
	 * Constroi um novo Espetaculo
	 * @param nome nome do Espetaculo
	 * @param localidade a localidade do Espetaculo
	 * @param numBilhetes o número de bilhetes disponíveis
	 */
	public Espetaculo(String nome, String localidade, int numBilhetes) {
		super(nome);

		this.localidade = localidade;

		if (numBilhetes > 0)
			this.numBilhetes = numBilhetes;
	}

	/**
	 * Informa se um determinado artista irá actuar no Espetaculo. 
	 * @return 1, se actuar e 0 caso contrário.
	 * @Override
	 */
	@Override
	public int numActuacoes(String artista) {
		for (String pessoa : artistas) {
			if (pessoa == null){
				continue;
			}
			if (pessoa.equals(artista)){
				return 1;
			}
		}
		return 0;
	}
	
	/**
	 * Permite adicionar un novo artista ao Espetaculo se o artista ainda 
	 * não tem actuações e se o número máximo de artistas ainda não foi ultrapassado.
	 * @param artista representa o novo artista
	 * @return verdadeiro, caso o artista tenha sido adicionado e falso caso contrário.
	 */
	public boolean addArtista(String artista) {
		for (int i = 0; i < artistas.length; i++) {
			String pessoa = artistas[i];
			if (pessoa == null){
				artistas[i] = artista;
				return true;
			}
			if (pessoa.equals(artista)){
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Devolve o número de bilhetes.
	 * @Override
	 */
	@Override
	public int getNumBilhetes() {
		return this.numBilhetes;
	}

	/**
	 * Devolve uma cópia dos artistas que actuam no Espetaculo.
	 * @Override
	 */
	@Override
	public String[] getArtistas() {
		int n = artistas.length;
		for (int i = 0; i < artistas.length; i++) {
			String pessoa = artistas[i];
			if (pessoa == null) {
				n = i;
				break;
			}
		}
		return Arrays.copyOfRange(this.artistas, 0, n);
	}

	/**
	 * Devolve a localidade do Espetaculo
	 * @return a localidade.
	 */
	public String getLocalidade() { 
		return this.localidade;
	}
	
	/**
	 * Devolve uma String a representar o Espetaculo.
	 * Nota: Ver o ficheiro OutputPretendido.txt
     * @Override
	 */
	@Override
	public String toString() {
		return super.toString() + " em " + localidade;
	}
	
	
	/**
	 * Constroi um novo Evento a partir do objecto Node passado como parâmetro.
	 * @param nNode
	 * @return um novo Evento
	 */
	public static Evento build(Node nNode) {

		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eEspectaculo = (Element) nNode;

			String nome = eEspectaculo.getElementsByTagName("Nome").item(0).getTextContent();

			NodeList artistas  =  eEspectaculo.getElementsByTagName("Artistas").item(0).getChildNodes();

			String numBilhetes =  eEspectaculo.getAttribute("numBilhetes");

			String localidade  =  eEspectaculo.getElementsByTagName("Localidade").item(0).getTextContent();

			Espetaculo espetaculo = new Espetaculo(nome, localidade, Integer.parseInt(numBilhetes));

			for(int i = 0; i < artistas.getLength(); i++){

				Node artista = artistas.item(i);

				if (artista.getNodeName().equals("Artista")){

					String nome_artista = artista.getTextContent();
					espetaculo.addArtista(nome_artista);
				}
			}
			return espetaculo;
		}
		return null;
	}
	
	/**
	 *  Constroi um novo Element a partir do Espectaculo actual.
	 *  @param doc - o documento que irá gerar o novo Element
	 */
	public Element createElement(Document doc) {

		Element eEspectaculo = doc.createElement("Espetaculo");
		eEspectaculo.setAttribute("numBilhetes",Integer.toString(getNumBilhetes()));

		Element eNome = doc.createElement("Nome");
		eNome.appendChild(doc.createTextNode(this.getNome()));
		eEspectaculo.appendChild(eNome);

		Element eArtistas = doc.createElement("Artistas");

		for (int i = 0; i < MAX_ARTISTAS; i++){
			String artista = artistas[i];

			if (artista != null){

				Element eArtista = doc.createElement("Artista");

				eArtista.appendChild(doc.createTextNode(artista));
				eArtistas.appendChild(eArtista);
			}
		}
		eEspectaculo.appendChild(eArtistas);

		Element eLocalidade = doc.createElement("Localidade");
		eLocalidade.appendChild(doc.createTextNode(getLocalidade()));
		eEspectaculo.appendChild(eLocalidade);

		return eEspectaculo;
	}
}
