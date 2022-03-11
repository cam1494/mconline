package com.mojang.authlib;

import java.util.HashMap;
import java.util.Map;

public enum UserType {
   LEGACY("legacy"),
   MOJANG("mojang");

   private static final Map BY_NAME = new HashMap();
   private final String name;

   private UserType(String name) {
      this.name = name;
   }

   public static UserType byName(String name) {
      return (UserType)BY_NAME.get(name.toLowerCase());
   }

   public String getName() {
      return this.name;
   }

   static {
      UserType[] arr$ = values();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         UserType type = arr$[i$];
         BY_NAME.put(type.name, type);
      }

   }
}
