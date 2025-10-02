package jetbrains.buildServer.unity.logging

class IL2CppProgressBlock : LogBlock {
    override val name = "IL2Cpp progress logs ..."
    override val logFirstLine = LogType.Inside
    override val logLastLine = LogType.Outside

    override fun isBlockStart(text: String) =
        startPatterns.any { it.containsMatchIn(text) }

    override fun isBlockEnd(text: String) =
        endPatterns.none { it.containsMatchIn(text) }

    override fun getText(text: String) = text

    companion object {
        private val startPatterns = listOf(
            Regex("\\[\\s*((\\d+/\\d+)|\\w+)?\\s*\\d+s\\]")
        )

        private val endPatterns = listOf(
            Regex("\\[\\s*((\\d+/\\d+)|\\w+)?\\s*\\d+s\\]"),
            Regex("Processing assembly.*"),
            Regex("processors:.*"),
            Regex("running.*"),
            Regex("^\\s*$")
        )
    }
}
