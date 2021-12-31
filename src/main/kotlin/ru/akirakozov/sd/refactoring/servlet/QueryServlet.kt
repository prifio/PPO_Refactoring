package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.getCount
import ru.akirakozov.sd.refactoring.getMax
import ru.akirakozov.sd.refactoring.getMin
import ru.akirakozov.sd.refactoring.getSum
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author akirakozov
 */
class QueryServlet : HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) =
        doResponse(response) {
            val command = request.getParameter("command")
            if (command == "max" || command == "min") {
                println("<h1>Product with $command price: </h1>")
                (if (command == "max") getMax() else getMin())?.let { (name, price) ->
                    println("$name\t$price</br>")
                }
            } else if ("sum" == command) {
                println("Summary price: ")
                println(getSum())
            } else if ("count" == command) {
                println("Number of products: ")
                println(getCount())
            } else {
                println("Unknown command: $command")
            }
        }
}
