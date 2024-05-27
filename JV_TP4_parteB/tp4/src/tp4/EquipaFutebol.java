package tp4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EquipaFutebol{
	
	private int vitorias;
	private int empates;
	private int derrotas;
	private int golos_marcados;
	private int golos_sofridos;
	private int pontos;
	private int jogos_jogados;
	public String nome;	
	private String localizacao;	
	private String estatisticas;
	//private ArrayList<EquipaFutebol> clubes;


	public EquipaFutebol(String nome, String localizacao) {
		this.nome = nome;
	}
	@Override
	public boolean equals(Object o) {
		return this.nome.equals(((EquipaFutebol)o).nome);
	}

	public int getVitorias() {
		return vitorias;
	}
	
	public int getEmpates() {
		return empates;
	}
	
	public int getDerrotas() {
		return derrotas;
	}
	
	public int getGolosMarcados() {
		return golos_marcados;
	}
	
	public int getGolosSofridos() {
		return golos_sofridos;
	}
	
	public int getJogosJogados() {
		return jogos_jogados;
	}
	
	public int getPontos() {
		return pontos;
	}

	
	public void setVitorias(int i) {
		vitorias = i;
	}
	
	public void setEmpates(int i) {
		empates = i;
	}
	
	public void setDerrotas(int i) {
		derrotas = i;
	}
	
	public void setGolosMarcados(int i) {
		golos_marcados = i;
	}
	
	public void setGolosSofridos(int i) {
		golos_sofridos = i;
	}
	
	public void setPontos(int i) {
		pontos = i;
	}
	
	public void setJogosJogados(int i) {
		jogos_jogados = i;
	}

	public String getNome() {
		return nome;
	}
	
	public String getLocalizacao() {
		return localizacao;
	}

	public String getEstatisticas() {
		return estatisticas;
	}
	
	public void setLocalizacao(String s){
		this.localizacao = s;
	}
	
	public void setEstatisticas(String s){
		this.estatisticas = s;
	}
	public void setNome(String s) {
		this.nome = s;
	}
	

	@Override
	public String toString() {
	    return this.getNome();
	}
}
