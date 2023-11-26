package nye.rft.service;


import nye.rft.model.Exam;
import nye.rft.model.User;
import nye.rft.model.UserRole;
import nye.rft.repository.ExamRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    ExamRepository examRepository;

    @InjectMocks
    ExamService underTest;

    @BeforeEach
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        calendar.set(2022,04,03);
        calendar.set(Calendar.MILLISECOND, 0);
        EXAM_DATE = calendar.getTime();
        underTest = new ExamService(examRepository);
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
    public void createExam_ShouldReturnPositiveMessage_WhenTheExamCanBeAdd() {
        //Given
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        Exam exam = new Exam(EXAM_ID,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER);
        Mockito.when(examRepository.saveExam(Mockito.any(Exam.class))).thenReturn(true);
        // When
        String result = underTest.createExam(exam);

        // Then
        Assertions.assertEquals("Exam successfully created.", result);
    }

    @Test
    public void createExam_ShouldReturnNegativeMessage_WhenTheExamCannotBeAdd() {
        //Given
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        Exam exam = new Exam(EXAM_ID,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER);
        Mockito.when(examRepository.saveExam(Mockito.any(Exam.class))).thenReturn(false);
        // When
        String result = underTest.createExam(exam);
        // Then
        Assertions.assertEquals("The exam already exists.", result);
    }


    @Test
    void getAllExams_ShouldReturnRegisteredExams_WhenCalled() {
        //Given
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        List<Exam> expected= new ArrayList<>(List.of(new Exam(EXAM_ID,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER))) ;
        Mockito.when(examRepository.getAllExams()).thenReturn(expected);
        //when(examRepository.getAllExams()).thenReturn(expected);
        //When
        List<Exam> actual = underTest.getAllExams();
        //Then
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void registerStudentForExam_ShouldAddTheStudentToTheExam_WhenTheExamExistsAndTheStudentNotAlreadyRegistered() {
       //Given
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        Exam exam= new Exam(EXAM_ID,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER) ;
        User student = new User(STUDENT_ID,STUDENT_NAME, UserRole.STUDENT);
        Mockito.when(examRepository.findExamById(EXAM_ID)).thenReturn(exam);
        //When
        String actual = underTest.registerStudentForExam(EXAM_ID,student);
        //Then
        Assertions.assertEquals("Student successfully registered.", actual);
    }

    @Test
    void registerStudentForExam_ShouldNotAddTheStudentToTheExam_WhenTheExamExistsButTheStudentAlreadyRegistered() {
        //Given
        User teacher = new User(TEACHER_ID,TEACHER_NAME, UserRole.TEACHER);
        Exam exam= new Exam(EXAM_ID,EXAM_DATE,COURSE_NAME,LOCATION,teacher,MAX_STUDENT_NUMBER) ;
        User student = new User(STUDENT_ID,STUDENT_NAME, UserRole.STUDENT);
        Mockito.when(examRepository.findExamById(EXAM_ID)).thenReturn(exam);
        underTest.registerStudentForExam(EXAM_ID,student);
        //When
        String actual = underTest.registerStudentForExam(EXAM_ID,student);
        //Then
        Assertions.assertEquals("The student already registered.", actual);
    }

    @Test
    void registerStudentForExam_ShouldThrowIllegalArgumentException_WhenTheExamDoesNotExist() {
        //Given
        Exam exam = null ;
        User student = new User(STUDENT_ID,STUDENT_NAME, UserRole.STUDENT);
        Mockito.when(examRepository.findExamById(EXAM_ID)).thenReturn(exam);
        //When--Then
        assertThrows(IllegalArgumentException.class,() -> underTest.registerStudentForExam(EXAM_ID,student));
    }

}