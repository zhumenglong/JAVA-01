package learn.beans;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class School {

    public School(String name) {
        this.name = name;
    }

    String name;

    List<Klass> classes;
}
