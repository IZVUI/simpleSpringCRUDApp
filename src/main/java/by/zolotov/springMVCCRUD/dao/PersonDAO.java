package by.zolotov.springMVCCRUD.dao;

import by.zolotov.springMVCCRUD.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1234";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public List<Person> index() {
        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM person");

            while (resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));
                person.setName(resultSet.getString("name"));

                people.add(person);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return people;
    }

    public Person show(int id) {
         return index().stream().filter(p -> p.getId() == id).findAny().orElse(null);
        //return null;
    }

    public void save(Person person) {
//        person.setId(people.size() + 1);
//        people.add(person);

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO person VALUES("+index().size() + ",'"+person.getName()+"'"+
                    ",'"+person.getAge()+"','"+person.getEmail()+"')");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(int id, Person updatedPerson) {
//        Person personToUpdate = show(id);
//
//        personToUpdate.setName(updatedPerson.getName());
//        personToUpdate.setEmail(updatedPerson.getEmail());
//        personToUpdate.setAge(updatedPerson.getAge());
    }


    public void delete(int id) {
       // people.removeIf(p -> p.getId() == id);
    }
}
