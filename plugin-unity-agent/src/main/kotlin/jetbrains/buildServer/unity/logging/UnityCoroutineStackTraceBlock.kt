package jetbrains.buildServer.unity.logging

class UnityCoroutineStackTraceBlock : LogBlock {

    override val name = "..."
    override val logFirstLine = LogType.Inside
    override val logLastLine = LogType.Inside

    override fun isBlockStart(text: String) =
        blockStartPatterns.any { it.containsMatchIn(text) }

    override fun isBlockEnd(text: String) =
        blockEndPatterns.any { (regex, invert) ->
            val matches = regex.containsMatchIn(text)
            if (invert) !matches else matches
        }

    override fun getText(text: String) = text

    companion object {
        // Start patterns
        private val blockStartPatterns = listOf(
            Regex(".*\\(Unity\\) StackWalker::GetCurrentCallstack.*"),
            Regex(".*#0 GetStacktrace\\(int\\).*")
        )

        // End patterns with optional inversion
        // If invert == true, the block continues while regex matches (like #\d+ lines)
        private val blockEndPatterns = listOf(
            Regex(".*\\(ntdll\\) RtlUserThreadStart.*") to false,
            Regex(".*#\\d+.*") to true
        )
    }
}
