package com.k.pmpstudy

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.CheckBoxList
import org.jetbrains.annotations.Nullable
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class FileSelectDialog(private val files: List<VirtualFile>) : DialogWrapper(true) {
    private val checkBoxList = CheckBoxList<VirtualFile>()

    init {
        title = "Opened Files Diff"
        init()
        checkBoxList.setItems(files.toList(), VirtualFile::getPath)
        checkBoxList.setItemSelected(files[0], true)
    }

    @Nullable
    override fun createCenterPanel(): JComponent {
        val dialogPanel = JPanel(BorderLayout())

        val label = JLabel("Select 2 files to compare.")
        label.preferredSize = Dimension(100, 50)
        dialogPanel.add(label, BorderLayout.NORTH)
        dialogPanel.add(checkBoxList, BorderLayout.CENTER)

        return dialogPanel
    }

    fun showChoose2FilesDialog(): List<VirtualFile> =
        if (showAndGet()) files.filter { checkBoxList.isItemSelected(it) }.toList()
        else emptyList()

    override fun doValidate(): ValidationInfo? =
        if (files.count { checkBoxList.isItemSelected(it) } == 2) null
        else ValidationInfo("Select just 2 files.", checkBoxList).asWarning()
}