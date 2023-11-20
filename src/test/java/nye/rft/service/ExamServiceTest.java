package nye.rft.service;



import nye.rft.model.Exam;
import nye.rft.model.User;
import nye.rft.model.UserRole;
import nye.rft.repository.ExamRepository;
import nye.rft.service.ExamService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)

class ExamServiceTest {

    private final Calendar calendar = Calendar.getInstance();
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
    private ExamRepository examRepository;
    @InjectMocks
    private ExamService underTest;

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
    public void test() {
        //Given
        assertTrue(true);
    }


    @Test
    public void testCreateExam() {
        //Given
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        Exam exam = new Exam(EXAM_ID,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER);
        //When
        underTest.createExam(exam);
        //Then
        verify(examRepository, times(1)).saveExam(exam);
    }

    @Test
    void getAllExams() {
        //Given
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        List<Exam> expected= new ArrayList<>(List.of(new Exam(EXAM_ID,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER))) ;
        given(examRepository.getAllExams()).willReturn(expected);
        //when(examRepository.getAllExams()).thenReturn(expected);
        //When
        List<Exam> actual = underTest.getAllExams();
        //Then
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void registerStudentForExam1() {
       //Given
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        Exam exam= new Exam(EXAM_ID,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER) ;
        User student = new User(STUDENT_ID,STUDENT_NAME, UserRole.STUDENT);
        given(examRepository.findExamById(EXAM_ID)).willReturn(exam);
        //When
        underTest.registerStudentForExam(EXAM_ID,student);
        //Then
        assertTrue(exam.getRegisteredStudents().contains(student));
    }


    @Test
    void registerStudentForExam2() {
        //Given
        Exam exam = null ;
        User student = new User(STUDENT_ID,STUDENT_NAME, UserRole.STUDENT);
        given(examRepository.findExamById(EXAM_ID)).willReturn(exam);
        //When--Then
        assertThrows(IllegalArgumentException.class,() -> underTest.registerStudentForExam(EXAM_ID,student));
    }

}