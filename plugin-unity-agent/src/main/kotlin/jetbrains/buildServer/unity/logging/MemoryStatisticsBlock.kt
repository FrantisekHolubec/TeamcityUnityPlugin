package jetbrains.buildServer.unity.logging

class MemoryStatisticsBlock : LogBlock {
    override val name = "Memory Statistics:"
    override val logFirstLine = LogType.Inside
    override val logLastLine = LogType.Outside

    override fun isBlockStart(text: String) = startBlockItem.containsMatchIn(text)

    override fun isBlockEnd(text: String) = endBlockItem.containsMatchIn(text)

    override fun getText(text: String) = text

    companion object {
        private val startBlockItem = Regex("Memory Statistics:")
        private val endBlockItem = Regex("##utp:\\{\\\"type\\\":\\\"MemoryLeaks\\\".*")
    }
}
