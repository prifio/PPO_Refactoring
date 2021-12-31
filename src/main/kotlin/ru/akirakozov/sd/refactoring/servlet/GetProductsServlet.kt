package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.getProducts
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author akirakozov
 */
class GetProductsServlet : HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) =
        doResponse(response) {
            getProducts().forEach { (name, price) ->
                println("$name\t$price</br>")
            }
        }
}
