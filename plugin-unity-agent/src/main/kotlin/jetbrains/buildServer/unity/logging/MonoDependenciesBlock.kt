package jetbrains.buildServer.unity.logging

class MonoDependenciesBlock : LogBlock {

    override val name = "Mono dependencies included in the build"

    override val logFirstLine = LogType.Inside

    override val logLastLine = LogType.Inside

    override fun isBlockStart(text: String) = blockStart.containsMatchIn(text)

    override fun isBlockEnd(text: String) = !blockEnd.containsMatchIn(text)

    override fun getText(text: String) = text

    companion object {
        private val blockStart = Regex("Mono dependencies included in the build")
        private val blockEnd = Regex("Dependency assembly - .*")
    }
}