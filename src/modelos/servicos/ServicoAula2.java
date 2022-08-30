package modelos.servicos;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import modelos.entidades.Aula;

public class ServicoAula2 {
	
	public List<Aula> findAll() throws ParseException{
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date d = (Date) sdf1.parse("31/01/2022");
		Date h1 = (Date) sdf2.parse("31/01/2022 14:00:00");
		Date h2 = (Date) sdf2.parse("31/01/2022 15:00:00");
		List<Aula> list = new ArrayList<>();
		//argumentos data, HoraInicio, HoraFim, Aluno, Disciplina, Professor
		list.add(new Aula(1, d, h1, h2,null,null,null));
		return list;
	}
}