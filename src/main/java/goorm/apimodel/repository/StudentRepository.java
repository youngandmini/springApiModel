package goorm.apimodel.repository;


import goorm.apimodel.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StudentRepository {

    private static final Map<Long, Student> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    public Long save(Student student) {
        store.put(++sequence, student);
        student.setId(sequence);

        return sequence;
    }

    public int saveStudents(List<Student> students) {
        for (Student student : students) {
            save(student);
        }

        return students.size();
    }

    public List<Student> findAll() {
        return store.values().stream()
                .sorted(Comparator.comparing(Student::getGrade))
                .collect(Collectors.toList());
    }

    public List<Student> findByGrade(int grade) {
        return store.values().stream()
                .filter(s -> s.getGrade()==grade)
                .collect(Collectors.toList());
    }
}
