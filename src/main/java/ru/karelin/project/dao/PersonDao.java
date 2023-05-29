package ru.karelin.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.karelin.project.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {
    private final JdbcTemplate jdbcTemplate;

    private static final Person LIBRARY = new Person(0, "Library", "storage", 2006);

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        String SQL = "SELECT * FROM Person ORDER BY surname, name";
        List<Person> people = jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Person.class));

        return people;
    }

    public Person show(int id) {
        if(id == 0){
            return LIBRARY;
        }
        String SQL = "SELECT * FROM Person WHERE id = " + id;
        Person person = jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(new Person());

        return person;
    }

    public void save(Person person) {
        String SQL = "INSERT INTO Person(name, surname, yearOfBirth) VALUES(?, ?, ?)";
        jdbcTemplate.update(SQL, person.getName(), person.getSurname(), person.getYearOfBirth());
    }

    public void edit(Person person) {
        String SQL = "UPDATE Person SET name = ?, surname = ?, yearOfBirth = ? WHERE id = ?";
        jdbcTemplate.update(SQL, person.getName(), person.getSurname(), person.getYearOfBirth(), person.getId());
    }

    public void delete(int id) {
        String SQL = "DELETE FROM Person WHERE id = " + id;
        jdbcTemplate.update(SQL);
    }

    public Optional<Person> show(String name, String surname) {
        String SQL = "SELECT * FROM Person WHERE name = ? AND surname = ?";

        return jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Person.class), name, surname)
                .stream().findAny();
    }
}
