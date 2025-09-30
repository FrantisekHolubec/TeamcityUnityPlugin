package jetbrains.buildServer.unity.logging

class AssetDatabaseRefreshBlock : LogBlock {

    override val name = "Asset Database Refresh"

    override val logFirstLine = LogType.Inside

    override val logLastLine = LogType.Inside

    override fun isBlockStart(text: String) = blockStart.containsMatchIn(text)

    override fun isBlockEnd(text: String) = blockEnd.containsMatchIn(text)

    override fun getText(text: String) = text

    companion object {
        private val blockStart = Regex("Application\\.AssetDatabase\\.Refresh Start.*")
        private val blockEnd = Regex("Application\\.AssetDatabase\\.Refresh End.*")
    }
}