package com.jhxiaochen.dollancustomized.fixes;

import org.lwjgl.opengl.GL11;

import java.lang.reflect.Method;

public final class OptiFineFogHelper {
    private static Method isFogOff;
    private static boolean resolved;
    private OptiFineFogHelper() {}

    public static boolean isFogOff() {
        if (!resolved) {
            resolved = true;
            try {
                isFogOff = Class.forName("Config").getMethod("isFogOff");
            } catch (Throwable t) {
                isFogOff = null;
            }
        }
        if (isFogOff == null) return false;
        try {
            return (Boolean) isFogOff.invoke(null);
        } catch (Throwable t) {
            return false;
        }
    }

    /** 强制真实 GL_FOG 跟随 OptiFine，陆地/水面两个 pass 统一。 */
    public static void syncFog() {
        if (isFogOff()) GL11.glDisable(GL11.GL_FOG);
        else GL11.glEnable(GL11.GL_FOG);
    }
}
