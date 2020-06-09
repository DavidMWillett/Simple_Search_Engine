package search

fun main() {
    val people = People()
    loop@ do {
        println("=== Menu ===")
        println("1. Find a person")
        println("2. Print all people")
        println("0. Exit")
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

class People {
    private val people: List<String> = {
        println("Enter the number of people:")
        val numPeople = readLine()!!.toInt()
        val people = mutableListOf<String>()
        println("Enter all people:")
        repeat(numPeople) {
            people.add(readLine()!!)
        }
        println()
        people
    }()

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