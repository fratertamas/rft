package nye.rft.service;

import nye.rft.model.Exam;
import nye.rft.repository.ExamRepository;

import java.util.List;

public class ExamService {
    private ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public void createExam(Exam exam) {
        examRepository.saveExam(exam);
    }

    public List<Exam> getAllExams() {
        return examRepository.getAllExams();
    }
}
