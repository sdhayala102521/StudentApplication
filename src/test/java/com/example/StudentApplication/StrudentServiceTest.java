import com.example.StudentApplication.Services.StudentService;
import com.example.StudentApplication.model.Student;
import com.example.StudentApplication.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        student = Student.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();
    }

    @Test
    void testCreateStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        Student created = studentService.createStudent(student);
        assertEquals(student, created);
        verify(studentRepository).save(student);
    }

    @Test
    void testGetAllStudents() {
        List<Student> students = List.of(student);
        when(studentRepository.findAll()).thenReturn(students);
        List<Student> result = studentService.getAllStudents();
        assertEquals(students, result);
        verify(studentRepository).findAll();
    }

    @Test
    void testGetStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Optional<Student> result = studentService.getStudentById(1L);
        assertTrue(result.isPresent());
        assertEquals(student, result.get());
        verify(studentRepository).findById(1L);
    }

    @Test
    void testUpdateStudent() {
        Student updatedDetails = Student.builder()
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedDetails);

        Student updated = studentService.updateStudent(1L, updatedDetails);
        assertEquals(updatedDetails.getName(), updated.getName());
        assertEquals(updatedDetails.getEmail(), updated.getEmail());
        verify(studentRepository).findById(1L);
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void testUpdateStudentNotFound() {
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());
        Student details = Student.builder().name("X").email("x@example.com").build();
        assertThrows(RuntimeException.class, () -> studentService.updateStudent(2L, details));
        verify(studentRepository).findById(2L);
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(studentRepository).deleteById(1L);
        studentService.deleteStudent(1L);
        verify(studentRepository).deleteById(1L);
    }
}