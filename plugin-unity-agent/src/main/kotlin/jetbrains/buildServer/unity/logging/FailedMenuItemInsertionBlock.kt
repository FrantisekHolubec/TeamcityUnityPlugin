package jetbrains.buildServer.unity.logging

class FailedMenuItemInsertionBlock : LogBlock {
    override val name = "Failed menu item insertion"
    override val logFirstLine = LogType.Inside
    override val logLastLine = LogType.Outside

    override fun isBlockStart(text: String) =
        startPatterns.any { it.containsMatchIn(text) }

    override fun isBlockEnd(text: String) =
        endPatterns.none { it.containsMatchIn(text) }

    override fun getText(text: String) = text

    companion object {
        private val startPatterns = listOf(
            Regex("^Failed to insert item.*")
        )

        private val endPatterns = listOf(
            Regex("^Failed to.*"),
            Regex("^Name:.*")
        )
    }
}
