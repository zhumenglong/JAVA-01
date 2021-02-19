package learn.beans;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Student {

    public Student(String name) {
        this.name = name;
    }

    String name;

}
