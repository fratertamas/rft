package nye.rft.controller;



import nye.rft.model.Exam;
import nye.rft.model.User;
import nye.rft.model.UserRole;
import nye.rft.repository.ExamRepository;
import nye.rft.service.ExamService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ExamControllerTest {

    private Calendar calendar = Calendar.getInstance();
    private static final String EXAM_ID = "exam1";
    private static final String TEACHER_ID = "teacher1";
    private static final String TEACHER_NAME = "Kovacs Aladar";
    private static final String STUDENT_ID = "student1";
    private static final String STUDENT_NAME = "Kovacs Adel";
    private static final String COURSE_NAME = "course1";
    private static final String LOCATION = "location1";
    private static final int MAX_STUDENT_NUMBER = 10;
    private static Date EXAM_DATE;

    private AutoCloseable closeable;

    @Mock
    ExamService examService;

    @Mock
    ExamRepository examRepository;
    @InjectMocks
    private ExamController underTest;

    @BeforeEach
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        calendar.set(2022,04,03);
        calendar.set(Calendar.MILLISECOND, 0);
        EXAM_DATE = calendar.getTime();
    }
    @AfterEach
    void tearDown()  {
        try {
            closeable.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createExam() {
        //Given
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        Exam exam =new Exam(EXAM_ID,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER);
        //When
        underTest.createExam(exam);
        //Then
        verify(examService, times(1)).createExam(exam);
    }

    @Test
    void getAllExams() {
        //Given
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        Exam exam =new Exam(EXAM_ID,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER);
        List<Exam> expected = List.of(exam);
        given(examService.getAllExams()).willReturn(expected);
        //When
        List<Exam> actual= underTest.getAllExams();
        //Then
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void registerStudentForExam() {
        //Given
        User student = new User(STUDENT_ID,STUDENT_NAME, UserRole.STUDENT);
        //When
        underTest.registerStudentForExam(EXAM_ID,student);
        //Then
        verify(examService, times(1)).registerStudentForExam(EXAM_ID,student);
    }
}