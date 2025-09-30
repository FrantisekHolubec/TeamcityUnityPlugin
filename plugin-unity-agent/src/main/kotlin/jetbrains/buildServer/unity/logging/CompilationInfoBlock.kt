package jetbrains.buildServer.unity.logging

class CompilationInfoBlock : LogBlock {
    override val name = "Compilation Info"
    override val logFirstLine = LogType.Inside
    override val logLastLine = LogType.Outside

    override fun isBlockStart(text: String) = blockItem.containsMatchIn(text)

    override fun isBlockEnd(text: String): Boolean {
        if (blockItem.containsMatchIn(text)) return false
        return blockEndItems.any { text.contains(it) }.not()
    }

    override fun getText(text: String) = text

    companion object {
        private val blockItem = Regex("^info: .*")

        private val blockEndItems = listOf(
            "      Request starting",
            "      Request finished ",
            "      Executing endpoint ",
            "      Executed endpoint ",
            "      ALC ILPP context",
            "      Resolved ",
            "      processors: ",
            "      Processing assembly ",
            "      running ",
            "- EILPP : ",
            "      ALC ILPP "
        )
    }
}
