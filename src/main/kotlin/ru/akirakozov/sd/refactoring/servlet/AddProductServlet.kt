package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.addProduct
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author akirakozov
 */
class AddProductServlet : HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val name = request.getParameter("name")
        val price = request.getParameter("price").toLong()
        addProduct(name, price)

        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
        response.writer.println("OK")
    }
}
