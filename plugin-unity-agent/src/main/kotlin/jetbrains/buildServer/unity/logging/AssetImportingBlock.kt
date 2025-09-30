package jetbrains.buildServer.unity.logging

class AssetImportingBlock : LogBlock {
    override val name = "Asset Importing"
    override val logFirstLine = LogType.Inside
    override val logLastLine = LogType.Outside

    override fun isBlockStart(text: String) = startPatterns.any { it.containsMatchIn(text) }

    override fun isBlockEnd(text: String) = endPatterns.any { it.containsMatchIn(text) }

    override fun getText(text: String) = text

    companion object {
        private val startPatterns = listOf(
            Regex("^\\s*Start importing .*")
        )

        // Only patterns that actually indicate the block ended
        private val endPatterns = listOf(
            Regex("Refreshing native plugins .*"),
            Regex("Preloading .* native plugins.*"),
            Regex("Asset Pipeline Refresh.*")
        )
    }
}
