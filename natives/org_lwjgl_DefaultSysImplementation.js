// This entire object is exported. Feel free to define private helper functions above it.
registerNatives({
  'org/lwjgl/DefaultSysImplementation': {

    'getJNIVersion()I': function(thread, javaThis) {
      return 19;
    },

    'getPointerSize()I': function(thread, javaThis) {
      return 4;
    },

    'setDebug(Z)V': function(thread, javaThis, arg0) {
      
    }

  }
});
