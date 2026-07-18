# 蓝花楹木告示牌本地化指南

## 概述
为了安全地添加蓝花楹木告示牌的本地化内容，我创建了单独的本地化文件而不是直接修改现有的语言文件。

## 文件说明

### 1. 本地化补丁文件
- **jacaranda_sign_localization.patch** - 包含了需要添加到现有语言文件的代码

### 2. 单独的本地化文件
- **jacaranda_sign_en.lang** - 英文本地化内容
- **jacaranda_sign_zh.lang** - 中文本地化内容

## 如何应用本地化

### 方法一：手动合并到现有文件（推荐）
1. 打开 `src/main/resources/assets/dollancustomized/lang/en_us.lang`
2. 在文件末尾添加以下内容：
```lang
# Jacaranda Sign
tile.jacaranda_sign.name=Jacaranda Sign
gui.done=Done
gui.sign.click_to_edit=Click to edit text
gui.sign.shift_click_for_colors=Shift+Click to open editor
```

3. 打开 `src/main/resources/assets/dollancustomized/lang/zh_cn.lang`
4. 在文件末尾添加以下内容：
```lang
# Jacaranda Sign
tile.jacaranda_sign.name=蓝花楹木告示牌
gui.done=完成
gui.sign.click_to_edit=点击编辑文本
gui.sign.shift_click_for_colors=按住Shift点击打开编辑界面
```

### 方法二：使用补丁文件
如果您的项目支持补丁应用，可以使用 `jacaranda_sign_localization.patch` 文件。

## 本地化内容说明

### 方块名称
- `tile.jacaranda_sign.name` - 蓝花楹木告示牌的显示名称

### GUI文本
- `gui.done` - "完成"按钮文本
- `gui.sign.click_to_edit` - "点击编辑文本"提示
- `gui.sign.shift_click_for_colors` - "按住Shift点击打开编辑界面"提示

## 注意事项
1. 确保在添加本地化内容时保持文件格式正确
2. 每个条目应该占单独一行
3. 注释行以 `#` 开头
4. 不要删除或覆盖现有内容，只在新行添加

## 文件状态
- ✅ 现有语言文件保持不变
- ✅ 新的本地化内容以安全的方式提供
- ✅ 提供了完整的中文和英文翻译