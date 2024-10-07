package com.example.lab1

import com.example.lab1.model.Person
import com.example.lab1.repository.PersonRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Test
    fun `should return all persons`() {
        mockMvc.perform(get("/api/v1/persons"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name").value("Alena"))
    }

    @Test
    fun `should create a new person`() {
        val person = Person(
            name = "Hakan",
            age = 30,
            address = "Street 123",
            work = "Engineer"
        )
        val personJson = objectMapper.writeValueAsString(person)

        mockMvc.perform(post("/api/v1/persons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(personJson))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.name").value("Hakan"))
    }

    @Test
    fun `should return 400 if name is missing`() {
        val personJson = """
            {
              "age": 30,
              "address": "Street 123",
              "work": "Engineer"
            }
        """
        mockMvc.perform(post("/api/v1/persons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(personJson))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should delete person by id`() {
        val person = Person(
            name = "DeleteMe",
            age = 28,
            address = "Some Address",
            work = "Job"
        )
        val savedPerson = personRepository.save(person)

        mockMvc.perform(delete("/api/v1/persons/${savedPerson.id}"))
            .andExpect(status().isNoContent)
    }
}
