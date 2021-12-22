package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.executeQuery
import java.sql.DriverManager
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
            executeQuery("*", "ORDER BY PRICE DESC LIMIT 1") { rs ->
                response.writer.println("<html><body>")
                response.writer.println("<h1>Product with max price: </h1>")
                while (rs.next()) {
                    val name = rs.getString("name")
                    val price = rs.getInt("price")
                    response.writer.println("$name\t$price</br>")
                }
                response.writer.println("</body></html>")
            }
        } else if ("min" == command) {
            executeQuery("*", "ORDER BY PRICE LIMIT 1") { rs ->
                response.writer.println("<html><body>")
                response.writer.println("<h1>Product with min price: </h1>")
                while (rs.next()) {
                    val name = rs.getString("name")
                    val price = rs.getInt("price")
                    response.writer.println("$name\t$price</br>")
                }
                response.writer.println("</body></html>")
            }
        } else if ("sum" == command) {
            executeQuery("SUM(price)", "") { rs ->
                response.writer.println("<html><body>")
                response.writer.println("Summary price: ")
                if (rs.next()) {
                    response.writer.println(rs.getInt(1))
                }
                response.writer.println("</body></html>")
            }
        } else if ("count" == command) {
            executeQuery("COUNT(*)", "") { rs ->
                response.writer.println("<html><body>")
                response.writer.println("Number of products: ")
                if (rs.next()) {
                    response.writer.println(rs.getInt(1))
                }
                response.writer.println("</body></html>")
            }
        } else {
            response.writer.println("Unknown command: $command")
        }
        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
    }
}
