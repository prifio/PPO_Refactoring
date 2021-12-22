package ru.akirakozov.sd.refactoring

import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

fun <T> withStatement(block: (Statement) -> T): T =
    DriverManager.getConnection("jdbc:sqlite:test.db").use { c ->
        c.createStatement().use(block)
    }

fun <T> executeQuery(what: String, query: String, handler: (ResultSet) -> T): T =
    withStatement { stmt ->
        stmt.executeQuery("SELECT $what FROM PRODUCT $query").use(handler)
    }

fun executeUpdate(sql: String) {
    withStatement { stmt ->
        stmt.executeUpdate(sql)
    }
}
