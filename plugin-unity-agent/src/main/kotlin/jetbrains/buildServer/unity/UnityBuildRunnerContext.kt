package jetbrains.buildServer.unity

import jetbrains.buildServer.agent.BuildRunnerContext
import java.io.File
import java.io.InputStream

class UnityBuildRunnerContext(
    private val context: BuildRunnerContext,
) : BuildRunnerContext by context {
    val unityProjectPath: String by lazy {
        val configuredPath = runnerParameters[UnityConstants.PARAM_PROJECT_PATH]?.trim().orEmpty()
        val projectFile = if (configuredPath.isBlank()) {
            workingDirectory
        } else {
            val configuredFile = File(configuredPath)
            if (configuredFile.isAbsolute) configuredFile else File(workingDirectory, configuredPath)
        }

        projectFile.absolutePath
    }

    val unityProject: UnityProject by lazy {
        UnityProject(FileSystemUnityProjectFileAccessor(unityProjectPath))
    }

    private class FileSystemUnityProjectFileAccessor(
        projectPath: String,
    ) : UnityProjectFilesAccessor {
        private var current = File(projectPath)

        override fun directory(name: String): UnityProjectFilesAccessor? {
            current = current.listFiles()?.firstOrNull { it.isDirectory && it.name == name } ?: return null
            return this
        }

        override fun file(name: String): InputStream? {
            val file = current.listFiles()?.firstOrNull { it.isFile && it.name == name } ?: return null
            return file.inputStream()
        }
    }
}
