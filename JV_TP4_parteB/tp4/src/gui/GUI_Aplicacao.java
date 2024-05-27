package gui;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import tp4.Comparador;
import tp4.EquipaFutebol;
import tp4.Futebol;
import tp4.Liga;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import tp4.Jogo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import java.awt.Color;

public class GUI_Aplicacao extends JFrame {

	private JPanel contentPane;
	private JComboBox comboBox;
	Futebol futebol = new Futebol(new ArrayList<>());
	private JTextField textField;
	private JTextField textField_1;
	private ArrayList<EquipaFutebol> equipas = new ArrayList<>();
	EquipaFutebol equipaA;
	EquipaFutebol equipaB;
	Jogo jogoGuardado;
	private JTextField textField_gol_casa;
	private JTextField textField_gol_fora;
	private JTextField textField_date;
	private JTextField textField_2;
	private JTable table;
	private JTable table_1;
	
	
	public void build() {
	    try {
	        // Ler o arquivo XML e criar um documento DOM
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document document = builder.parse(new File("src/XML/dataBase.xml"));

	        // Obter a lista de elementos "liga" do documento
	        NodeList ligaNodes = document.getElementsByTagName("liga");
	        for (int i = 0; i < ligaNodes.getLength(); i++) {
	            Element ligaElement = (Element) ligaNodes.item(i);

	            // Extrair os elementos da liga
	            Element nomeLigaElement = (Element) ligaElement.getElementsByTagName("nome").item(0);
	            String nomeLiga = nomeLigaElement.getTextContent();

	            Element localLigaElement = (Element) ligaElement.getElementsByTagName("local").item(0);
	            String local = localLigaElement.getTextContent();

	            Element numeroEquipasElement = (Element) ligaElement.getElementsByTagName("numeroEquipas").item(0);
	            String numeroEquipasString = numeroEquipasElement.getTextContent();
	            int numeroEquipas = 0; // Valor para caso não seja possível converter

	            if (!numeroEquipasString.isEmpty()) {
	                try {
	                    numeroEquipas = Integer.parseInt(numeroEquipasString);
	                } catch (NumberFormatException e) {
	                    e.printStackTrace();
	                }
	            }

	            // Criar o objeto Liga
	            Liga liga = new Liga(nomeLiga, local, numeroEquipas);
	            
	            
	         

	            // Obter a lista de elementos "equipa" dentro da liga
	            NodeList equipaNodes = ligaElement.getElementsByTagName("equipa");
	            for (int j = 0; j < equipaNodes.getLength(); j++) {
	                Element equipaElement = (Element) equipaNodes.item(j);

	                // Extrair os elementos da equipa
	                Element nomeEquipaElement = (Element) equipaElement.getElementsByTagName("nome").item(0);
	                String nomeEquipa = nomeEquipaElement.getTextContent();

	                Element localizacaoEquipaElement = (Element) equipaElement.getElementsByTagName("local").item(0);
	                String localizacao = localizacaoEquipaElement.getTextContent();

	                // Criar o objeto EquipaFutebol
	                EquipaFutebol equipa = new EquipaFutebol(nomeEquipa, localizacao);


	                // Adicionar a equipa à liga
	                liga.addEquipa(equipa);
	                equipas.add(equipa);
	            }
	         // Obter a lista de elementos "jogo" dentro da liga
	            NodeList jogoNodes = ligaElement.getElementsByTagName("jogo");
	            for (int j = 0; j < jogoNodes.getLength(); j++) {
	                Element jogoElement = (Element) jogoNodes.item(j);

	                // Extrair os elementos do jogo
	                Element teamAElement = (Element) jogoElement.getElementsByTagName("teamA").item(0);
	                String teamAName = teamAElement.getTextContent();

	                Element teamBElement = (Element) jogoElement.getElementsByTagName("teamB").item(0);
	                String teamBName = teamBElement.getTextContent();

	                Element teamAScoreElement = (Element) jogoElement.getElementsByTagName("teamAScore").item(0);
	                String teamAScoreString = teamAScoreElement.getTextContent();
	                int teamAScore = Integer.parseInt(teamAScoreString); // Supondo que sempre será um número válido

	                Element teamBScoreElement = (Element) jogoElement.getElementsByTagName("teamBScore").item(0);
	                String teamBScoreString = teamBScoreElement.getTextContent();
	                int teamBScore = Integer.parseInt(teamBScoreString); // Supondo que sempre será um número válido

	                
	                Element dataJogoElement = (Element) jogoElement.getElementsByTagName("date").item(0);
	                String dataJogoString = dataJogoElement.getTextContent();
	                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	                Date dataJogo;
	                try {
	                    dataJogo = dateFormat.parse(dataJogoString);
	                } catch (ParseException e) {
	                    e.printStackTrace();
	                    dataJogo = null; 
	                }
	                
	                
	                // Encontrar as equipas com base nos nomes
	                EquipaFutebol teamA = liga.getEquipaByName(teamAName);
	                EquipaFutebol teamB = liga.getEquipaByName(teamBName);
	                
	                // Verificar se as equipas foram encontradas
	                if (teamA != null && teamB != null) {
	                    // Criar o objeto Jogo
	                    Jogo jogo = new Jogo(teamA, teamB, teamAScore, teamBScore, dataJogo);
	                   
	                    // Adicionar o jogo à liga
	                    liga.addJogo(jogo);
	                    System.out.println(liga.getJogos());
	                    
	                }
	                
	            }
	            for(Jogo jogo2: liga.getJogos()) {
							liga.atualizarEstatisticas(jogo2);
					}
	            // Adicionar a liga à lista de ligas
	            futebol.addLiga(liga);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void saveToXML() {
		 
		 try {
		        // Criar um novo documento XML
		        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder builder = factory.newDocumentBuilder();
		        Document document = builder.newDocument();

		        // Criar o elemento raiz
		        Element rootElement = document.createElement("futebol");
		        document.appendChild(rootElement);

		        // Percorrer a lista de ligas
		        for (Liga liga : futebol.getLigas()) {
		            // Criar o elemento liga
		            Element ligaElement = document.createElement("liga");
		            rootElement.appendChild(ligaElement);

		            // Criar o elemento nomeLiga e adicionar o texto
		            Element nomeLigaElement = document.createElement("nome");
		            nomeLigaElement.setTextContent(liga.getName());
		            ligaElement.appendChild(nomeLigaElement);

		            // Criar o elemento localLiga e adicionar o texto
		            Element localLigaElement = document.createElement("local");
		            localLigaElement.setTextContent(liga.getLocal());
		            ligaElement.appendChild(localLigaElement);

		            // Criar o elemento numeroEquipas e adicionar o texto
		            Element numeroMaxClubesElement = document.createElement("numeroEquipas");
		            numeroMaxClubesElement.setTextContent(String.valueOf(liga.getNumEquipas()));
		            ligaElement.appendChild(numeroMaxClubesElement);
		            
		         // Percorrer a lista de jogos dentro da liga
		            for (Jogo jogo : liga.getJogos()) {
		                // Criar o elemento jogo
		                Element jogoElement = document.createElement("jogo");
		                ligaElement.appendChild(jogoElement);

		                // Criar o elemento teamA e adicionar o texto
		                Element teamAElement = document.createElement("teamA");
		                teamAElement.setTextContent(jogo.getTeamA().getNome());
		                jogoElement.appendChild(teamAElement);

		                // Criar o elemento teamB e adicionar o texto
		                Element teamBElement = document.createElement("teamB");
		                teamBElement.setTextContent(jogo.getTeamB().getNome());
		                jogoElement.appendChild(teamBElement);

		                // Criar o elemento teamAScore e adicionar o texto
		                Element teamAScoreElement = document.createElement("teamAScore");
		                teamAScoreElement.setTextContent(String.valueOf(jogo.getTeamAScore()));
		                jogoElement.appendChild(teamAScoreElement);

		                // Criar o elemento teamBScore e adicionar o texto
		                Element teamBScoreElement = document.createElement("teamBScore");
		                teamBScoreElement.setTextContent(String.valueOf(jogo.getTeamBScore()));
		                jogoElement.setNodeValue(String.valueOf(jogo.getTeamBScore()));
		                jogoElement.appendChild(teamBScoreElement);
		                // Criar o elemento date e adicionar o texto
		                Element dateElement = document.createElement("date");
		                dateElement.setTextContent(String.valueOf(jogo.getDateAsString()));
		                jogoElement.appendChild(dateElement);
		            }

		            // Percorrer a lista de equipas dentro da liga
		            for (EquipaFutebol equipa : liga.getEquipas()) {
		                // Criar o elemento equipa
		                Element equipaElement = document.createElement("equipa");
		                ligaElement.appendChild(equipaElement);

		                // Criar o elemento nomeEquipa e adicionar o texto
		                Element nomeEquipaElement = document.createElement("nome");
		                nomeEquipaElement.setTextContent(equipa.getNome());
		                equipaElement.appendChild(nomeEquipaElement);

		                // Criar o elemento localizacaoEquipa e adicionar o texto
		                Element localizacaoEquipaElement = document.createElement("local");
		                localizacaoEquipaElement.setTextContent(equipa.getLocalizacao());
		                equipaElement.appendChild(localizacaoEquipaElement);

		            }
		        }

	     // Salva o documento XML em um arquivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileWriter("src/XML/dataBase.xml"));
            transformer.transform(source, result);

            System.out.println("Dados salvos no arquivo XML com sucesso!");

        } catch (ParserConfigurationException |TransformerException | IOException e) {
            e.printStackTrace();
        }
	}
	
	
	public static void main(String[] args) {
		try {

	        File xmlFile = new File("src/XML/dataBase.xml");

	        // Cria uma instância de DocumentBuilderFactory
	        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

	        // Cria uma instância de DocumentBuilder
	        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

	        // Faz o parse do arquivo XML e obtém um objeto Document
	        Document doc = docBuilder.parse(xmlFile);

	        // Usa EventQueue para executar código relacionado à GUI na Event Dispatch Thread
	        EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {
	                    // Cria e mostra o frame da aplicação GUI
	                    GUI_Aplicacao frame = new GUI_Aplicacao();
	                    frame.setVisible(true);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });


        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    
	    
	}
	

	/**
	 * cria o frame
	 */
	public GUI_Aplicacao() {
		build();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 891, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JComboBox<String> sel_liga = new JComboBox<>();
		sel_liga.setBounds(334, 134, 153, 58);
		sel_liga.setModel(new DefaultComboBoxModel<>(new String[] {"Selecione a Liga"}));
		for (int i = 0; i < futebol.getLigas().size(); i++) {
			sel_liga.addItem(futebol.getLigas().get(i).getName());
		}
		
		ImageIcon imagemFundo = new ImageIcon("D:\\4semestre_Leim\\MOP2.0\\JV_TP4_parteB\\campo.jpg");

		// Redimensiona a imagem 
		Image imagemRedimensionada = imagemFundo.getImage().getScaledInstance(888, 409, Image.SCALE_SMOOTH);

		// Cria um novo ImageIcon com a imagem redimensionada
		ImageIcon imagemFundoRedimensionada = new ImageIcon(imagemRedimensionada);
		
		
		JPanel gerir_equipa_panel = new JPanel();
		gerir_equipa_panel.setVisible(false);
		gerir_equipa_panel.setBounds(0, 0, 888, 409);
		contentPane.add(gerir_equipa_panel);
		gerir_equipa_panel.setLayout(null);
		JLabel labelFundo7 = new JLabel(imagemFundoRedimensionada);
		labelFundo7.setBounds(0, 0, 888, 409);
		
		
		JPanel liga_panel = new JPanel();
		liga_panel.setVisible(false);
		liga_panel.setBounds(0, 0, 888, 409);
		contentPane.add(liga_panel);
		liga_panel.setLayout(null);
		JLabel labelFundo5 = new JLabel(imagemFundoRedimensionada);
		labelFundo5.setBounds(0, 0, 888, 409);
		
		
		JPanel escolha_painel = new JPanel();
		escolha_painel.setVisible(false);
		escolha_painel.setBounds(0, 0, 888, 409);
		contentPane.add(escolha_painel);
		escolha_painel.setLayout(null);
		JLabel labelFundo4 = new JLabel(imagemFundoRedimensionada);
		labelFundo4.setBounds(0, 0, 888, 409);
		
		JPanel addJogo_panel = new JPanel();
		addJogo_panel.setBounds(0, 0, 888, 409);
		contentPane.add(addJogo_panel);
		addJogo_panel.setLayout(null);
		JLabel labelFundo3 = new JLabel(imagemFundoRedimensionada);
		labelFundo3.setBounds(0, 0, 888, 409);
		
		JPanel stats_painel = new JPanel();
		stats_painel.setVisible(false);
		stats_painel.setBounds(0, 0, 888, 409);
		contentPane.add(stats_painel);
		stats_painel.setLayout(null);
		JLabel labelFundo2 = new JLabel(imagemFundoRedimensionada);
		labelFundo2.setBounds(0, 0, 888, 409);
		
		JPanel menu_panel = new JPanel();
		menu_panel.setBounds(0, 0, 888, 409);
		contentPane.add(menu_panel);
		menu_panel.setLayout(null);
		JLabel labelFundo1 = new JLabel(imagemFundoRedimensionada);
		labelFundo1.setBounds(0, 0, 888, 409);
		
		
	
		
		JPanel classificacao_panel = new JPanel();
		classificacao_panel.setVisible(false);
		classificacao_panel.setBounds(0, 0, 888, 409);
		classificacao_panel.setLayout(new GridBagLayout());
		contentPane.add(classificacao_panel);
		JButton btnNewButton_voltar8 = new JButton("Voltar");
		btnNewButton_voltar8.setBounds(35, 352, 85, 21);
		classificacao_panel.add(btnNewButton_voltar8);
		btnNewButton_voltar8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				liga_panel.setVisible(true);
				classificacao_panel.setVisible(false);
				}
			});
		JLabel labelFundo6 = new JLabel(imagemFundoRedimensionada);
		labelFundo6.setBounds(0, 0, 888, 409);
		
		
		
		JPanel jogos_panel = new JPanel();
		jogos_panel.setBounds(0, 0, 888, 409);
		contentPane.add(jogos_panel);
		jogos_panel.setLayout(null);
		jogos_panel.setVisible(false);
		JLabel labelFundo8 = new JLabel(imagemFundoRedimensionada);
		labelFundo8.setBounds(0, 0, 888, 409);
		
		
		
		JPanel ver_jogo_panel = new JPanel();
		ver_jogo_panel.setVisible(false);
		ver_jogo_panel.setBounds(0, 0, 888, 409);
		contentPane.add(ver_jogo_panel);
		ver_jogo_panel.setLayout(null);
		JLabel labelFundo9 = new JLabel(imagemFundoRedimensionada);
		labelFundo9.setBounds(0, 0, 888, 409);
		
		
		JPanel ver_jogo_panel2 = new JPanel();
		ver_jogo_panel2.setVisible(false);
		ver_jogo_panel2.setBounds(0, 0, 888, 409);
		contentPane.add(ver_jogo_panel2);
		ver_jogo_panel2.setLayout(null);
		JButton btnNewButton_voltar7 = new JButton("Voltar");
		btnNewButton_voltar7.setBounds(35, 352, 85, 21);
		ver_jogo_panel2.add(btnNewButton_voltar7);
		btnNewButton_voltar7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				ver_jogo_panel.setVisible(true);
				ver_jogo_panel2.setVisible(false);
				}
			});
		JLabel labelFundo10 = new JLabel(imagemFundoRedimensionada);
		labelFundo10.setBounds(0, 0, 888, 409);
		
		
		
		//STATS PAINEL
		JLabel nome_equipa = new JLabel("Nome Equipa");
		nome_equipa.setForeground(Color.WHITE);
		nome_equipa.setHorizontalAlignment(SwingConstants.CENTER);
		nome_equipa.setFont(new Font("Tahoma", Font.BOLD, 24));
		nome_equipa.setBounds(260, 23, 258, 65);
		stats_painel.add(nome_equipa);
		
		JLabel vitorias = new JLabel("Vitórias :");
		vitorias.setForeground(Color.WHITE);
		vitorias.setFont(new Font("Tahoma", Font.PLAIN, 20));
		vitorias.setBounds(152, 198, 85, 25);
		stats_painel.add(vitorias);
		
		JLabel derrotas = new JLabel("Derrotas :");
		derrotas.setForeground(Color.WHITE);
		derrotas.setFont(new Font("Tahoma", Font.PLAIN, 20));
		derrotas.setBounds(152, 261, 95, 25);
		stats_painel.add(derrotas);
		
		JLabel empates = new JLabel("Empates:");
		empates.setForeground(Color.WHITE);
		empates.setFont(new Font("Tahoma", Font.PLAIN, 20));
		empates.setBounds(152, 319, 85, 25);
		stats_painel.add(empates);
		
		JLabel pontos = new JLabel("Pontos :");
		pontos.setForeground(Color.WHITE);
		pontos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pontos.setBounds(541, 132, 74, 25);
		stats_painel.add(pontos);
		
		JLabel golos_marcados = new JLabel("Golos Marcados :");
		golos_marcados.setForeground(Color.WHITE);
		golos_marcados.setFont(new Font("Tahoma", Font.PLAIN, 20));
		golos_marcados.setBounds(541, 198, 154, 25);
		stats_painel.add(golos_marcados);
		
		JLabel golos_sofridos = new JLabel("Golos Sofridos:");
		golos_sofridos.setForeground(Color.WHITE);
		golos_sofridos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		golos_sofridos.setBounds(541, 261, 219, 25);
		stats_painel.add(golos_sofridos);
		
		JLabel jogos = new JLabel("Jogos :");
		jogos.setForeground(Color.WHITE);
		jogos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		jogos.setBounds(152, 132, 70, 25);
		stats_painel.add(jogos);
		
		JButton btnNewButton_voltar6 = new JButton("Voltar");
		btnNewButton_voltar6.setBounds(35, 352, 85, 21);
		stats_painel.add(btnNewButton_voltar6);
		
		JLabel jogos2 = new JLabel("");
		jogos2.setForeground(Color.WHITE);
		jogos2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		jogos2.setBounds(232, 132, 56, 25);
		stats_painel.add(jogos2);
		
		JLabel vitorias2 = new JLabel("");
		vitorias2.setForeground(Color.WHITE);
		vitorias2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		vitorias2.setBounds(245, 198, 85, 25);
		stats_painel.add(vitorias2);
		
		JLabel derrotas2 = new JLabel("");
		derrotas2.setForeground(Color.WHITE);
		derrotas2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		derrotas2.setBounds(249, 261, 95, 25);
		stats_painel.add(derrotas2);
		
		JLabel empates2 = new JLabel("");
		empates2.setForeground(Color.WHITE);
		empates2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		empates2.setBounds(245, 319, 85, 25);
		stats_painel.add(empates2);
		
		JLabel pontos2 = new JLabel("");
		pontos2.setForeground(Color.WHITE);
		pontos2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pontos2.setBounds(625, 132, 74, 25);
		stats_painel.add(pontos2);
		
		JLabel golos_marcados2 = new JLabel("");
		golos_marcados2.setForeground(Color.WHITE);
		golos_marcados2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		golos_marcados2.setBounds(699, 198, 154, 25);
		stats_painel.add(golos_marcados2);
		
		JLabel golos_sofridos2 = new JLabel("");
		golos_sofridos2.setForeground(Color.WHITE);
		golos_sofridos2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		golos_sofridos2.setBounds(676, 261, 154, 25);
		stats_painel.add(golos_sofridos2);
		btnNewButton_voltar6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				gerir_equipa_panel.setVisible(true);
				stats_painel.setVisible(false);
				}
			});
		stats_painel.add(labelFundo2);
		//

		
		//VER JOGO PAINEL 8
		JLabel lblNewLabel_4 = new JLabel("Insira a equipa da casa");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setBounds(87, 134, 188, 56);
		ver_jogo_panel.add(lblNewLabel_4);
		
		JComboBox<String>comboBox_1 = new JComboBox<>();
		comboBox_1.setBounds(122, 214, 86, 21);
		ver_jogo_panel.add(comboBox_1);
		for (int i = 0; i < equipas.size(); i++) {
			comboBox_1.addItem(equipas.get(i).getNome());
		}
		
		
		JLabel lblNewLabel_4_1 = new JLabel("Insira a equipa visitante");
		lblNewLabel_4_1.setForeground(Color.WHITE);
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4_1.setBounds(351, 134, 188, 56);
		ver_jogo_panel.add(lblNewLabel_4_1);
		
		JComboBox<String>comboBox_1_1 = new JComboBox<>();
		comboBox_1_1.setBounds(377, 214, 86, 21);
		ver_jogo_panel.add(comboBox_1_1);
		for (int i = 0; i < equipas.size(); i++) {
			comboBox_1_1.addItem(equipas.get(i).getNome());
		}
		
		
		
		JLabel lblNewLabel_4_1_1 = new JLabel("Insira a data");
		lblNewLabel_4_1_1.setForeground(Color.WHITE);
		lblNewLabel_4_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4_1_1.setBounds(637, 134, 188, 56);
		ver_jogo_panel.add(lblNewLabel_4_1_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(637, 215, 96, 19);
		ver_jogo_panel.add(textField_2);
		textField_2.setColumns(10);
		
		
		
		JButton btnNewButton_10 = new JButton("Ver Jogo");
		btnNewButton_10.setBounds(377, 317, 85, 21);
		ver_jogo_panel.add(btnNewButton_10);
		btnNewButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		String ligaSelecionada = (String) sel_liga.getSelectedItem();
		String equipaSelecionada = (String) comboBox_1.getSelectedItem();
		for (Liga liga : futebol.getLigas()) {
			for(EquipaFutebol equipa : equipas) {
				if(equipa.getNome().equals(equipaSelecionada)) {
					if(liga.getEquipas().contains(equipa)) {
						equipaA = equipa;
					}	
				}
    		}
    	}
		String equipaSelecionadaB = (String) comboBox_1_1.getSelectedItem();
		for (Liga liga : futebol.getLigas()) {
			for(EquipaFutebol equipa : equipas) {
				if(equipa.getNome().equals(equipaSelecionadaB)) {
					if(liga.getEquipas().contains(equipa)) {
						equipaB = equipa;
					}	
				}
    		}
    	}
		
		String data = textField_2.getText();	
		

			for(Liga liga : futebol.getLigas()) {
				if (liga.getName().equals(ligaSelecionada)) {
					for(Jogo jogo : liga.getJogos()) {
						if(jogo.getTeamA().equals(equipaA) && jogo.getTeamB().equals(equipaB) && jogo.getDateAsString().equals(data)){
							jogoGuardado = jogo;
							ver_jogo_panel.setVisible(false);
							ver_jogo_panel2.setVisible(true);
							
							JLabel lblNewLabel_5 = new JLabel("Equipa A");
							lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 26));
							lblNewLabel_5.setBounds(151, 125, 158, 104);
							ver_jogo_panel2.add(lblNewLabel_5);
							lblNewLabel_5.setText(equipaSelecionada);
							lblNewLabel_5.setForeground(Color.WHITE);
							
							JLabel lblNewLabel_6 = new JLabel("GA");
							lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 26));
							lblNewLabel_6.setBounds(320, 146, 97, 62);
							ver_jogo_panel2.add(lblNewLabel_6);
							lblNewLabel_6.setForeground(Color.WHITE);
							//System.out.println(jogo.getTeamBScore());
							if(jogoGuardado != null) {
								int golosA = jogoGuardado.getTeamAScore();
								String golosAString = String.valueOf(golosA);
								System.out.println(golosAString);
								lblNewLabel_6.setText(golosAString);
							}
		
		
							JLabel lblNewLabel_5_1 = new JLabel("Equipa B");
							lblNewLabel_5_1.setFont(new Font("Tahoma", Font.BOLD, 26));
							lblNewLabel_5_1.setBounds(604, 125, 158, 104);
							ver_jogo_panel2.add(lblNewLabel_5_1);
							lblNewLabel_5_1.setText(equipaSelecionadaB);
							lblNewLabel_5_1.setForeground(Color.WHITE);
		
							JLabel lblNewLabel_6_1 = new JLabel("GB");
							lblNewLabel_6_1.setFont(new Font("Tahoma", Font.PLAIN, 26));
							lblNewLabel_6_1.setBounds(497, 146, 97, 62);
							ver_jogo_panel2.add(lblNewLabel_6_1);
							if(jogoGuardado != null) {
								int golosB = jogoGuardado.getTeamBScore();
								String golosBString = String.valueOf(golosB);
								lblNewLabel_6_1.setText(golosBString);
							}
							lblNewLabel_6_1.setForeground(Color.WHITE);
							JLabel lblNewLabel_6_2 = new JLabel("-");
							lblNewLabel_6_2.setFont(new Font("Tahoma", Font.PLAIN, 26));
							lblNewLabel_6_2.setBounds(414, 146, 56, 62);
							ver_jogo_panel2.add(lblNewLabel_6_2);
							lblNewLabel_6_2.setForeground(Color.WHITE);
							
							JLabel lblNewLabel_7 = new JLabel("Data");
							lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 18));
							lblNewLabel_7.setBounds(399, 218, 97, 27);
							ver_jogo_panel2.add(lblNewLabel_7);
							if(jogoGuardado != null) {
								lblNewLabel_7.setText(jogoGuardado.getDateAsString());
							}
							lblNewLabel_7.setForeground(Color.WHITE);
							ver_jogo_panel2.add(labelFundo9);
						}
					}
				}
			}	
    	}
    	});
		
		
		JButton btnNewButton_voltar5 = new JButton("Voltar");
		btnNewButton_voltar5.setBounds(35, 352, 85, 21);
		ver_jogo_panel.add(btnNewButton_voltar5);
		btnNewButton_voltar5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				jogos_panel.setVisible(true);
				ver_jogo_panel.setVisible(false);
				}
			});
		ver_jogo_panel.add(labelFundo3);
		//
		
		//ADD JOGO PAINEL
		JLabel lblNewLabel_3 = new JLabel("Equipa da casa");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3.setBounds(71, 83, 192, 57);
		addJogo_panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Equipa Visitante");
		lblNewLabel_3_1.setForeground(Color.WHITE);
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3_1.setBounds(574, 83, 192, 57);
		addJogo_panel.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_2 = new JLabel("Golos da equipa da casa");
		lblNewLabel_3_2.setForeground(Color.WHITE);
		lblNewLabel_3_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3_2.setBounds(71, 243, 220, 57);
		addJogo_panel.add(lblNewLabel_3_2);
		
		JLabel lblNewLabel_3_3 = new JLabel("Golos equipa visitante");
		lblNewLabel_3_3.setForeground(Color.WHITE);
		lblNewLabel_3_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3_3.setBounds(574, 243, 192, 57);
		addJogo_panel.add(lblNewLabel_3_3);
		
		JComboBox<String> sel_equipa_casa = new JComboBox<>();
		sel_equipa_casa.setBounds(121, 169, 79, 21);
		addJogo_panel.add(sel_equipa_casa);
		for (int i = 0; i < equipas.size(); i++) {
			sel_equipa_casa.addItem(equipas.get(i).getNome());
		}
		
		JComboBox<String>sel_equipa_visitante = new JComboBox<>();
		sel_equipa_visitante.setBounds(628, 169, 79, 21);
		addJogo_panel.add(sel_equipa_visitante);
		for (int i = 0; i < equipas.size(); i++) {
			sel_equipa_visitante.addItem(equipas.get(i).getNome());
		}
		
		JLabel lblNewLabel_3_4 = new JLabel("Insira a data");
		lblNewLabel_3_4.setForeground(Color.WHITE);
		lblNewLabel_3_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_4.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3_4.setBounds(332, 133, 192, 57);
		addJogo_panel.add(lblNewLabel_3_4);
		
		textField_gol_casa = new JTextField();
		textField_gol_casa.setBounds(130, 310, 96, 19);
		addJogo_panel.add(textField_gol_casa);
		textField_gol_casa.setColumns(10);
		
		textField_gol_fora = new JTextField();
		textField_gol_fora.setColumns(10);
		textField_gol_fora.setBounds(628, 310, 96, 19);
		addJogo_panel.add(textField_gol_fora);
		
		textField_date = new JTextField();
		textField_date.setColumns(10);
		textField_date.setBounds(385, 188, 96, 19);
		addJogo_panel.add(textField_date);
		
		JButton btnNewButton_9 = new JButton("Adicionar Jogo");
		btnNewButton_9.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_9.setBounds(358, 300, 146, 35);
		addJogo_panel.add(btnNewButton_9);
		addJogo_panel.setVisible(false);
		btnNewButton_9.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	String ligaSelecionada = (String) sel_liga.getSelectedItem();
		    	String equipaCasaSelecionada = (String) sel_equipa_casa.getSelectedItem();
		    	String equipaForaSelecionada = (String) sel_equipa_visitante.getSelectedItem();
		    	String golosCasaString = textField_gol_casa.getText();
		    	int golosCasa = Integer.parseInt(golosCasaString);
		    	String golosForaString = textField_gol_fora.getText();
		    	int golosFora = Integer.parseInt(golosForaString);
		    	String data = textField_date.getText();
		    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dataJogo;
                try {
                    // Parse the dataJogoString into a Date object
                    dataJogo = dateFormat.parse(data);
                } catch (ParseException e1) {
                    // Handle the parsing exception if the date format is invalid
                    e1.printStackTrace();
                    // Set a default value or throw an exception, depending on your requirements
                    dataJogo = null; // Set a default value (null in this case)
                }
		    	for (Liga liga : futebol.getLigas()) {
		    		System.out.println(liga.getName() + ligaSelecionada);
		    		if (liga.getName().equals(ligaSelecionada)) {
		    			for(EquipaFutebol equipa : equipas) {
		    				if(equipa.getNome().equals(equipaCasaSelecionada)) {
		    					if(liga.getEquipas().contains(equipa)) {
		    						equipaA = equipa;
		    					}	
		    				}
		    				if(equipa.getNome().equals(equipaForaSelecionada)) {
		    					if(liga.getEquipas().contains(equipa)) {
		    						equipaB = equipa;
		    					}	
		    				}
		    			}
		    	Jogo jogo = new Jogo(equipaA, equipaB, golosCasa, golosFora, dataJogo);
		    	liga.addJogo(jogo);
		    	System.out.print(jogo);
		    	System.out.print(liga.getJogos());
		    	saveToXML();
		    	liga.atualizarEstatisticas(jogo);
		    		}
		    	
		    	}
		    	
		    }
		});
		
		JButton btnNewButton_voltar4 = new JButton("Voltar");
		btnNewButton_voltar4.setBounds(35, 352, 85, 21);
		addJogo_panel.add(btnNewButton_voltar4);
		btnNewButton_voltar4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				addJogo_panel.setVisible(false);
				jogos_panel.setVisible(true);
				}
			});
		addJogo_panel.add(labelFundo4);
		//
		
		
		//
		
		
		//JOGOS PAINEL
		JButton btnNewButton_8 = new JButton("Adicionar Jogo");
		btnNewButton_8.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_8.setBounds(342, 99, 157, 56);
		jogos_panel.add(btnNewButton_8);
		btnNewButton_8.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	jogos_panel.setVisible(false);
		    	addJogo_panel.setVisible(true);
		    	
		    }
		});
		
		JButton btnNewButton_8_1 = new JButton("Ver Jogo");
		btnNewButton_8_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_8_1.setBounds(342, 227, 157, 56);
		jogos_panel.add(btnNewButton_8_1);
		btnNewButton_8_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	jogos_panel.setVisible(false);
		    	ver_jogo_panel.setVisible(true);
		    }
		});
		
		
		JButton btnNewButton_voltar3 = new JButton("Voltar");
		btnNewButton_voltar3.setBounds(35, 352, 85, 21);
		jogos_panel.add(btnNewButton_voltar3);
		btnNewButton_voltar3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			liga_panel.setVisible(true);
			jogos_panel.setVisible(false);
				}
			});
		jogos_panel.add(labelFundo5);
		//
		
		
		
		//GERIR EQUIPA PAINEL
		JLabel lblNewLabel_1 = new JLabel("Criar Equipa");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(77, 40, 161, 73);
		gerir_equipa_panel.add(lblNewLabel_1);
		
		
		JLabel lblNewLabel_1_1 = new JLabel("Eliminar Equipa");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1.setBounds(328, 40, 161, 73);
		gerir_equipa_panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Ver Estatisticas de uma Equipa");
		lblNewLabel_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1_1.setBounds(566, 15, 287, 123);
		gerir_equipa_panel.add(lblNewLabel_1_1_1);
		

		
		JLabel lblNewLabel_2 = new JLabel("<html>Escreva o nome da equipa<br>que pretende criar</html>");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(67, 78, 177, 80);
		gerir_equipa_panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("<html>Selecione o nome da equipa<br>que pretende eliminar</html>");
		lblNewLabel_2_1.setForeground(Color.WHITE);
		lblNewLabel_2_1.setBounds(350, 78, 151, 80);
		gerir_equipa_panel.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("<html>Selecione o nome da equipa<br>que pretende ver estatisticas</html>");
		lblNewLabel_2_2.setForeground(Color.WHITE);
		lblNewLabel_2_2.setBounds(619, 78, 190, 80);
		gerir_equipa_panel.add(lblNewLabel_2_2);
		
		
		//ADICIONAR NOME EQUIPA
		textField = new JTextField();
		textField.setBounds(77, 146, 96, 19);
		gerir_equipa_panel.add(textField);
		textField.setColumns(10);

		
		//ADICIONAR LOC EQUIPA
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(77, 257, 96, 19);
		gerir_equipa_panel.add(textField_1);

		//SELECIONAR EQUIPA A ELIMINAR
		JComboBox<String> elim_equipa_sel = new JComboBox<>();
		elim_equipa_sel.setBounds(360, 156, 85, 21);
		gerir_equipa_panel.add(elim_equipa_sel);
		for (int i = 0; i < equipas.size(); i++) {
			elim_equipa_sel.addItem(equipas.get(i).getNome());
		}
		JButton btnNewButton_7_1 = new JButton("eliminar");
		btnNewButton_7_1.setBounds(360, 193, 85, 21);
		gerir_equipa_panel.add(btnNewButton_7_1);
		btnNewButton_7_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	String ligaSelecionada = (String) sel_liga.getSelectedItem();
		    	String equipaSelecionada = (String) elim_equipa_sel.getSelectedItem();
		    	for (Liga liga : futebol.getLigas()) {
		    		System.out.println(liga.getName() + ligaSelecionada);
		    		if (liga.getName().equals(ligaSelecionada)) {
		    			for(EquipaFutebol equipa : equipas) {
		    				if(equipa.getNome().equals(equipaSelecionada)) {
		    					liga.removeEquipa(equipa);
		    					System.out.println(liga.getEquipas());
		    					saveToXML();
		    				}
		    			}
		    		}
		    		}
		    }
		});
		
		//SELECIONAR EQUIPA A ADICIONAR
		JComboBox<String> add_equipa_sel = new JComboBox<>();
		add_equipa_sel.setBounds(360, 302, 85, 21);
		gerir_equipa_panel.add(add_equipa_sel);
		for (int i = 0; i < equipas.size(); i++) {
		    add_equipa_sel.addItem(equipas.get(i).getNome());
		}
		
		
		//CRIAR EQUIPA
		JButton btnNewButton_6 = new JButton("Criar Equipa");
		btnNewButton_6.setBounds(67, 302, 96, 21);
		gerir_equipa_panel.add(btnNewButton_6);
		btnNewButton_6.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	String nomeEquipa = textField.getText();
		        String locEquipa = textField_1.getText();
		    	EquipaFutebol equipa = new EquipaFutebol(nomeEquipa, locEquipa);
		    	equipas.add(equipa);
		    	System.out.print(equipas);
		    	// Atualiza a combobox com as equipas atualizadas
		        add_equipa_sel.addItem(equipa.getNome());
		        elim_equipa_sel.addItem(equipa.getNome());
		 
		        saveToXML();
		    }
		});
		
		JLabel lblNewLabel_2_3 = new JLabel("<html>Escreva a localizacao da equipa<br>que pretende criar</html>");
		lblNewLabel_2_3.setForeground(Color.WHITE);
		lblNewLabel_2_3.setBounds(67, 196, 190, 80);
		gerir_equipa_panel.add(lblNewLabel_2_3);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Adicionar Equipa");
		lblNewLabel_1_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1_2.setBounds(328, 201, 161, 73);
		gerir_equipa_panel.add(lblNewLabel_1_1_2);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("<html>Selecione o nome da equipa<br>que pretende adicionar</html>");
		lblNewLabel_2_1_1.setForeground(Color.WHITE);
		lblNewLabel_2_1_1.setBounds(338, 233, 177, 80);
		gerir_equipa_panel.add(lblNewLabel_2_1_1);
		
		//SELECIONAR EQUIPA PARA MOSTRAR ESTATISTICAS
		JComboBox <String>sel_equipa_stats = new JComboBox<>();
		sel_equipa_stats.setBounds(651, 156, 85, 21);
		gerir_equipa_panel.add(sel_equipa_stats);
		for (int i = 0; i < equipas.size(); i++) {
			sel_equipa_stats.addItem(equipas.get(i).getNome());
			}
		JButton btnNewButton_7_2 = new JButton("avançar");
		btnNewButton_7_2.setBounds(651, 196, 85, 21);
		gerir_equipa_panel.add(btnNewButton_7_2);
		btnNewButton_7_2.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String ligaSelecionada = (String) sel_liga.getSelectedItem();
			String equipa_selecionada = (String) sel_equipa_stats.getSelectedItem();
			nome_equipa.setText(equipa_selecionada);
			gerir_equipa_panel.setVisible(false);
			stats_painel.setVisible(true);
			for (Liga liga : futebol.getLigas()) {
			System.out.println(liga.getName() + ligaSelecionada);
			if (liga.getName().equals(ligaSelecionada)) {
				for(EquipaFutebol equipa : equipas) {
				    if(equipa.getNome().equals(equipa_selecionada)) {
				    	int jogos_jogados = equipa.getJogosJogados();
				    	System.out.println(jogos_jogados);
				    	jogos2.setText(Integer.toString(jogos_jogados));
				    	int e_vitorias = equipa.getVitorias();
				    	vitorias2.setText(Integer.toString(e_vitorias));
				    	int e_derrotas = equipa.getDerrotas();
				    	derrotas2.setText(Integer.toString(e_derrotas));
				    	int e_empates = equipa.getEmpates();
				    	empates2.setText(Integer.toString(e_empates));
				    	int e_pontos = equipa.getPontos();
				    	pontos2.setText(Integer.toString(e_pontos));
				    	int e_golos_marcados = equipa.getGolosMarcados();
				    	golos_marcados2.setText(Integer.toString(e_golos_marcados));
				    	int e_golos_sofridos = equipa.getGolosSofridos();
				    	golos_sofridos2.setText(Integer.toString(e_golos_sofridos));
				    	saveToXML();
				    	}
				    }
				}
			}
		}
	});
		//ADICIONAR EQUIPA
		JButton btnNewButton_7 = new JButton("adicionar");
		btnNewButton_7.setBounds(360, 340, 85, 21);
		gerir_equipa_panel.add(btnNewButton_7);
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ligaSelecionada = (String) sel_liga.getSelectedItem();
				String equipaSelecionada = (String) add_equipa_sel.getSelectedItem();
				for (Liga liga : futebol.getLigas()) {
				    System.out.println(liga.getName() + ligaSelecionada);
				    if (liga.getName().equals(ligaSelecionada)) {
				    	for(EquipaFutebol equipa : equipas) {
				    		if(equipa.getNome().equals(equipaSelecionada)) {
				    			if(liga.getEquipas().contains(equipa)) {
				    				System.out.println("Esta equipa já existe na liga");
				    				return;
				    				}
				    			liga.addEquipa(equipa);
				    			sel_equipa_stats.addItem(equipa.getNome());
				    			sel_equipa_casa.addItem(equipa.getNome());
				    			sel_equipa_visitante.addItem(equipa.getNome());
				    			System.out.println(liga.getEquipas());
				    			saveToXML();
				    			}
				    		}
				    	}
				    }
				}
			});
				
				
		JButton btnNewButton_voltar2 = new JButton("Voltar");
		btnNewButton_voltar2.setBounds(35, 352, 85, 21);
		gerir_equipa_panel.add(btnNewButton_voltar2);
		btnNewButton_voltar2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			gerir_equipa_panel.setVisible(false);
			liga_panel.setVisible(true);
				}
			});
		
		gerir_equipa_panel.add(labelFundo6);
		//
		
		
		
		
		
		//LIGA PAINEL 3
		JButton btnNewButton_1 = new JButton("Gerir Equipa");
		btnNewButton_1.setBounds(344, 94, 139, 39);
		liga_panel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	liga_panel.setVisible(false);
		    	gerir_equipa_panel.setVisible(true);
		    }
		});
		
		JButton btnNewButton_2 = new JButton("Classificação");
		btnNewButton_2.setBounds(344, 172, 139, 39);
		liga_panel.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	liga_panel.setVisible(false);
		    	classificacao_panel.setVisible(true);	
		    	DefaultTableModel tableModel = new DefaultTableModel(
		        new Object[][] {},
		        new String[] {"Equipa", "Posição", "Pontos", "Diferença de golos"}
		);
		JTable table_1 = new JTable(tableModel);

		JScrollPane scrollPane = new JScrollPane(table_1);
		scrollPane.setBounds(108, 111, 530, 64);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		classificacao_panel.add(scrollPane, gbc);

		int posicao = 1;
		String ligaSelecionada = (String) sel_liga.getSelectedItem();
		for (Liga liga : futebol.getLigas()) {
		    if (liga.getName().equals(ligaSelecionada)) {
		        Collections.sort(liga.getEquipas(), new Comparador());
		        for (EquipaFutebol team : liga.getEquipas()) {
		            System.out.println(team.getNome());
		            System.out.println(team.getGolosMarcados());
		            System.out.println(team.getGolosSofridos());

		            Object[] rowData = new Object[] {team.getNome(), posicao, team.getPontos(), team.getGolosMarcados() - team.getGolosSofridos()};
		            tableModel.addRow(rowData);
		            posicao++;
		        }
		    }
		}
		    }
		});


				
		
		JButton btnNewButton_3 = new JButton("Jogos");
		btnNewButton_3.setBounds(344, 250, 139, 39);
		liga_panel.add(btnNewButton_3);
		btnNewButton_3.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	liga_panel.setVisible(false);
		    	jogos_panel.setVisible(true);
		    	
		    }
		});
		
		JButton btnNewButton_4 = new JButton("Voltar");
		btnNewButton_4.setBounds(35, 352, 85, 21);
		liga_panel.add(btnNewButton_4);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			escolha_painel.setVisible(true);
			liga_panel.setVisible(false);
				}
			});
		
		liga_panel.add(labelFundo7);
		//
		
		//ESCOLHA PAINEL 2			
		escolha_painel.add(sel_liga);		
		JButton btnNewButton_5 = new JButton("Avançar");
		btnNewButton_5.setBounds(367, 299, 85, 21);
		escolha_painel.add(btnNewButton_5);

		btnNewButton_5.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		String ligaSelecionada = (String) sel_liga.getSelectedItem();
		System.out.println(ligaSelecionada);
		escolha_painel.setVisible(false);
		liga_panel.setVisible(true);
			}
		});
		
		JButton btnNewButton_voltar = new JButton("Voltar");
		btnNewButton_voltar.setBounds(35, 352, 85, 21);
		escolha_painel.add(btnNewButton_voltar);
		btnNewButton_voltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			escolha_painel.setVisible(false);
			menu_panel.setVisible(true);
				}
			});
		escolha_painel.add(labelFundo8);
		//


		//MENU PAINEL
		JLabel lblNewLabel = new JLabel("Football Manager");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(264, 64, 349, 97);
		menu_panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Vamos Começar");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu_panel.setVisible(false);
				escolha_painel.setVisible(true);
			}
		});
		btnNewButton.setBounds(364, 284, 136, 40);
		menu_panel.add(btnNewButton);
		menu_panel.add(labelFundo1);
		
	}
}
