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
        // Matches lines like: [2562/3476  0s] ... or [BUSY 6s] ...
        private val startPatterns = listOf(
            Regex("\\[\\s*((\\d+/\\d+)|\\w+)?\\s*\\d+s\\]")
        )

        // Lines that should **keep the block open**
        private val endPatterns = listOf(
            Regex("\\[\\s*((\\d+/\\d+)|\\w+)?\\s*\\d+s\\]"),
            Regex("processors: .*"),
            Regex("running.*"),
            Regex("^\\s*$")
        )
    }
}
