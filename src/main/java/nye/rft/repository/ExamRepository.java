package nye.rft.repository;

import nye.rft.model.Exam;

import java.util.ArrayList;
import java.util.List;

public class ExamRepository {

    private List<Exam> exams = new ArrayList<>();

    public void saveExam(Exam exam) {
        exams.add(exam);
    }

    public List<Exam> getAllExams() {
        return exams;
    }
}
