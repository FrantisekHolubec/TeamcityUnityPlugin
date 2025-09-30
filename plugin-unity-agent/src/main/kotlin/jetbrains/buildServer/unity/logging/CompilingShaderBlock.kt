package jetbrains.buildServer.unity.logging

class CompilingShaderBlock : LogBlock {

    override var name = "Shader log"

    override val logFirstLine = LogType.Outside

    override val logLastLine = LogType.Inside

    override fun isBlockStart(text: String) = blockStart.containsMatchIn(text)

    override fun isBlockEnd(text: String) = blockEnd.containsMatchIn(text) || blockStart.containsMatchIn(text)

    override fun getText(text: String) = text

    companion object {
        private val blockStart = Regex("Compiling shader .*")
        private val blockEnd = Regex("^\\s*Prepared data for serialisation.*")
    }
}
