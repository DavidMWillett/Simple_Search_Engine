package search

import java.io.File

fun main(args: Array<String>) {
    val (option, parameter) = args.toList().zipWithNext().single()
    if (option == "--data") {
        val people = People(parameter)
        SearchEngine.execute(people)
    }
}

object SearchEngine {
    private val MENU = """
            === Menu ===
            1. Find a person
            2. Print all people
            0. Exit
    """.trimIndent()

    fun execute(people: People) {
        loop@ do {
            println(MENU)
            val choice = readLine()!!.toInt()
            println()
            when (choice) {
                1 -> people.search()
                2 -> people.list()
                0 -> break@loop
                else -> println("Incorrect option! Try again.")
            }
            println()
        } while (true)
        println("Bye!")
    }
}

class People(fileName: String) {
    private val people = File(fileName).readLines()

    fun search() {
        println("Enter a name or email to search all suitable people.")
        val search = readLine()!!
        val found = people.filter { it.contains(search, ignoreCase = true) }
        if (found.isNotEmpty()) {
            found.forEach { println(it) }
        } else {
            println("No matching people found.")
        }
    }

    fun list() {
        println("=== List of people ===")
        people.forEach { println(it) }
    }
}