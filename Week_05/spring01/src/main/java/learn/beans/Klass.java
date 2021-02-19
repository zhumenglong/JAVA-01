package learn.beans;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Klass {

    public Klass(int grade) {
        this.grade = grade;
    }

    int grade;

    List<Student> students;
}
