package de.codecentric.hikaku.parser.blueprint

import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.ast.Node
import de.codecentric.hikaku.parser.blueprint.extensions.isApiNameAndOverviewSection
import de.codecentric.hikaku.parser.blueprint.extensions.isMetaDataSection
import de.codecentric.hikaku.parser.blueprint.extensions.isResourceSection
import de.codecentric.hikaku.parser.blueprint.sections.ApiNameAndOverviewSection
import de.codecentric.hikaku.parser.blueprint.sections.BlueprintDocument
import de.codecentric.hikaku.parser.blueprint.sections.MetaDataSection
import de.codecentric.hikaku.parser.blueprint.sections.ResourceSection
import java.lang.IllegalStateException
import java.nio.file.Files
import java.nio.file.Path

class BlueprintParser(private val file: Path) {

    private val rootBlocks = mutableListOf<Node>()
    private val blueprintDocument = BlueprintDocument()

    fun parse(): BlueprintDocument {
        val content = Files.readAllLines(file).joinToString("\n")
        val document = Parser.builder().build().parse(content)
        findRootBlocks(document)

        var currentNodeIndex = 0

        // MetaDataSection must have the first position
        if (nodeExists(currentNodeIndex) && rootBlocks[currentNodeIndex].isMetaDataSection()) {
            blueprintDocument.metaDataSection = MetaDataSection(rootBlocks[currentNodeIndex])
            currentNodeIndex++
        }

        // ApiNameAndOverviewSection must be the first Heading
        if (nodeExists(currentNodeIndex) && !rootBlocks[currentNodeIndex].isResourceSection() && rootBlocks[currentNodeIndex].isApiNameAndOverviewSection()) {
            blueprintDocument.apiNameAndOverview = ApiNameAndOverviewSection(rootBlocks[currentNodeIndex])
            currentNodeIndex++
        }

        for (index in currentNodeIndex until rootBlocks.size) {
            if (nodeExists(index) && rootBlocks[index].isResourceSection()) {
                blueprintDocument.resources.add(ResourceSection(rootBlocks[index]))
            }else if () {
                // TODO ResourceGroup
            } else if () {
                //TODO DATA BLOCK
            } else {
                throw IllegalStateException("")
            }
        }

        return blueprintDocument
    }

    private fun nodeExists(index: Int) = rootBlocks.size -1  >= index

    private fun findRootBlocks(block: Node) {
        rootBlocks.add(block.firstChild)

        if (block.next != null) {
            findRootBlocks(block.next)
        }
    }
}