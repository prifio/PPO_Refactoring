package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.executeQuery
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author akirakozov
 */
class GetProductsServlet : HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        executeQuery("*", ""){ rs ->
            response.writer.println("<html><body>")
            while (rs.next()) {
                val name = rs.getString("name")
                val price = rs.getInt("price")
                response.writer.println("$name\t$price</br>")
            }
            response.writer.println("</body></html>")
        }

        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
    }
}
