package nye.rft.controller;

import nye.rft.model.Exam;
import nye.rft.service.ExamService;

public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    public void createExam(Exam exam) {
        examService.createExam(exam);
    }
}
