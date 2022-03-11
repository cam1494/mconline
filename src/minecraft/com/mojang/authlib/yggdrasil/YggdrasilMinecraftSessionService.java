//
// Decompiled by Procyon v0.5.36
//

package com.mojang.authlib.yggdrasil;

import org.apache.logging.log4j.LogManager;
import com.mojang.authlib.AuthenticationService;
import java.net.URISyntaxException;
import java.net.URI;
import com.mojang.authlib.yggdrasil.response.MinecraftProfilePropertiesResponse;
import java.util.Iterator;
import com.google.gson.JsonParseException;
import com.mojang.authlib.yggdrasil.response.MinecraftTexturesPayload;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.google.common.collect.Iterables;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.Map;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.google.common.collect.Multimap;
import com.mojang.authlib.yggdrasil.response.HasJoinedMinecraftServerResponse;
import java.util.HashMap;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.response.Response;
import com.mojang.authlib.yggdrasil.request.JoinMinecraftServerRequest;
import java.security.spec.KeySpec;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import org.apache.commons.io.IOUtils;
import com.google.common.cache.CacheLoader;
import java.util.concurrent.TimeUnit;
import com.google.common.cache.CacheBuilder;
import java.lang.reflect.Type;
import com.mojang.util.UUIDTypeAdapter;
import java.util.UUID;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.GameProfile;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import java.security.PublicKey;
import java.net.URL;
import org.apache.logging.log4j.Logger;
import com.mojang.authlib.minecraft.HttpMinecraftSessionService;

public class YggdrasilMinecraftSessionService extends HttpMinecraftSessionService
{
   private static final String[] WHITELISTED_DOMAINS;
   private static final Logger LOGGER;
   private static final String BASE_URL = "https://sessionserver.mojang.com/session/minecraft/";
   private static final URL JOIN_URL;
   private static final URL CHECK_URL;
   private static final byte[] yg_sess_key = new byte[] {(byte)0x30, (byte)0x82, (byte)0x2, (byte)0x22, (byte)0x30, (byte)0xd, (byte)0x6, (byte)0x9, (byte)0x2a, (byte)0x86, (byte)0x48, (byte)0x86, (byte)0xf7, (byte)0xd, (byte)0x1, (byte)0x1, (byte)0x1, (byte)0x5, (byte)0x0, (byte)0x3, (byte)0x82, (byte)0x2, (byte)0xf, (byte)0x0, (byte)0x30, (byte)0x82, (byte)0x2, (byte)0xa, (byte)0x2, (byte)0x82, (byte)0x2, (byte)0x1, (byte)0x0, (byte)0xca, (byte)0x50, (byte)0x78, (byte)0x7, (byte)0xa9, (byte)0xb9, (byte)0x97, (byte)0x3e, (byte)0xe3, (byte)0xc2, (byte)0xb7, (byte)0x5, (byte)0xcf, (byte)0xa1, (byte)0x5d, (byte)0xfd, (byte)0xf9, (byte)0xdf, (byte)0x52, (byte)0x17, (byte)0x2f, (byte)0x97, (byte)0x1b, (byte)0x13, (byte)0x4a, (byte)0x7e, (byte)0x64, (byte)0x20, (byte)0xaf, (byte)0xf6, (byte)0x68, (byte)0x61, (byte)0xb5, (byte)0xb, (byte)0x79, (byte)0xc, (byte)0xcb, (byte)0x85, (byte)0x63, (byte)0x87, (byte)0x9, (byte)0x71, (byte)0x17, (byte)0xa0, (byte)0x45, (byte)0x51, (byte)0x29, (byte)0xdf, (byte)0xc6, (byte)0x13, (byte)0x6c, (byte)0xc0, (byte)0xf8, (byte)0xe1, (byte)0xdd, (byte)0x98, (byte)0xad, (byte)0x90, (byte)0x1c, (byte)0x44, (byte)0x0, (byte)0x56, (byte)0x36, (byte)0xe6, (byte)0xec, (byte)0xe4, (byte)0x24, (byte)0x54, (byte)0x70, (byte)0x1f, (byte)0x5, (byte)0x40, (byte)0xf6, (byte)0x67, (byte)0x70, (byte)0x8d, (byte)0xab, (byte)0x21, (byte)0x5c, (byte)0x82, (byte)0xed, (byte)0x47, (byte)0xa3, (byte)0xaa, (byte)0x74, (byte)0xb5, (byte)0xd7, (byte)0x15, (byte)0x5c, (byte)0x94, (byte)0x3c, (byte)0x85, (byte)0x11, (byte)0x2c, (byte)0xe4, (byte)0x96, (byte)0xa8, (byte)0x63, (byte)0x41, (byte)0xb5, (byte)0xbd, (byte)0x3c, (byte)0x10, (byte)0xde, (byte)0x5f, (byte)0x32, (byte)0x71, (byte)0xbc, (byte)0x88, (byte)0x52, (byte)0xe0, (byte)0x10, (byte)0x92, (byte)0x1, (byte)0x33, (byte)0x1b, (byte)0x3d, (byte)0x6, (byte)0xf1, (byte)0x77, (byte)0xb3, (byte)0x7f, (byte)0xc5, (byte)0x86, (byte)0xe1, (byte)0xf2, (byte)0x3c, (byte)0xa8, (byte)0x95, (byte)0x4d, (byte)0x99, (byte)0x97, (byte)0x63, (byte)0x45, (byte)0x98, (byte)0xe1, (byte)0x9c, (byte)0x98, (byte)0xaa, (byte)0xfa, (byte)0xa, (byte)0x47, (byte)0xe5, (byte)0xe3, (byte)0x69, (byte)0x34, (byte)0x4f, (byte)0x3e, (byte)0xf0, (byte)0x6c, (byte)0xd, (byte)0xae, (byte)0x16, (byte)0x23, (byte)0x6b, (byte)0xb5, (byte)0xcf, (byte)0x15, (byte)0xcc, (byte)0x78, (byte)0x79, (byte)0x68, (byte)0x75, (byte)0x3d, (byte)0xe5, (byte)0x9c, (byte)0x2c, (byte)0xd5, (byte)0xbf, (byte)0x23, (byte)0x98, (byte)0x4, (byte)0xb5, (byte)0xcb, (byte)0xe8, (byte)0x22, (byte)0x60, (byte)0x7, (byte)0x2b, (byte)0x8e, (byte)0xc5, (byte)0xc7, (byte)0x9, (byte)0xf2, (byte)0x19, (byte)0xd7, (byte)0x7c, (byte)0x5f, (byte)0x72, (byte)0x63, (byte)0x81, (byte)0x74, (byte)0x69, (byte)0xe2, (byte)0xf4, (byte)0xf3, (byte)0x42, (byte)0x73, (byte)0xff, (byte)0x7f, (byte)0x84, (byte)0x4, (byte)0xd1, (byte)0xbc, (byte)0x44, (byte)0xee, (byte)0x1, (byte)0xca, (byte)0x1b, (byte)0x41, (byte)0x7f, (byte)0x83, (byte)0x72, (byte)0xf0, (byte)0xd4, (byte)0x3a, (byte)0xb2, (byte)0xd5, (byte)0xac, (byte)0x8d, (byte)0xf7, (byte)0x94, (byte)0x22, (byte)0xf1, (byte)0xfb, (byte)0x6d, (byte)0x4f, (byte)0xf8, (byte)0xcc, (byte)0x26, (byte)0x1c, (byte)0x60, (byte)0xea, (byte)0xb8, (byte)0x5a, (byte)0xb2, (byte)0x27, (byte)0x5c, (byte)0x7a, (byte)0x92, (byte)0xf2, (byte)0xaa, (byte)0xee, (byte)0xe, (byte)0x62, (byte)0x25, (byte)0xec, (byte)0xfe, (byte)0x57, (byte)0x5c, (byte)0x67, (byte)0x1a, (byte)0x6e, (byte)0xec, (byte)0xd0, (byte)0xb2, (byte)0xd3, (byte)0xdf, (byte)0xfe, (byte)0x1d, (byte)0x82, (byte)0x44, (byte)0x5, (byte)0x29, (byte)0x3f, (byte)0xf3, (byte)0xe5, (byte)0x1d, (byte)0x77, (byte)0xc, (byte)0x96, (byte)0xf7, (byte)0xb0, (byte)0x8e, (byte)0x61, (byte)0x94, (byte)0xe8, (byte)0xc7, (byte)0xc3, (byte)0x2b, (byte)0xe9, (byte)0x62, (byte)0x7d, (byte)0x27, (byte)0xd, (byte)0x63, (byte)0x8, (byte)0xcf, (byte)0xc5, (byte)0x1a, (byte)0x38, (byte)0x9c, (byte)0xa4, (byte)0xc6, (byte)0x15, (byte)0x97, (byte)0xff, (byte)0xd1, (byte)0xaa, (byte)0x87, (byte)0xa, (byte)0x1f, (byte)0xc4, (byte)0x2, (byte)0xc4, (byte)0x32, (byte)0x2c, (byte)0xaa, (byte)0xce, (byte)0x21, (byte)0x84, (byte)0x44, (byte)0xd2, (byte)0x50, (byte)0xc6, (byte)0xdd, (byte)0xbc, (byte)0xc1, (byte)0x87, (byte)0xca, (byte)0x20, (byte)0x20, (byte)0xc2, (byte)0xc0, (byte)0xba, (byte)0xb, (byte)0x0, (byte)0x7c, (byte)0x16, (byte)0xfa, (byte)0x33, (byte)0x79, (byte)0xaa, (byte)0xa, (byte)0xa2, (byte)0x9a, (byte)0x4c, (byte)0xc0, (byte)0x1f, (byte)0xfd, (byte)0x50, (byte)0x23, (byte)0x87, (byte)0x91, (byte)0x45, (byte)0x26, (byte)0x78, (byte)0xd2, (byte)0xfb, (byte)0xb6, (byte)0xc4, (byte)0xf9, (byte)0xb6, (byte)0x44, (byte)0x57, (byte)0x4a, (byte)0xb0, (byte)0x6b, (byte)0x1d, (byte)0x42, (byte)0x12, (byte)0x83, (byte)0xcf, (byte)0x24, (byte)0xbd, (byte)0x5f, (byte)0x21, (byte)0x8, (byte)0xce, (byte)0x4b, (byte)0xcc, (byte)0xe3, (byte)0xd3, (byte)0xce, (byte)0xa0, (byte)0x8e, (byte)0x91, (byte)0x91, (byte)0xa, (byte)0xd4, (byte)0xb2, (byte)0xf7, (byte)0xe6, (byte)0xd3, (byte)0x3d, (byte)0x37, (byte)0xd3, (byte)0x47, (byte)0xf4, (byte)0x89, (byte)0xf9, (byte)0x55, (byte)0x1, (byte)0xe4, (byte)0x6d, (byte)0x88, (byte)0x78, (byte)0x22, (byte)0x2d, (byte)0x90, (byte)0xd1, (byte)0xe9, (byte)0x25, (byte)0x7d, (byte)0xb2, (byte)0x82, (byte)0x88, (byte)0xc5, (byte)0x67, (byte)0xd9, (byte)0xf9, (byte)0x16, (byte)0x78, (byte)0xfb, (byte)0x47, (byte)0xd6, (byte)0x67, (byte)0x38, (byte)0xcf, (byte)0x8b, (byte)0xf2, (byte)0x7c, (byte)0x88, (byte)0x8c, (byte)0xfb, (byte)0xb7, (byte)0x69, (byte)0xe3, (byte)0xef, (byte)0xb5, (byte)0xca, (byte)0xd7, (byte)0x97, (byte)0x22, (byte)0xe1, (byte)0x1f, (byte)0xf8, (byte)0x6f, (byte)0x39, (byte)0xf2, (byte)0xe, (byte)0x8c, (byte)0xfc, (byte)0x4e, (byte)0x96, (byte)0xa5, (byte)0xbe, (byte)0x3b, (byte)0x94, (byte)0x30, (byte)0x48, (byte)0x53, (byte)0xf8, (byte)0x38, (byte)0x47, (byte)0x32, (byte)0x2c, (byte)0x5, (byte)0x20, (byte)0xd, (byte)0xd5, (byte)0x20, (byte)0xc9, (byte)0x6d, (byte)0xfa, (byte)0x67, (byte)0x99, (byte)0x81, (byte)0xdb, (byte)0xce, (byte)0x6e, (byte)0x29, (byte)0x6f, (byte)0xe2, (byte)0x82, (byte)0x1c, (byte)0xd9, (byte)0xb0, (byte)0xe4, (byte)0xde, (byte)0xbd, (byte)0x55, (byte)0x6, (byte)0xd9, (byte)0xc, (byte)0x3, (byte)0x22, (byte)0xd3, (byte)0x9b, (byte)0x21, (byte)0x5e, (byte)0xa2, (byte)0xf1, (byte)0x10, (byte)0xb1, (byte)0x15, (byte)0xb3, (byte)0x76, (byte)0xa0, (byte)0x31, (byte)0xf2, (byte)0x3d, (byte)0xa3, (byte)0xb9, (byte)0x8f, (byte)0x5b, (byte)0x53, (byte)0x68, (byte)0x13, (byte)0x72, (byte)0x56, (byte)0xdf, (byte)0x2, (byte)0x3, (byte)0x1, (byte)0x0, (byte)0x1};
   private final PublicKey publicKey;
   private final Gson gson;
   private final LoadingCache<GameProfile, GameProfile> insecureProfiles;

   protected YggdrasilMinecraftSessionService(final YggdrasilAuthenticationService authenticationService) {
      super(authenticationService);
      this.gson = new GsonBuilder().registerTypeAdapter((Type)UUID.class, (Object)new UUIDTypeAdapter()).create();
      this.insecureProfiles = (LoadingCache<GameProfile, GameProfile>)CacheBuilder.newBuilder().expireAfterWrite(6L, TimeUnit.HOURS).build((CacheLoader)new CacheLoader<GameProfile, GameProfile>() {
         public GameProfile load(final GameProfile key) throws Exception {
            return YggdrasilMinecraftSessionService.this.fillGameProfile(key, false);
         }
      });
      try {
    	 YggdrasilMinecraftSessionService.LOGGER.info("loading ygg key");
         final X509EncodedKeySpec spec = new X509EncodedKeySpec(yg_sess_key);
         final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
         this.publicKey = keyFactory.generatePublic(spec);
      }
      catch (Exception e) {
         throw new Error("Missing/invalid yggdrasil public key!");
      }
   }

   @Override
   public void joinServer(final GameProfile profile, final String authenticationToken, final String serverId) throws AuthenticationException {
      final JoinMinecraftServerRequest request = new JoinMinecraftServerRequest();
      request.accessToken = authenticationToken;
      request.selectedProfile = profile.getId();
      request.serverId = serverId;
      this.getAuthenticationService().makeRequest(YggdrasilMinecraftSessionService.JOIN_URL, request, Response.class);
   }

   @Override
   public GameProfile hasJoinedServer(final GameProfile user, final String serverId) throws AuthenticationUnavailableException {
      final Map<String, Object> arguments = new HashMap<String, Object>();
      arguments.put("username", user.getName());
      arguments.put("serverId", serverId);
      final URL url = HttpAuthenticationService.concatenateURL(YggdrasilMinecraftSessionService.CHECK_URL, HttpAuthenticationService.buildQuery(arguments));
      try {
         final HasJoinedMinecraftServerResponse response = this.getAuthenticationService().makeRequest(url, null, HasJoinedMinecraftServerResponse.class);
         if (response != null && response.getId() != null) {
            final GameProfile result = new GameProfile(response.getId(), user.getName());
            if (response.getProperties() != null) {
               result.getProperties().putAll((Multimap)response.getProperties());
            }
            return result;
         }
         return null;
      }
      catch (AuthenticationUnavailableException e) {
         throw e;
      }
      catch (AuthenticationException e2) {
         return null;
      }
   }

   @Override
   public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures(final GameProfile profile, final boolean requireSecure) {
      final Property textureProperty = (Property)Iterables.getFirst((Iterable)profile.getProperties().get("textures"), (Object)null);
      if (textureProperty == null) {
         return new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
      }
      if (requireSecure) {
         if (!textureProperty.hasSignature()) {
            YggdrasilMinecraftSessionService.LOGGER.error("Signature is missing from textures payload");
            throw new InsecureTextureException("Signature is missing from textures payload");
         }
         if (!textureProperty.isSignatureValid(this.publicKey)) {
            YggdrasilMinecraftSessionService.LOGGER.error("Textures payload has been tampered with (signature invalid)");
            throw new InsecureTextureException("Textures payload has been tampered with (signature invalid)");
         }
      }
      MinecraftTexturesPayload result;
      try {
         final String json = new String(Base64.decodeBase64(textureProperty.getValue()), Charsets.UTF_8);
         result = (MinecraftTexturesPayload)this.gson.fromJson(json, (Class)MinecraftTexturesPayload.class);
      }
      catch (JsonParseException e) {
         YggdrasilMinecraftSessionService.LOGGER.error("Could not decode textures payload", (Throwable)e);
         return new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
      }
      if (result.getTextures() == null) {
         return new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
      }
      for (Object entry : result.getTextures().entrySet()) {
         if (!isWhitelistedDomain(((Map.Entry<MinecraftProfileTexture.Type, MinecraftProfileTexture>) entry).getValue().getUrl())) {
            YggdrasilMinecraftSessionService.LOGGER.error("Textures payload has been tampered with (non-whitelisted domain)");
            return new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
         }
      }
      return result.getTextures();
   }

   @Override
   public GameProfile fillProfileProperties(final GameProfile profile, final boolean requireSecure) {
      if (profile.getId() == null) {
         return profile;
      }
      if (!requireSecure) {
         return (GameProfile)this.insecureProfiles.getUnchecked(profile);
      }
      return this.fillGameProfile(profile, true);
   }

   protected GameProfile fillGameProfile(final GameProfile profile, final boolean requireSecure) {
      try {
         URL url = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/profile/" + UUIDTypeAdapter.fromUUID(profile.getId()));
         url = HttpAuthenticationService.concatenateURL(url, "unsigned=" + !requireSecure);
         final MinecraftProfilePropertiesResponse response = this.getAuthenticationService().makeRequest(url, null, MinecraftProfilePropertiesResponse.class);
         if (response == null) {
            YggdrasilMinecraftSessionService.LOGGER.debug("Couldn't fetch profile properties for " + profile + " as the profile does not exist");
            return profile;
         }
         final GameProfile result = new GameProfile(response.getId(), response.getName());
         result.getProperties().putAll((Multimap)response.getProperties());
         profile.getProperties().putAll((Multimap)response.getProperties());
         YggdrasilMinecraftSessionService.LOGGER.debug("Successfully fetched profile properties for " + profile);
         return result;
      }
      catch (AuthenticationException e) {
         YggdrasilMinecraftSessionService.LOGGER.warn("Couldn't look up profile properties for " + profile, (Throwable)e);
         return profile;
      }
   }

   @Override
   public YggdrasilAuthenticationService getAuthenticationService() {
      return (YggdrasilAuthenticationService)super.getAuthenticationService();
   }

   private static boolean isWhitelistedDomain(final String url) {
      URI uri = null;
      try {
         uri = new URI(url);
      }
      catch (URISyntaxException e) {
         throw new IllegalArgumentException("Invalid URL '" + url + "'");
      }
      final String domain = uri.getHost();
      for (int i = 0; i < YggdrasilMinecraftSessionService.WHITELISTED_DOMAINS.length; ++i) {
         if (domain.endsWith(YggdrasilMinecraftSessionService.WHITELISTED_DOMAINS[i])) {
            return true;
         }
      }
      return false;
   }

   static {
      WHITELISTED_DOMAINS = new String[] { ".minecraft.net", ".mojang.com" };
      LOGGER = LogManager.getLogger();
      JOIN_URL = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/join");
      CHECK_URL = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/hasJoined");
   }
}
