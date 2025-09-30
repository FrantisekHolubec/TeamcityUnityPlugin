

package jetbrains.buildServer.unity.logging

class ScriptCompilationBlock : LogBlock {

    override val name = "Script compilation"

    override val logFirstLine = LogType.Inside

    override val logLastLine = LogType.Inside

    override fun isBlockStart(text: String) = blockStart.containsMatchIn(text)

    override fun isBlockEnd(text: String) = blockEnd.containsMatchIn(text)

    override fun getText(text: String) = text.removePrefix(prefix)

    companion object {
        private val blockStart = Regex("- Starting script compilation.*")
        private val blockEnd = Regex("- Finished script compilation.*")
        private const val prefix = "[ScriptCompilation] "
    }
}
