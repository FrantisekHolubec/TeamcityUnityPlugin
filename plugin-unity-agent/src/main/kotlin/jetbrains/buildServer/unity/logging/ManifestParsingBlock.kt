package jetbrains.buildServer.unity.logging

class ManifestParsingBlock : LogBlock {

    override val name = "Manifest aliases:"

    override val logFirstLine = LogType.None

    override val logLastLine = LogType.Inside

    override fun isBlockStart(text: String) = blockStart.containsMatchIn(text)

    override fun isBlockEnd(text: String) = blockEnd.containsMatchIn(text)

    override fun getText(text: String) = text

    companion object {
        private val blockStart = Regex("Manifest aliases:.*")
        private val blockEnd = Regex("Current files:*$")
    }
}