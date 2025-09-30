package jetbrains.buildServer.unity.logging

class AssetDatabaseInitialRefreshBlock : LogBlock {

    override val name = "AssetDatabase Initial Script Refresh"

    override val logFirstLine = LogType.Inside

    override val logLastLine = LogType.Inside

    override fun isBlockStart(text: String) = blockStart.containsMatchIn(text)

    override fun isBlockEnd(text: String) = blockEnd.containsMatchIn(text)

    override fun getText(text: String) = text

    companion object {
        private val blockStart = Regex("Application\\.AssetDatabase Initial (Script )*Refresh Start.*")
        private val blockEnd = Regex("Application\\.AssetDatabase Initial (Script )*Refresh End.*")
    }
}
