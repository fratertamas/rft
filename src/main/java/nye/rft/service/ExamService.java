package nye.rft.service;

import nye.rft.repository.ExamRepository;

public class ExamService {
    private ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }
}
