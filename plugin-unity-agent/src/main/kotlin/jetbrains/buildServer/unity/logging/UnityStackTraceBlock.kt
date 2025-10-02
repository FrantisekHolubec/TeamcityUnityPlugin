package jetbrains.buildServer.unity.logging

class UnityStackTraceBlock : LogBlock {

    override val name = "StackTrace..."
    override val logFirstLine = LogType.Inside
    override val logLastLine = LogType.Inside

    override fun isBlockStart(text: String) = blockStartPatterns.any { it.containsMatchIn(text) }

    override fun isBlockEnd(text: String) = blockEndPatterns.any { it.containsMatchIn(text) }

    override fun getText(text: String) =
        if (filterOut.any { it.containsMatchIn(text) }) ""
        else text

    companion object {
        private val blockStartPatterns = listOf(
            Regex("UnityEngine.*ExtractStackTrace"),
            Regex("UnityEngine\\.StackTraceUtility:ExtractStackTrace.*"),
            Regex("UnityEditor\\.BuildPipeline:BuildPlayerInternalNoCheck.*$")
        )

        private val blockEndPatterns = listOf(
            Regex("(^\\s*\\(Filename:.*)|(\\s*\\[.* line \\d+\\])"),
            Regex("UnityEditor\\.Scripting\\.ScriptCompilation\\.EditorCompilationInterface:(TickCompilationPipeline|CompileScripts).*"),
            Regex("UnityEditorInternal\\.APIUpdating\\.APIUpdaterManager:ProcessImportedAssemblies.*"),
            Regex("UnityEditor\\.Modules\\.ModuleManager:InitializePlatformSupportModules.*"),
            Regex("UnityEditor\\.EditorApplication:Internal_CallDelayFunctions.*"),
            Regex("UnityEditor\\.EditorAssemblies:ProcessInitializeOnLoadMethodAttributes.*"),
            Regex("UnityEditor\\.AssetPostprocessingInternal:PostprocessAllAssets.*")
        )

        private val filterOut = listOf(
            Regex("^UnityEngine\\.Debug:.*$"),
            Regex("^UnityEngine\\.Logger:.*$"),
            Regex("^UnityEngine\\.StackTraceUtility:.*$")
        )
    }
}
