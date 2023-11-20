package nye.rft.repository;

import nye.rft.model.Exam;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExamRepository {

    private List<Exam> exams = new ArrayList<>();

    public void saveExam(Exam exam) {
        exams.add(exam);
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

