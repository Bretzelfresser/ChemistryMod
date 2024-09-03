package com.bretzelfresser.chemistry.common.util;

import net.minecraft.resources.ResourceLocation;

public class ResourceLocationUtils {

    public static ResourceLocation prepend(ResourceLocation location, String prepender) {
        return new ResourceLocation(location.getNamespace(), prepender + location.getPath());
    }

    public static ResourceLocation extend(ResourceLocation location, String extender) {
        return new ResourceLocation(location.getNamespace(), location.getPath() + extender);
    }

    public static ResourceLocation prependExtend(ResourceLocation location, String prepender, String extender) {
        return new ResourceLocation(location.getNamespace(), prepender + location.getPath() + extender);
    }
}
