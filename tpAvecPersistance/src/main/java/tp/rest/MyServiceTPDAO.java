package tp.rest;

import tp.model.Animal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyServiceTPDAO {

    private Connection connection;
    
    //le lien de la base de donnée en ligne bluemix
    private final static String url = "jdbc:mysql://sl-eu-lon-2-portal.1.dblayer.com:17137zoo-manager";
    private final static String user = "admin";
    private final static String pwd = "NPCSSJETJEJLREAK";

    public MyServiceTPDAO() throws Exception {
        Connection connection = null;

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection =
                    DriverManager.getConnection(url, user, pwd);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("USE zoo");
        stmt.executeUpdate("DROP TABLE IF EXISTS animals");
        stmt.executeUpdate("DROP TABLE IF EXISTS cages");
        stmt.executeUpdate("CREATE TABLE animals (id varchar(100), name varchar(100), cage varchar(100), species varchar(100))");
    }

    
    //ajouter un animal dans la base
    public void addAnimal(Animal animal) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO animals VALUES(?,?,?,?)");
        preparedStatement.setString(1, animal.getId().toString());
        preparedStatement.setString(2, animal.getName());
        preparedStatement.setString(3, animal.getCage());
        preparedStatement.setString(4, animal.getSpecies());
        preparedStatement.executeUpdate();
    }
    
    //recuperer tous les animaux dans la base

    public List<Animal> selectAnimal() throws  SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM animals");
        List<Animal> animals = new ArrayList<Animal>();
        while (rs.next()) {
            animals.add(
                    new Animal(
                            rs.getString("name"),
                            rs.getString("cage"),
                            rs.getString("species"),
                            UUID.fromString(rs.getString("id"))
                    ));
        }
        return animals;
    }
    
    //spprimer tous les animaux dans la base
    public void deleteAnimals() throws SQLException{
    	PreparedStatement preparedStatement = connection.prepareStatement("DELETE * from animals");
    	preparedStatement.execute();
    }
    
    //
    //spprimer un animal dans la base
    public void deleteAnimalById(String id) throws SQLException{
       	PreparedStatement preparedStatement = connection.prepareStatement("DELETE * FROM animals WHERE id=?");
       	preparedStatement.setString(1, id);
        
    }
}
