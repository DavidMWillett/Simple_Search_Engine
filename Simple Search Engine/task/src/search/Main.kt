package search

import java.util.Scanner

fun main() {
    val database = readLine()!!
    val keyword = readLine()!!

    val scanner = Scanner(database)
    var index = 0
    while (scanner.hasNext()) {
        index++
        if (scanner.next() == keyword) {
            println(index)
            return
        }
    }
    println("Not found")
}
