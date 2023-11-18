package nye.rft.service;

import nye.rft.model.Exam;
import nye.rft.model.User;
import nye.rft.repository.ExamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public void registerStudentForExam(String examId, User user) {
        Exam exam = examRepository.findExamById(examId);

        if (exam != null) {
            exam.registerStudent(user);
        } else {
            throw new IllegalArgumentException("The exam with the specified identifier could not be found.");
        }
    }
}
