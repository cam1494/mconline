package net.minecraft.src;

import com.google.gson.JsonDeserializer;

public interface MetadataSectionSerializer extends JsonDeserializer {
   String getSectionName();
}
