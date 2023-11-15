package nye.rft.controller;

import nye.rft.service.ExamService;

public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }
}
