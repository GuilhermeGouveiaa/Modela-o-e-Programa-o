package tp4;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Jogo {

	
	private EquipaFutebol teamA;
	private EquipaFutebol teamB;
	
	private int teamAScore;
	private int teamBScore;
	
	private Date date;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public Jogo(EquipaFutebol teamA, EquipaFutebol teamB, int teamAScore, int teamBScore, Date date) {
		this.teamA = teamA;
		this.teamB = teamB;
		this.teamAScore = teamAScore;
		this.teamBScore = teamBScore;
		this.date = date;
	}
	
	public EquipaFutebol getTeamA() {
		return teamA;
	}
	
	public EquipaFutebol getTeamB() {
		return teamB;
	}
	
	public int getTeamAScore() {
		return teamAScore;
	}
	
	public int getTeamBScore() {
		return teamBScore;
	}
	
	public Date getDate() {
		return date;
	}
	public String getDateAsString() {
	    return dateFormat.format(date);
	}
	
	public void setTeamA(EquipaFutebol teamA) {
		this.teamA = teamA;
	}
	
	public void setTeamB(EquipaFutebol teamB) {
		this.teamB = teamB;
	}
	
	public void setTeamAScore(int teamAScore) {
		this.teamAScore = teamAScore;
	}
	public void setTeamBScore(int teamBScore) {
		this.teamBScore = teamBScore;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
	    return this.getTeamA().toString() + this.getTeamB().toString() + this.getTeamAScore() + this.getTeamAScore();
	}

}
