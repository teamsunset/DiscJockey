package com.sunset.discjockey.client.model;

import net.minecraft.client.model.geom.ModelPart;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModelManager {

    public ModelPart initialRoot;

    public Map<String, ModelPart> initialParts;

    public ModelPart root;

    public Map<String, ModelPart> parts;

    public ModelManager(Supplier<ModelPart> rootSupplier) {
        this.initialRoot = rootSupplier.get();
        this.initialParts = flatParts(initialRoot);
        this.root = rootSupplier.get();
        this.parts = flatParts(root);
    }

    public void setRelative(String id, String axis, float value) {
        ModelPart part = parts.get(id);
        ModelPart initialPart = initialParts.get(id);
        if (part == null || initialPart == null) {
            new Exception("ModelManager: setRelative: part or initialPart is null: " + id).printStackTrace();
            return;
        }
        switch (axis) {
            case "x" -> part.x = initialPart.x + value;
            case "y" -> part.y = initialPart.y + value;
            case "z" -> part.z = initialPart.z + value;
        }
    }

    public void rotate(String id, String axis, float value) {
        ModelPart part = parts.get(id);
        ModelPart initialPart = initialParts.get(id);
        if (part == null || initialPart == null) {
            new Exception("ModelManager: setRelative: part or initialPart is null: " + id).printStackTrace();
            return;
        }
        switch (axis) {
            case "x" -> part.xRot = initialPart.xRot + value;
            case "y" -> part.yRot = initialPart.yRot + value;
            case "z" -> part.zRot = initialPart.zRot + value;
        }
    }

    public ModelPart getRoot() {
        return this.root;
    }

    public static Map<String, ModelPart> flatParts(ModelPart root) {
        Map<String, ModelPart> parts = new HashMap<>();
        return flatParts(root, parts);
    }

    public static Map<String, ModelPart> flatParts(ModelPart root, Map<String, ModelPart> parts) {
        for (String name : root.children.keySet()) {
            ModelPart part = root.getChild(name);
            parts.put(name, part);
            flatParts(part, parts);
        }
        return parts;
    }
}
