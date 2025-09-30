package jetbrains.buildServer.unity.logging

class ManifestCurrentFilesBlock : LogBlock {

    override val name = "Current files:"

    override val logFirstLine = LogType.None

    override val logLastLine = LogType.Outside

    override fun isBlockStart(text: String) = blockStart.containsMatchIn(text)

    override fun isBlockEnd(text: String) = !blockEnd.containsMatchIn(text)

    override fun getText(text: String) = text

    companion object {
        private val blockStart = Regex("Current files:.*")
        private val blockEnd = Regex("^Assets.*$")
    }
}