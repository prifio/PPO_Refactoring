package ru.akirakozov.sd.refactoring

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet
import ru.akirakozov.sd.refactoring.servlet.QueryServlet

/**
 * @author akirakozov
 */

fun startServer(args: Array<String>): Server {
    val sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)"
    executeUpdate(sql)

    val server = Server(8081)
    val context = ServletContextHandler(ServletContextHandler.SESSIONS)
    context.contextPath = "/"
    server.handler = context
    context.addServlet(ServletHolder(AddProductServlet()), "/add-product")
    context.addServlet(ServletHolder(GetProductsServlet()), "/get-products")
    context.addServlet(ServletHolder(QueryServlet()), "/query")
    server.start()
    return server
}

fun main(args: Array<String>) {
    val server = startServer(args)
    server.join()
}
