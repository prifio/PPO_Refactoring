package ru.akirakozov.sd.refactoring.servlet

import java.io.PrintWriter
import javax.servlet.http.HttpServletResponse

fun doResponse(response: HttpServletResponse, block: PrintWriter.() -> Unit) {
    with(response.writer) {
        println("<html><body>")
        block()
        println("</body></html>")
    }
    response.contentType = "text/html"
    response.status = HttpServletResponse.SC_OK
}
