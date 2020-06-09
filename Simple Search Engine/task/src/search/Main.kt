package search

fun main() {
    val people = enterPeople()
    searchPeople(people)
}

fun enterPeople(): List<String> {
    println("Enter the number of people:")
    val numPeople = readLine()!!.toInt()
    val people = mutableListOf<String>()
    println("Enter all people:")
    repeat(numPeople) {
        people.add(readLine()!!)
    }
    println()
    return people
}

fun searchPeople(people: List<String>) {
    println("Enter the number of search queries:")
    val numQueries = readLine()!!.toInt()
    println()
    repeat(numQueries) {
        println("Enter data to search people:")
        val search = readLine()!!
        val found = people.filter { it.contains(search, ignoreCase = true) }
        if (found.isNotEmpty()) {
            println()
            println("Found people:")
            found.forEach { println(it) }
        } else {
            println("No matching people found.")
        }
        println()
    }
}
