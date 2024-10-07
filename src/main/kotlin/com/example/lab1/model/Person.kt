package com.example.lab1.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "persons")
data class Person(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var name: String = "",
    var age: Int = 0,
    var address: String = "",
    var work: String = ""
) {
    constructor() : this(0, "", 0, "", "")
}
