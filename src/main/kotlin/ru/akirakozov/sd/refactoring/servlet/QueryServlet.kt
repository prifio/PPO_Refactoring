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
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val command = request.getParameter("command")
        if ("max" == command) {
            response.writer.println("<html><body>")
            response.writer.println("<h1>Product with max price: </h1>")
            getMax()?.let { (name, price) ->
                response.writer.println("$name\t$price</br>")
            }
            response.writer.println("</body></html>")
        } else if ("min" == command) {
            response.writer.println("<html><body>")
            response.writer.println("<h1>Product with min price: </h1>")
            getMin()?.let { (name, price) ->
                response.writer.println("$name\t$price</br>")
            }
            response.writer.println("</body></html>")
        } else if ("sum" == command) {
            response.writer.println("<html><body>")
            response.writer.println("Summary price: ")
            response.writer.println(getSum())
            response.writer.println("</body></html>")
        } else if ("count" == command) {
            response.writer.println("<html><body>")
            response.writer.println("Number of products: ")
            response.writer.println(getCount())
            response.writer.println("</body></html>")
        } else {
            response.writer.println("Unknown command: $command")
        }
        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
    }
}
