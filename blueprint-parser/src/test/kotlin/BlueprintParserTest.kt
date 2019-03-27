import de.codecentric.hikaku.parser.blueprint.BlueprintParser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class BlueprintParserTest {

    @Test
    fun `meta data section`() {
        //given
        val file = Paths.get(this::class.java.classLoader.getResource("specification_examples/metadata_section.md").toURI())
        val expectedMetaDataSection = mutableMapOf(
            "FORMAT" to "1A",
            "HOST" to "http://blog.acme.com"
        )

        //when
        val result = BlueprintParser(file).parse()

        //then
        assertThat(result.metaDataSection?.entries).containsAllEntriesOf(expectedMetaDataSection)
    }

    @Test
    fun `api name and overview section`() {
        //given
        val file = Paths.get(this::class.java.classLoader.getResource("specification_examples/api_name_and_overview_section.md").toURI())
        val name = "Basic ACME Blog API"
        val description = "Welcome to the ACME Blog API. This API provides access to the ACME\nBlog service."

        //when
        val result = BlueprintParser(file).parse()

        //then
        assertThat(result.apiNameAndOverview?.name).isEqualTo(name)
        assertThat(result.apiNameAndOverview?.description).isEqualTo(description)
    }

    @Nested
    inner class ResourceSectionTests {

        @Test
        fun `defined by path`() {
            //given
            val file = Paths.get(this::class.java.classLoader.getResource("specification_examples/resource_section/path.md").toURI())
            val path = "/posts/{id}"

            //when
            val result = BlueprintParser(file).parse()

            //then
            assertThat(result.resources[0].path).isEqualTo(path)
        }

        @Test
        fun `defined by http method followed by the path`() {
            //given
            val file = Paths.get(this::class.java.classLoader.getResource("specification_examples/resource_section/http_method_path.md").toURI())
            val path = "/posts/{id}"

            //when
            val result = BlueprintParser(file).parse()

            //then
            assertThat(result.resources[0].path).isEqualTo(path)
        }

        @Test
        fun `defined by identifier followed by the path`() {
            //given
            val file = Paths.get(this::class.java.classLoader.getResource("specification_examples/resource_section/identifier_path.md").toURI())
            val path = "/posts/{id}"

            //when
            val result = BlueprintParser(file).parse()

            //then
            assertThat(result.resources[0].path).isEqualTo(path)
        }
    }
}