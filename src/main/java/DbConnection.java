import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private String db;
    private String url;
    private String login;
    private String password;
    /**
     * Проверка подключения к базе
     * @param db передаём тип базы H2, Oracle или Postgres
     * @param url передаём URL базы
     * @param login
     * @param password
     * @return если получилось подключиться к базе - True, если нет, то - False
     */
    public boolean check(String db, String url, String login, String password){
        this.db=db;
        this.url=url;
        this.login=login;
        this.password=password;
        switch (db){
            case "h2":
                try{
                    Class.forName ("org.h2.Driver");
                }catch (ClassNotFoundException e){
                    return false;
                }
                return con();
            case "oracle":return con();

            case "postgres":return con();
        }
        return false;
    }
    private boolean con(){
        try(Connection connection = DriverManager.getConnection ("jdbc:" + db + ":" +url, login, password);)
        {
            return true;
        }catch(SQLException e){
            return false;
        }
    }
}
