package nye.rft.controller;

import java.util.List;

import nye.rft.model.Exam;
import nye.rft.model.User;
import nye.rft.service.ExamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/exams")
public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public ResponseEntity<String> createExam(@RequestBody Exam exam) {
        String response = examService.createExam(exam);
        if (response.contains("successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @GetMapping
    public List<Exam> getAllExams() {
        return examService.getAllExams();
    }

    @PostMapping("/{examId}")
    public ResponseEntity<String> registerStudentForExam(@PathVariable("examId") String examId, @RequestBody User user) {
        String response = examService.registerStudentForExam(examId, user);
        if (response.contains("successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
}

