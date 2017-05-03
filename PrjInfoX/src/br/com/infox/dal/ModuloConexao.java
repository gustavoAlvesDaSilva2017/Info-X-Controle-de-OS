package br.com.infox.dal;
import java.sql.*;
import javax.swing.JOptionPane;

public class ModuloConexao {
    // Método responsável por estabelecer a conexão com o banco
    public static Connection conector() {
        /*
         * variável que receberá a string de conexão
        */
        java.sql.Connection conexao = null;

        /*
         * carrega o driver correspondente ao tipo de banco de dados
         * (o dado deve estar na biblioteca)
        */
        String driver = "com.mysql.jdbc.Driver";

        // armazena informações referentes ao banco
        String url = "jdbc:mysql://localhost:3306/dbinfox";

        // usuário, senha
        String user = "usermysql";
        String password = "cursomysql";

        // Estabelecendo a conexão com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
            return null;
        }
    }
}
