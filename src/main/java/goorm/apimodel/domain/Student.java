package goorm.apimodel.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class Student {
    private Long id;

    private String name;

    private int grade;

    public Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
