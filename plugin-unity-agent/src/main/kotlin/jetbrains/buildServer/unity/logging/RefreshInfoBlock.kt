package jetbrains.buildServer.unity.logging

class RefreshInfoBlock : LogBlock {

    override val name = "RefreshInfo - more:"

    override val logFirstLine = LogType.Inside

    override val logLastLine = LogType.Inside

    override fun isBlockStart(text: String) = blockStart.containsMatchIn(text)

    override fun isBlockEnd(text: String) = blockEnd.containsMatchIn(text)

    override fun getText(text: String) = text

    companion object {
        private val blockStart = Regex("InvokeBeforeRefreshCallbacks: .*")
        private val blockEnd = Regex("Untracked:.*")
    }
}