package de.codecentric.hikaku.parser.blueprint.sections

import com.vladsch.flexmark.ast.Text
import com.vladsch.flexmark.util.ast.Node

class MetaDataSection(private val node: Node) {

    val entries: Map<String, String> by lazy {
        parseEntries()
    }

    private val literals = mutableListOf<String>()

    private fun parseEntries(): Map<String, String> {
        val metaData = mutableMapOf<String, String>()

        fetchAllLiterals(node.firstChild)

        literals.map {
            it.split(":", limit = 2)
        }
        .forEach {
            val key = it[0].trim()
            val value = it[1].trim()
            metaData[key] = value
        }

        return metaData
    }

    private fun fetchAllLiterals(node: Node) {
        if (node is Text) {
            literals.add(node.chars.toString())
        }

        if (node.next != null) {
            fetchAllLiterals(node.next)
        }
    }
}