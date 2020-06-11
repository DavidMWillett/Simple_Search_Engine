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
                1 -> {
                    val strategy = selectStrategy() ?: continue@loop
                    people.search(strategy)
                }
                2 -> people.list()
                0 -> break@loop
                else -> println("Incorrect option! Try again.")
            }
            println()
        } while (true)
        println("Bye!")
    }

    private fun selectStrategy(): Strategy? {
        println("Select a matching strategy: ALL, ANY, NONE")
        val strategy = when (readLine()!!) {
            "ALL" -> Strategy.ALL
            "ANY" -> Strategy.ANY
            "NONE" -> Strategy.NONE
            else -> null
        }
        println()
        return strategy
    }
}

class People(fileName: String) {
    private val people = File(fileName).readLines()
    private val index = Index(people)

    fun search(strategy: Strategy) {
        println("Enter a name or email to search all suitable people.")
        val terms = readLine()!!.toLowerCase().split(" ")
        val lines = index.search(terms, strategy)
        if (lines.isNotEmpty()) {
            println("${lines.size} persons found:")
            lines.forEach { println(people[it]) }
        } else {
            println("No matching people found.")
        }
    }

    fun list() {
        println("=== List of people ===")
        people.forEach { println(it) }
    }
}

class Index(dataset: List<String>) {
    private val index = mutableMapOf<String, MutableSet<Int>>()

    init {
        dataset.forEachIndexed { line, text -> text.split(" ").forEach { word -> add(word, line) } }
    }

    private fun add(term: String, line: Int) {
        index.getOrPut(term.toLowerCase()) { mutableSetOf() }.add(line)
    }

    fun search(terms: List<String>, strategy: Strategy) = strategy.search(index, terms)
}

enum class Strategy {
    ALL {
        override fun search(index: Map<String, Set<Int>>, terms: List<String>) =
                terms.map { index[it].orEmpty() }.reduce { acc, set -> acc intersect set }
    },
    ANY {
        override fun search(index: Map<String, Set<Int>>, terms: List<String>) =
                terms.map { index[it].orEmpty() }.reduce { acc, set -> acc union set }
    },
    NONE {
        override fun search(index: Map<String, Set<Int>>, terms: List<String>) =
                terms.map { index[it].orEmpty() }.fold(index.values.flatten().toSet()) { acc, set -> acc - set }
    };

    abstract fun search(index: Map<String, Set<Int>>, terms: List<String>): Set<Int>
}
