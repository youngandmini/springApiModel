package goorm.apimodel.web.dto;


import goorm.apimodel.domain.Student;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class StudentDto {

    @NotBlank(message = "name을 제대로 입력해주세요")
    private String name;

    @Range(min = 1, max = 6, message = "grade의 범위는 1~6입니다.")
    private int grade;

    public StudentDto(Student student) {
        this.name = student.getName();
        this.grade = student.getGrade();
    }

}
