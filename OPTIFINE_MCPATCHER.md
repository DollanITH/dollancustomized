# OptiFine 资源包配置说明

## 目录结构

```
src/main/resources/
├── assets/minecraft/mcpatcher/
│   ├── mcpatcher.cfg              # MCPatcher 配置文件
│   ├── custom_textures.json       # 自定义纹理配置
│   ├── biome.properties           # 生物群系配置
│   ├── terrain.png                # 地形纹理
│   ├── cit/                       # Custom Item Textures
│   │   └── spawn_egg/             # 生蛋纹理
│   │       ├── *.png
│   │       └── *.properties
│   ├── ctm/                       # Connected Textures Mod
│   │   └── default/               # 默认连接纹理
│   │       └── *_glass_*.png
│   └── mob/                       # 自定义 Mob 纹理
│       ├── *.properties
│       ├── *.png
│       └── [entity_name]/         # 各个生物的子目录
├── packs/minecraft/
│   ├── pack.mcmeta                 # 资源包元数据
│   └── pack.png                    # 资源包图标
└── ...
```

## 关键配置文件

### 1. mcpatcher.cfg
OptiFine MCPatcher 配置文件，控制各个功能的开关。

```properties
enableConnectedTextures=false
enableCustomColors=false
enableDynamicLights=false
enableNewParticle=true
enablePerspective=false
enableRelightTextures=false
```

### 2. custom_textures.json
自定义纹理的映射配置，OptiFine 读取此文件来识别自定义纹理。

```json
{
  "custom_textures": {
    "minecraft": {
      "assets": {
        "minecraft": {
          "mcpatcher": {
            "cit": {
              "spawn_egg": {
                "types": { ... }
              }
            },
            "mob": {
              "types": { ... }
            }
          }
        }
      }
    }
  }
}
```

### 3. biome.properties
生物群系配置文件。

### 4. terrain.png
地形纹理文件，OptiFine 通过检测此文件来判断资源包是否包含 mcpatcher 资源。

## 资源包加载机制

### ModPack 类
- 继承自 `FileResourcePack`
- 读取模组 jar 本身作为资源包
- 通过 Mixin 自动挂载到 Minecraft 资源包仓库

### Mixin 自动挂载
`ResourcePackRepositoryMixin.updateRepositoryEntriesAllReturn()` 方法：
1. 检查资源包是否已存在
2. 如果不存在，创建新的资源包条目
3. 添加到 `repositoryEntriesAll`（可用列表）
4. 添加到 `repositoryEntries`（已选列表，仅初始加载时）

## OptiFine 识别流程

1. **资源包加载**：
   - Minecraft 加载资源包时，会检测 `assets/minecraft/mcpatcher/` 目录
   - 如果存在 `terrain.png` 和必要的配置文件，OptiFine 会识别为 mcpatcher 资源

2. **纹理应用**：
   - CIT（Custom Item Textures）：根据 `custom_textures.json` 配置应用自定义物品纹理
   - CTM（Connected Textures）：应用连接纹理效果
   - Mob：应用自定义 Mob 纹理

3. **优先级**：
   - 第一个资源包优先级最高
   - 模组资源包需要在 OptiFine 之前加载，确保 mcpatcher 资源正确应用

## 验证方法

1. 启动游戏后，进入资源包选择界面
2. 检查日志中是否有 "Bundled resource pack auto-applied" 信息
3. 验证 OptiFine 配置界面是否显示所有已启用的功能
4. 检查自定义纹理是否正确应用

## 注意事项

- **pack_format**: 设置为 3（对应 1.5.x 版本）
- **pack.mcmeta**: 必须在 `packs/minecraft/` 目录下
- **terrain.png**: 必须存在，否则 OptiFine 无法识别 mcpatcher 资源
- **custom_textures.json**: 如果不使用 CIT 功能，可以删除此文件
- **资源包顺序**: 模组资源包必须在 OptiFine 之前加载

## 重新构建

```bash
# 使用 Gradle 构建
gradlew build

# 构建后的 jar 文件包含：
# - assets/minecraft/mcpatcher/ 所有资源
# - pack.mcmeta
# - pack.png
```
