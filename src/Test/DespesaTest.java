package Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import Dao.DespesaDAO;
import Domain.Despesa;
import Util.FacesUtil;

public class DespesaTest {

	@Test
	public void cartaoCredito() {
		//Despesa d = new Despesa();
		
		int i = 1;
		int t = 10;
		
		while(i<=t) {
			System.out.println(i+" parcela");
			i++;
		}
		System.out.println("Fim");
		
	}

	/*@Test
	public void replicar() {
		try {
			//DespesaDAO dao = new DespesaDAO();
			
			//Calendar datahoje = Calendar.getInstance();
			//int mes = datahoje.get(Calendar.MONTH); // mês
			System.out.println("-Replicar 1-");
			int t = dvdCadastro.getAux(); // Auxiliar para fazer calculo da parcela
			int x = 1;
			
			while(x<=t) {
				System.out.println("-Replicar 2-");
				DespesaDAO dao = new DespesaDAO();
				
				Date data = new Date();
				Calendar datahoje = Calendar.getInstance();
				datahoje.setTime(data);
				
				datahoje.set(datahoje.YEAR, datahoje.MONTH+x, datahoje.DAY_OF_MONTH);
				
				Date datahojeTransformada = data;
				int ano = datahoje.get(Calendar.YEAR); // ano atual
				int mes = datahoje.get(Calendar.MONTH+x); // mês atual
				int dia = datahoje.get(Calendar.DAY_OF_MONTH); // dia atual
			
				GregorianCalendar gc = new GregorianCalendar();
				Date data1;
				gc.set(GregorianCalendar.YEAR,ano);
				gc.set(GregorianCalendar.MONTH,mes);
				gc.set(GregorianCalendar.DAY_OF_MONTH,dia);
				data1 = gc.getTime();
				
				
				private static final GregorianCalendar primeiro_DAY = new GregorianCalendar(1930, Calendar.JANUARY, 1, 0, 0, 0);
				GregorianCalendar inicio = primeiro_DAY;
				int i[] = new int[3];
		        int dia1 = primeiro_DAY.get(Calendar.DATE);
		        int mes1 = primeiro_DAY.get(Calendar.MONTH);
		        int ano1 = primeiro_DAY.get(Calendar.YEAR);
		        GregorianCalendar today = new GregorianCalendar(ano1, mes1, dia1, 0, 0, 0);
		        today.add(Calendar.DATE, numero - 1);
		        Date ini = today.getTime();
		        i[0] = ini.getYear() + 1900;
		        i[1] = ini.getMonth() + 1;
		        i[2] = ini.getDate() + 1;
				
				 dvdCadastro.setDataPagamento(data1);
				
				 System.out.println("data1 = "+data1);
				System.out.println(x+" parcela");
				System.out.println("datahoje = "+datahoje);
				System.out.println("ano = "+ano+". mês = "+mes+". dia = "+dia);
				
				dao.salvar(dvdCadastro);
				x++;
			}
			System.out.println("-Replicar 3-");
			
			
			//dao.salvar(dvdCadastro);
		} catch (RuntimeException ex) {
			FacesUtil.addMsgErro("ERRO" + ex.getMessage());
			throw ex;
		}
	}*/
}
