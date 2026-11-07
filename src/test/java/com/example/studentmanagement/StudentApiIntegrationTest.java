package com.example.studentmanagement;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
class StudentApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
    }

    private static Date date(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month - 1, day);
        return calendar.getTime();
    }

    @Test
    void saveThenFindAllAndCount() throws Exception {
        mockMvc.perform(post("/students/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"LACHGAR\",\"prenom\":\"Mohamed\",\"dateNaissance\":\"1985-09-01\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nom").value("LACHGAR"));

        mockMvc.perform(get("/students/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(get("/students/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    void saveInvalidStudentReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/students/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"\",\"prenom\":\"Mohamed\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteExistingAndUnknownStudent() throws Exception {
        Student saved = studentRepository.save(new Student("DANI", "Mohamed", date(1990, 5, 12)));

        mockMvc.perform(delete("/students/delete/" + saved.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/students/delete/" + saved.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void countStudentsByYear() throws Exception {
        studentRepository.save(new Student("A", "A", date(1985, 1, 1)));
        studentRepository.save(new Student("B", "B", date(1985, 2, 2)));
        studentRepository.save(new Student("C", "C", date(1990, 3, 3)));

        mockMvc.perform(get("/students/byYear"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0][0]").value(1985))
                .andExpect(jsonPath("$[0][1]").value(2))
                .andExpect(jsonPath("$[1][0]").value(1990))
                .andExpect(jsonPath("$[1][1]").value(1));
    }

    @Test
    void swaggerUiAndApiDocsAreExposed() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("Student Management API"))
                .andExpect(jsonPath("$.paths['/students/all']").exists());

        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk());
    }
}
