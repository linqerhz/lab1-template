package com.example.lab1

import com.example.lab1.model.Person
import com.example.lab1.repository.PersonRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/v1/persons")
class PersonController(private val personRepository: PersonRepository) {

    @GetMapping
    fun listPersons(): ResponseEntity<List<Person>> {
        val persons = personRepository.findAll()
        return ResponseEntity.ok(persons)
    }

    @PostMapping
    fun createPerson(@RequestBody request: Person): ResponseEntity<Person> {
        val newPerson = personRepository.save(request)
        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newPerson.id)
            .toUri()

        return ResponseEntity.created(location).body(newPerson)
    }

    @GetMapping("/{id}")
    fun getPerson(@PathVariable id: Long): ResponseEntity<Person> {
        val person = personRepository.findById(id)
        return if (person.isPresent) {
            ResponseEntity.ok(person.get())
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PatchMapping("/{id}")
    fun updatePerson(@PathVariable id: Long, @RequestBody updatedPerson: Person): ResponseEntity<Person> {
        var existingPerson = personRepository.findById(id)

        return if (existingPerson.isPresent) {
            var person = existingPerson.get()

            person.name = updatedPerson.name.takeIf { it.isNotEmpty() } ?: person.name
            person.address = updatedPerson.address.takeIf { it.isNotEmpty() } ?: person.address
            person.age = updatedPerson.age.takeIf { it > 0 } ?: person.age
            person.work = updatedPerson.work.takeIf { it.isNotEmpty() } ?: person.work

            var updated = personRepository.save(person)
            ResponseEntity.ok(updated)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deletePerson(@PathVariable id: Long): ResponseEntity<Unit> {
        return if (personRepository.existsById(id)) {
            personRepository.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}