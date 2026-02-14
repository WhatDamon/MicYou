package com.lanrhyme.micyou

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import micyou.composeapp.generated.resources.Res
import micyou.composeapp.generated.resources.app_icon
import org.jetbrains.compose.resources.painterResource
import java.io.File

/**
 * 托盘菜单配置
 * 提供便捷的方法来创建和管理托盘菜单
 */
object TrayMenu {

    /**
     * 菜单项类型
     */
    enum class ItemType {
        ITEM,
        SEPARATOR
    }

    /**
     * 菜单项配置
     */
    data class ItemConfig(
        val id: String = "",
        val type: ItemType = ItemType.ITEM,
        val text: String = "",
        val enabled: Boolean = true,
        val callback: () -> Unit = {}
    )

    /**
     * 默认菜单项
     */
    object DefaultItems {
        const val SHOW_HIDE = "show_hide"
        const val CONNECT_DISCONNECT = "connect_disconnect"
        const val SETTINGS = "settings"
        const val EXIT = "exit"
    }

    /**
     * 创建默认菜单配置
     */
    fun createDefaultConfig(
        isVisible: Boolean,
        isStreaming: Boolean,
        onShowHideClick: () -> Unit,
        onConnectDisconnectClick: () -> Unit,
        onSettingsClick: () -> Unit,
        onExitClick: () -> Unit
    ): List<ItemConfig> {
        return listOf(
            ItemConfig(
                id = DefaultItems.SHOW_HIDE,
                text = if (isVisible) "Hide Window" else "Show Window",
                enabled = true,
                callback = onShowHideClick
            ),
            ItemConfig(
                id = DefaultItems.CONNECT_DISCONNECT,
                text = if (isStreaming || isStreaming) "Disconnect" else "Connect",
                enabled = true,
                callback = onConnectDisconnectClick
            ),
            ItemConfig(
                id = DefaultItems.SETTINGS,
                text = "Settings",
                enabled = true,
                callback = onSettingsClick
            ),
            ItemConfig(
                type = ItemType.SEPARATOR
            ),
            ItemConfig(
                id = DefaultItems.EXIT,
                text = "Exit",
                enabled = true,
                callback = onExitClick
            )
        )
    }

    /**
     * 获取默认图标路径
     * 按优先级尝试多个可能的图标路径
     */
    fun getDefaultIconPath(): String {
        val candidatePaths = listOf(
            // 1. 安装路径（生产环境）
            "/opt/micyou/lib/MicYou.png",
            // 2. 开发环境路径
            System.getProperty("user.dir") + "/composeApp/src/commonMain/composeResources/drawable/app_icon.png",
            "composeApp/src/commonMain/composeResources/drawable/app_icon.png",
            "src/commonMain/composeResources/drawable/app_icon.png"
        )

        for (path in candidatePaths) {
            val file = File(path)
            if (file.exists()) {
                return file.absolutePath
            }
        }

        // 返回安装路径作为默认值（便于调试时看到预期路径）
        return candidatePaths.first()
    }

    /**
     * 获取图标资源（用于 Compose）
     */
    @Composable
    @Suppress("UNUSED_PARAMETER")
    fun getIconPainter(): androidx.compose.ui.graphics.painter.Painter {
        return painterResource(Res.drawable.app_icon)
    }
}
