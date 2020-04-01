package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {
private static final String USUARIO = "root";
private static final String SENHA = "5017";
private static final String URL = "jdbc:mysql://192.168.25.21:3306/fin2";

public static Connection conectar() throws SQLException{
	DriverManager.registerDriver(new com.mysql.jdbc.Driver());	
	Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
	return conexao;
}


public static void main(String[] args) {
	try {
		Connection conexao = ConexaoFactory.conectar();
		System.out.println("Conexão realizada com sucesso!" + conexao);
	} catch (SQLException ex) {
		System.out.println("Falha na conexão!");
	}
}

}
