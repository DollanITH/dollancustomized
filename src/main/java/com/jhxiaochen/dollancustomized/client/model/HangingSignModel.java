/*
    Simple backport of new minecraft features to mc 1.12.2
    Copyright (C) 2022 WolodiaM

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package com.jhxiaochen.dollancustomized.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HangingSignModel extends ModelBase {
    public final ModelRenderer root;
    public final ModelRenderer plank;
    public final ModelRenderer vChains;
    public final ModelRenderer normalChains;
    public final ModelRenderer chainL1;
    public final ModelRenderer chainL2;
    public final ModelRenderer chainR1;
    public final ModelRenderer chainR2;
    public final ModelRenderer board;

    // 标记哪些部分应该显示
    private boolean showPlank = false;
    private boolean showVChains = false;
    private boolean showNormalChains = true;

    public HangingSignModel() {
        super();

        // 创建根模型 - 1.12.2需要纹理尺寸参数
        this.root = new ModelRenderer(this, 0, 0);

        // 创建板子 - 使用纹理坐标(0, 12)
        this.board = new ModelRenderer(this, 0, 12);
        this.board.addBox(-7, 0, -1, 14, 10, 2);
        this.root.addChild(this.board);

        // 创建背板 - 使用纹理坐标(0, 0)
        this.plank = new ModelRenderer(this, 0, 0);
        this.plank.addBox(-8, -6, -2, 16, 2, 4);
        this.root.addChild(this.plank);

        // 创建普通锁链容器 - 使用纹理坐标(0, 6)
        this.normalChains = new ModelRenderer(this, 0, 6);
        this.root.addChild(this.normalChains);

        // 创建左链子1 - 使用纹理坐标(0, 6)
        this.chainL1 = new ModelRenderer(this, 0, 6);
        this.chainL1.addBox(-1.5F, 0, 0, 3, 6, 0);
        this.chainL1.setRotationPoint(-5, -6, 0);
        this.chainL1.rotateAngleY = (float) (-Math.PI / 4);
        this.normalChains.addChild(this.chainL1);

        // 创建左链子2 - 使用纹理坐标(6, 6)
        this.chainL2 = new ModelRenderer(this, 6, 6);
        this.chainL2.addBox(-1.5F, 0, 0, 3, 6, 0);
        this.chainL2.setRotationPoint(-5, -6, 0);
        this.chainL2.rotateAngleY = (float) (Math.PI / 4);
        this.normalChains.addChild(this.chainL2);

        // 创建右链子1 - 使用纹理坐标(0, 6)
        this.chainR1 = new ModelRenderer(this, 0, 6);
        this.chainR1.addBox(-1.5F, 0, 0, 3, 6, 0);
        this.chainR1.setRotationPoint(5, -6, 0);
        this.chainR1.rotateAngleY = (float) (-Math.PI / 4);
        this.normalChains.addChild(this.chainR1);

        // 创建右链子2 - 使用纹理坐标(6, 6)
        this.chainR2 = new ModelRenderer(this, 6, 6);
        this.chainR2.addBox(-1.5F, 0, 0, 3, 6, 0);
        this.chainR2.setRotationPoint(5, -6, 0);
        this.chainR2.rotateAngleY = (float) (Math.PI / 4);
        this.normalChains.addChild(this.chainR2);

        // 创建V型锁链 - 使用纹理坐标(14, 6)
        this.vChains = new ModelRenderer(this, 14, 6);
        this.vChains.addBox(-6, -6, 0, 12, 6, 0);
        this.root.addChild(this.vChains);

        // 默认状态
        this.showPlank = true;
        this.showVChains = false;
        this.showNormalChains = true;
    }

    public void evaluateVisibleParts(boolean isSideModel, boolean attached) {
        // 根据方块类型显示不同的部分
        this.showPlank = isSideModel;
        this.showVChains = false;
        this.showNormalChains = true;

        if (!isSideModel) {
            // 顶部悬挂式，根据 attached 属性切换链子显示
            this.showNormalChains = !attached;
            this.showVChains = attached;
        }
    }

    public void render() {
        // 先渲染板子（总是显示）
        this.board.render(0.0625F);

        // 根据标记显示其他部分
        if (this.showPlank) {
            this.plank.render(0.0625F);
        }

        if (this.showNormalChains) {
            this.normalChains.render(0.0625F);
        }

        if (this.showVChains) {
            // 禁用裁剪以避免V型锁链渲染问题
            this.vChains.render(0.0625F);
        }
    }
}
