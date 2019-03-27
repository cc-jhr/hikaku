package de.codecentric.hikaku.parser.blueprint.extensions

import com.vladsch.flexmark.ast.Heading
import com.vladsch.flexmark.ast.Paragraph
import com.vladsch.flexmark.ast.util.TextCollectingVisitor
import com.vladsch.flexmark.util.ast.Node

fun Node.toPlainText() = TextCollectingVisitor().collectAndGetText(this)?.trim() ?: ""

fun Node.isMetaDataSection() = this is Paragraph && this.toPlainText().matches(Regex("(.+?:.+\\n?)*"))

fun Node.isApiNameAndOverviewSection() = this is Heading && this.next is Paragraph

fun Node.isResourceSection(): Boolean {
    val heading = this.toPlainText()

    var isHeading = this is Heading

    if (isHeading && heading.matches(Regex("(/.+?)*"))) {
        return true
    }

    if (isHeading && heading.matches(Regex("(.+? \\[/.+?)*\\]"))) {
        return true
    }
/*
    val splittedHeading = this.toPlainText().split(" ", limit = 2)
    val startsWithPath = splittedHeading.first().matches(Regex("[ -~]*"))
*/
    return false
}