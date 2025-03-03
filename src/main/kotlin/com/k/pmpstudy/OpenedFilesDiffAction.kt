package com.k.pmpstudy

import com.intellij.diff.DiffContentFactory
import com.intellij.diff.DiffDialogHints
import com.intellij.diff.DiffManager
import com.intellij.diff.contents.DiffContent
import com.intellij.diff.requests.SimpleDiffRequest
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.fileEditor.impl.EditorWindow
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.annotations.NotNull

class OpenedFilesDiffAction : AnAction() {
    private val ACTION_ERROR_MESSAGE_TITLE: String = "Showing Diff Error"

    override fun update(e: AnActionEvent) {
        val project = e.project
        e.presentation.isEnabledAndVisible = (project != null)
    }

    override fun actionPerformed(@NotNull e: AnActionEvent) {
        val fileEditorManager: FileEditorManagerEx = FileEditorManagerEx.getInstanceEx(e.project!!)
        val windows: Array<EditorWindow> = fileEditorManager.splitters.getWindows()

        if (isOpenedAtMost1File(windows)) {
            Messages.showMessageDialog(
                "Please open at least 2 files.", ACTION_ERROR_MESSAGE_TITLE, Messages.getInformationIcon()
            )
            return
        }
        if (windows.size == 1) {
            showDiffFirst2Files(e.project!!, windows[0].fileList)
            return
        }

        var selectedFileList: List<VirtualFile> = windows.map { it.selectedFile!! }.distinct().toList()
        if (selectedFileList.size > 2) {
            selectedFileList = FileSelectDialog(selectedFileList).showChoose2FilesDialog()
        }
        if (selectedFileList.isEmpty()) return
        showDiffFirst2Files(e.project!!, selectedFileList)
    }

    private fun isOpenedAtMost1File(windows: Array<EditorWindow>): Boolean =
        windows.isEmpty()
                || (windows.size == 1 && windows[0].fileList.size <= 1)

    private fun showDiffFirst2Files(project: Project, files: List<VirtualFile>) {
        if (files.size <= 1) {
            Messages.showMessageDialog(
                "Please display 2 different files.", ACTION_ERROR_MESSAGE_TITLE, Messages.getInformationIcon()
            )
            return
        }
        if (files[0] == files[1]) {
            Messages.showMessageDialog(
                "Same files are opened", ACTION_ERROR_MESSAGE_TITLE, Messages.getInformationIcon()
            )
            return
        }

        val contentList: List<DiffContent> = files.map { DiffContentFactory.getInstance().create(project, it) }.take(2)

        val request = SimpleDiffRequest(
            "Opened Files Diff",
            contentList[0],
            contentList[1],
            files[0].canonicalPath,
            files[1].canonicalPath,
        )
        DiffManager.getInstance().showDiff(project, request, DiffDialogHints.MODAL)
    }

    override fun getActionUpdateThread() = ActionUpdateThread.BGT
}