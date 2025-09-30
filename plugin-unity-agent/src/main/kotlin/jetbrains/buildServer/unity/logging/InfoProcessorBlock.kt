package jetbrains.buildServer.unity.logging

class InfoProcessorBlock : LogBlock {

    override val name = "info (processors)"
    override val logFirstLine = LogType.Inside
    override val logLastLine = LogType.Inside

    override fun isBlockStart(text: String) = blockStartPatterns.any { it.containsMatchIn(text) }

    override fun isBlockEnd(text: String) = blockEndPatterns.none { it.containsMatchIn(text) }

    override fun getText(text: String) = text

    companion object {
        private val blockStartPatterns = listOf(
            Regex("\\[40m.*(info |warn ).*: Unity.ILPP.Runner.*"),
            Regex("\\[40m.*(info |warn ).*: Microsoft.*")
        )

        private val blockEndPatterns = listOf(
            Regex("^\\s*Executed endpoint.*"),
            Regex("^\\s*Request (finished|starting).*"),
            Regex("^\\s*(running|processors:|Processing assembly) .*")
        )
    }
}
