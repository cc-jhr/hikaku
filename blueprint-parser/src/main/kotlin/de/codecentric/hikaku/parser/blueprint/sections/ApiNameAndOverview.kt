package de.codecentric.hikaku.parser.blueprint.sections

import com.vladsch.flexmark.util.ast.Node
import de.codecentric.hikaku.parser.blueprint.extensions.toPlainText


class ApiNameAndOverviewSection(node: Node) {
    val name: String = node.toPlainText()
    val description: String = node.next.toPlainText()
}