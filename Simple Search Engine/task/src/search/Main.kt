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
    private val index = Index(people)

    fun search() {
        println("Enter a name or email to search all suitable people.")
        val searchTerm = readLine()!!
        val lineNums = index.search(searchTerm)
        if (lineNums == null) {
            println("No matching people found.")
        } else {
            println("${lineNums.size} persons found:")
            lineNums.forEach { println(people[it]) }
        }
    }

    fun list() {
        println("=== List of people ===")
        people.forEach { println(it) }
    }
}

class Index(dataset: List<String>) {
    private val entries = create(dataset)

    private fun create(dataset: List<String>): Map<String, MutableList<Int>> {
        val entries = mutableMapOf<String, MutableList<Int>>()
        dataset.forEachIndexed { lineNum, line ->
            line.split(" ").forEach { word ->
                val lcWord = word.toLowerCase()
                val entry = entries[lcWord]
                if (entry == null) {
                    entries[lcWord] = mutableListOf(lineNum)
                } else {
                    entry.add(lineNum)
                }
            }
        }
        return entries
    }

    fun search(searchTerm: String): List<Int>? {
        return entries[searchTerm.toLowerCase()]
    }
}