

package jetbrains.buildServer.unity.logging

import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class LineStatusProvider {

    private val patterns = mutableListOf<Pair<Regex, LineStatus>>()

    constructor() {
        patterns.addAll(
            listOf(
                Regex(".*?warning CS\\d+.*?") to LineStatus.Warning,
                Regex("WARNING(?!_LOGS).*") to LineStatus.Warning,
                Regex(".*?error CS\\d+.*?") to LineStatus.NonFatalFailure,
                Regex("Compilation failed:.*") to LineStatus.Error,
                Regex("Scripts have compiler errors\\..*") to LineStatus.Error,
                Regex("Error building player because script class layout is incompatible between the editor and the player.") to LineStatus.Error,
                Regex("Fatal Error!.*") to LineStatus.Error,
                Regex("executeMethod method .+ threw exception") to LineStatus.Error,
                Regex("executeMethod class .+ could not be found") to LineStatus.Error,
                Regex("Couldn't set project path to:.+") to LineStatus.Error,
                Regex("Failed to activate/update license") to LineStatus.Error,
                Regex("Error building player .+") to LineStatus.Error,
                Regex("FAILURE: Build failed with an exception.*") to LineStatus.Error,
                Regex("\\*\\* ARCHIVE FAILED \\*\\*.*") to LineStatus.Error,
                Regex("Undefined symbols for architecture.*") to LineStatus.Error,
                Regex("Addressable content build failure.*") to LineStatus.Error,
                Regex("Failed to build Addressables content.*") to LineStatus.Error,
                Regex(".*?Unexpected exception in.*?") to LineStatus.NonFatalFailure,
                Regex("Assertion failed on expression:.*?") to LineStatus.Warning,
                Regex(".*?is self-intersecting and has been discarded.*?") to LineStatus.Warning,
                Regex(".*?mesh .+ has invalid normals.*?") to LineStatus.Warning,
                Regex("Identifier uniqueness violation:.*") to LineStatus.Warning,
                Regex("Missing types referenced from.*?") to LineStatus.NonFatalFailure,
                Regex("OVRPlugin not updated. Restart the editor to update.*") to LineStatus.NonFatalFailure,
                Regex("A meta data file \\(\\.meta\\) exists but its folder .* can't be found.*") to LineStatus.Warning,

        ))
    }

    constructor(lineStatusesFile: File) {
        val documentBuilderFactory = DocumentBuilderFactory.newInstance()
        val documentBuilder = documentBuilderFactory.newDocumentBuilder()
        val document = documentBuilder.parse(lineStatusesFile)
        document.documentElement.normalize()
        document.getElementsByTagName("line").nodes<Element>().forEach {
            val level = it.getAttribute("level")?.let { value ->
                LineStatus.parse(value)
            } ?: return@forEach
            val message = it.getAttribute("message") ?: return@forEach

            patterns += Regex(message) to level
        }
    }

    /**
     * Provides line status details.
     * @text is a text line.
     * @return status.
     */
    fun getLineStatus(text: String): LineStatus {
        if(text.startsWith("##teamcity"))
            return LineStatus.Normal
        return patterns.firstOrNull { it.first.containsMatchIn(text) }?.second ?: LineStatus.Normal
    }

    companion object {
        inline fun <reified T> NodeList.nodes(): Sequence<T> {
            val list = this
            return sequence {
                for (i in 0..list.length) {
                    yield(list.item(i))
                }
            }.filterIsInstance<T>()
        }
    }
}
