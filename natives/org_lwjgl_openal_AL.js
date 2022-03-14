// This entire object is exported. Feel free to define private helper functions above it.
registerNatives({
  'org/lwjgl/openal/AL': {

    'nCreate(Ljava/lang/String;)V': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nCreateDefault()V': function(thread) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nDestroy()V': function(thread) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'resetNativeStubs(Ljava/lang/Class;)V': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    }

  }
});
