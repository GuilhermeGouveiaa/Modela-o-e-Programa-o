package tp4;


import java.util.Comparator;

public class Comparador implements Comparator<EquipaFutebol>{
	
	@Override
	public int compare(EquipaFutebol t, EquipaFutebol t1) {
		
		if(t.getPontos() > t1.getPontos()) {
			return -1;
		}
		else {
			if((t.getPontos() < t1.getPontos())) {
				return 1;
			}
			else {
				int diferenca_golos = t.getGolosMarcados() - t.getGolosSofridos();
				int diferenca_golos2 = t1.getGolosMarcados() - t1.getGolosSofridos();
				if(diferenca_golos > diferenca_golos2) {
					return -1;
				}
				else {
					if(diferenca_golos < diferenca_golos2) {
						return 1;
					}
					else return 0;
				}
			}
		}
	}

}
