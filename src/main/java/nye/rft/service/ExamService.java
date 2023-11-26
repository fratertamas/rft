package nye.rft.service;

import java.util.List;

import nye.rft.model.Exam;
import nye.rft.model.User;
import nye.rft.repository.ExamRepository;
import org.springframework.stereotype.Service;

@Service
public class ExamService {
    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public String createExam(Exam exam) {
        boolean isSaved = examRepository.saveExam(exam);
        if (isSaved) {
            return "Exam successfully created.";
        } else {
            return "The exam already exists.";
        }
    }

    public List<Exam> getAllExams() {
        return examRepository.getAllExams();
    }

    public String registerStudentForExam(String examId, User user) {
        Exam exam = examRepository.findExamById(examId);

        if (exam != null) {
            boolean isSaved = exam.registerStudent(user);
            if (isSaved) {
                return "Student successfully registered.";
            } else {
                return "The student already registered.";
            }
        } else {
            throw new IllegalArgumentException("The exam with the specified identifier could not be found.");
        }
    }
}