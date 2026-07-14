package com.jhxiaochen.dollancustomized.client.model;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.List;

public class OffsetBakedModel implements IBakedModel {
    private final IBakedModel originalModel;
    private final float yOffset;

    public OffsetBakedModel(IBakedModel originalModel, float yOffset) {
        this.originalModel = originalModel;
        this.yOffset = yOffset;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        List<BakedQuad> originalQuads = originalModel.getQuads(state, side, rand);

        if (state == null) {
            return originalQuads;
        }

        List<BakedQuad> offsetQuads = new ArrayList<>(originalQuads.size());
        for (BakedQuad quad : originalQuads) {
            offsetQuads.add(offsetQuadY(quad, yOffset));
        }
        return offsetQuads;
    }

    private BakedQuad offsetQuadY(BakedQuad quad, float offset) {
        VertexFormat format = quad.getFormat();
        int posIndex = -1;

        for (int i = 0; i < format.getElementCount(); i++) {
            if (format.getElement(i).getUsage() == VertexFormatElement.EnumUsage.POSITION) {
                posIndex = i;
                break;
            }
        }
        if (posIndex == -1) return quad;

        int[] vertexData = quad.getVertexData().clone();
        int stride = format.getIntegerSize();
        int byteOffset = format.getOffset(posIndex);
        int intOffset = byteOffset / 4;

        for (int i = 0; i < 4; i++) {
            int base = i * stride + intOffset;
            float y = Float.intBitsToFloat(vertexData[base + 1]);
            vertexData[base + 1] = Float.floatToIntBits(y + offset);
        }

        return new BakedQuad(
                vertexData,
                quad.getTintIndex(),
                quad.getFace(),
                quad.getSprite(),
                quad.shouldApplyDiffuseLighting(),
                quad.getFormat()
        );
    }

    @Override public boolean isAmbientOcclusion() { return originalModel.isAmbientOcclusion(); }
    @Override public boolean isGui3d() { return originalModel.isGui3d(); }
    @Override public boolean isBuiltInRenderer() { return originalModel.isBuiltInRenderer(); }
    @Override public TextureAtlasSprite getParticleTexture() { return originalModel.getParticleTexture(); }
    @Override public ItemCameraTransforms getItemCameraTransforms() { return originalModel.getItemCameraTransforms(); }
    @Override public ItemOverrideList getOverrides() { return originalModel.getOverrides(); }
    @Override public boolean isAmbientOcclusion(IBlockState state) { return originalModel.isAmbientOcclusion(state); }
    @Override public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) { return originalModel.handlePerspective(cameraTransformType); }
}
