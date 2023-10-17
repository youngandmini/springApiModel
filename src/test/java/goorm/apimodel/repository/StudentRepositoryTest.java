package goorm.apimodel.repository;

import goorm.apimodel.domain.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class StudentRepositoryTest {

    StudentRepository studentRepository = new StudentRepository();

    @Test
    void save() {
        //given
        Student student = new Student("학생1", 1);
        //when
        Long saveId = studentRepository.save(student);
        //then
        assertThat(saveId).isEqualTo(student.getId());
    }

    @Test
    void saveStudents() {
        //given
        Student student1 = new Student("학생1", 1);
        Student student2 = new Student("학생2", 2);
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        //when
        int resultCount = studentRepository.saveStudents(students);

        //then
        assertThat(resultCount).isEqualTo(students.size());
    }

    @Test
    void findAll() {
        //given
        Student student1 = new Student("학생1", 1);
        Student student2 = new Student("학생2", 2);
        Student student3 = new Student("학생3", 3);
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        //when
        int resultCount = studentRepository.saveStudents(students);
        List<Student> findStudents = studentRepository.findAll();

        //then
        assertThat(findStudents.contains(student1)).isTrue();
        assertThat(findStudents.contains(student2)).isTrue();
        assertThat(findStudents.contains(student3)).isFalse();
        assertThat(findStudents.size()).isEqualTo(resultCount);

        //순서확인
        assertThat(findStudents.get(0)).isEqualTo(student1);
        assertThat(findStudents.get(1)).isEqualTo(student2);
    }

    @Test
    void findByGrade() {
        //given
        Student student1 = new Student("학생1", 1);
        Student student2 = new Student("학생2", 2);
        Student student3 = new Student("학생3", 2);
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);

        //when
        int resultCount = studentRepository.saveStudents(students);
        List<Student> findGrade1Students = studentRepository.findByGrade(1);
        List<Student> findGrade2Students = studentRepository.findByGrade(2);

        //then
        assertThat(findGrade1Students.contains(student1)).isTrue();
        assertThat(findGrade1Students.contains(student2)).isFalse();
        assertThat(findGrade1Students.contains(student3)).isFalse();

        assertThat(findGrade2Students.contains(student1)).isFalse();
        assertThat(findGrade2Students.contains(student2)).isTrue();
        assertThat(findGrade2Students.contains(student3)).isTrue();

        assertThat(findGrade1Students.size()).isEqualTo(1);
        assertThat(findGrade2Students.size()).isEqualTo(2);
    }

}