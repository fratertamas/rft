package nye.rft.repository;

import java.util.ArrayList;
import java.util.List;

import nye.rft.model.Exam;
import nye.rft.model.UserRole;
import org.springframework.stereotype.Repository;

@Repository
public class ExamRepository {

    private final List<Exam> exams = new ArrayList<>();

    public boolean saveExam(Exam exam) {
        if (!exams.contains(exam) &&
                !exams.stream().anyMatch(c -> c.getId().compareTo(exam.getId()) == 0) &&
                exam.getUser().getRole() != UserRole.STUDENT) {
            exams.add(exam);
            return true;
        } else {
            return false;
        }
    }

    public List<Exam> getAllExams() {
        return exams;
    }

    public Exam findExamById(String examId) {
        for (Exam exam : exams) {
            if (exam.getId().equals(examId)) {
                return exam;
            }
        }
        return null;
    }
}
