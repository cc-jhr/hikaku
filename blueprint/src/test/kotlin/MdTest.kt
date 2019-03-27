import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class MdTest {

    private val childs: MutableList<Node> = mutableListOf()

    @Test
    fun `small test`() {
        //given
        val file = Paths.get(this::class.java.classLoader.getResource("specification_examples/api_name_and_overview_section.md").toURI())

        val content = Files.readAllLines(file).joinToString("\n")

        val parser = Parser.builder().build()
        val document = parser.parse(content)

        iterateOverAll(document.firstChild, 0)
    }

    fun iterateOverAll(node: Node, layer: Int) {
        printchild(node, layer)

        if (node.next != null) {
            iterateOverAll(node.next, layer)
        }
    }

    fun printchild(node: Node, layer: Int) {
        println("${" ".repeat(layer)}$node")

        if (node.firstChild != null) {
            iterateOverAll(node.firstChild, layer + 2)
        }
    }



    /*

        fun iterateOverAll(node: Node, layer: Int) {
        printchild(node, layer)

        if (node.next != null) {
            iterateOverAll(node.next, 0)
        }
    }

    fun printchild(node: Node, layer: Int) {
        println("${" ".repeat(layer)}$node")

        if (node.firstChild != null) {
            printchild(node.firstChild, layer + 2)
        }
    }
     */
}