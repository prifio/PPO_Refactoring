package ru.akirakozov.sd.refactoring

import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

private fun <T> withStatement(block: (Statement) -> T): T =
    DriverManager.getConnection("jdbc:sqlite:test.db").use { c ->
        c.createStatement().use(block)
    }

private fun <T> executeQuery(what: String, query: String, handler: (ResultSet) -> T): T =
    withStatement { stmt ->
        stmt.executeQuery("SELECT $what FROM PRODUCT $query").use(handler)
    }

private fun executeUpdate(sql: String) {
    withStatement { stmt ->
        stmt.executeUpdate(sql)
    }
}

private fun <T> getList(what: String, query: String, extractor: (ResultSet) -> T): List<T> =
    executeQuery(what, query) { rs ->
        val lst = mutableListOf<T>()
        while (rs.next()) {
            lst += extractor(rs)
        }
        lst
    }

private fun defaultExtractor(rs: ResultSet): Pair<String, Long> =
    rs.getString("name") to rs.getLong("price")

fun initDb() {
    val sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)"
    executeUpdate(sql)
}

fun addProduct(name: String, price: Long) {
    val sql = "INSERT INTO PRODUCT " +
            "(NAME, PRICE) VALUES (\"$name\", $price)"
    executeUpdate(sql)
}

fun getProducts(): List<Pair<String, Long>> = getList("*", "", ::defaultExtractor)

fun getMax(): Pair<String, Long>? =
    getList("*", "ORDER BY PRICE DESC LIMIT 1", ::defaultExtractor).firstOrNull()

fun getMin(): Pair<String, Long>? =
    getList("*", "ORDER BY PRICE LIMIT 1", ::defaultExtractor).firstOrNull()

fun getSum(): Long =
    getList("SUM(price)", ""){ rs -> rs.getLong(1) }.firstOrNull() ?: 0

fun getCount(): Int =
    getList("COUNT(*)", ""){ rs -> rs.getInt(1) }.firstOrNull() ?: 0
