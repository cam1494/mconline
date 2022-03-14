// This entire object is exported. Feel free to define private helper functions above it.
registerNatives({
  'org/lwjgl/opengl/GLContext': {

    'ngetFunctionAddress(J)J': function(thread, arg0) {
	  return 1;
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nLoadOpenGLLibrary()V': function(thread) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nUnloadOpenGLLibrary()V': function(thread) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'resetNativeStubs(Ljava/lang/Class;)V': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    }

  }
});
