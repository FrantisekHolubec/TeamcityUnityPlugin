package jetbrains.buildServer.unity.logging

import kotlin.text.Regex

class UnityStackTraceBlockAlt : LogBlock {
    override val name = "StackTrace..."
    override val logFirstLine = LogType.Inside
    override val logLastLine = LogType.Inside

    override fun isBlockStart(text: String) = blockStart.containsMatchIn(text)
    override fun isBlockEnd(text: String) = !blockEnd.containsMatchIn(text)
    override fun getText(text: String) = text

    companion object {
        private val blockStart = Regex(".*#0 GetStacktrace\\(int\\).*")
        private val blockEnd = Regex(".*#\\d+.*")
    }
}
