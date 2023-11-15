package nye.rft.controller;

import nye.rft.model.Exam;
import nye.rft.model.User;
import nye.rft.service.ExamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }
    @PostMapping
    public void createExam(@RequestBody Exam exam) {
        examService.createExam(exam);
    }

    @GetMapping
    public List<Exam> getAllExams() {
        return examService.getAllExams();
    }

    @PostMapping("/{examId}")
    public void registerStudentForExam(@PathVariable("examId") String examId, @RequestBody User user) {
        examService.registerStudentForExam(examId, user);
    }
}
