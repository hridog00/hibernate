package app;

import java.math.BigInteger;

public class IbanGenerator {

	public String generarIban(String CC, String pais) {
		String resultado = "";
		String iban = String.valueOf(generarCodigoIban(CC, pais));
		if(iban.length()==1) {
			iban = "0"+iban;
		}
		resultado = pais +iban +CC;		
		return resultado;
	}
	
	public boolean comprobarC1(String c1, String ccc) {
		if (!c1.equals(String.valueOf(generarCCC(ccc)))) {
			
			return false;
		}
		return true;
	}
	
	public static String damePesoIBAN(char letra){
		String peso = "";
		letra = Character.toUpperCase(letra);
		switch (letra){
		case 'A': peso = "10"; 
		break;
		case 'B': peso = "11"; 
		break;
		case 'C': peso = "12"; 
		break;
		case 'D': peso = "13"; 
		break;
		case 'E': peso = "14"; 
		break;
		case 'F': peso = "15"; 
		break;
		case 'G': peso = "16"; 
		break;
		case 'H': peso = "17"; 
		break;
		case 'I': peso = "18"; 
		break;
		case 'J': peso = "19"; 
		break;
		case 'K': peso = "20"; 
		break;
		case 'L': peso = "21"; 
		break;
		case 'M': peso = "22"; 
		break;
		case 'N': peso = "23"; 
		break;
		case 'O': peso = "24"; 
		break;
		case 'P': peso = "25"; 
		break;
		case 'Q': peso = "26"; 
		break;
		case 'R': peso = "27"; 
		break;
		case 'S': peso = "28"; 
		break;
		case 'T': peso = "29"; 
		break;
		case 'U': peso = "30"; 
		break;
		case 'V': peso = "31"; 
		break;
		case 'W': peso = "32"; 
		break;
		case 'X': peso = "33"; 
		break;
		case 'Y': peso = "34"; 
		break;
		case 'Z': peso = "35"; 
		break;
		}
		return peso;
	}
	
	public int generarCCC(String codigos) {
		int resultado = 0;
		float sumatorio = 0;
		int[] factores = {1,2,4,8,5,10,9,7,3,6};
		for(int i=0;i<codigos.length();i++) {
			String s = ""+codigos.charAt(i);
			sumatorio = sumatorio + (factores[i]*Integer.parseInt(s));
		}
		resultado = (int) (11- (sumatorio%11));
		if(resultado == 10) {
			resultado = 1;
		}
		if(resultado == 11) {
			resultado = 0;
		}
		return resultado;
		
	}
	
	public int generarCodigoIban(String cc, String pais) {
		int resultado = 0;
		
		String calculo = cc + damePesoIBAN(pais.charAt(0))+ damePesoIBAN(pais.charAt(1))+ "00";
		BigInteger b = new BigInteger(calculo);
		BigInteger d = new BigInteger("97");
		int res = (int)(98-(b.mod(d).intValue()));
		resultado = res;
		
		
		return resultado;
		
	}

}
