// This entire object is exported. Feel free to define private helper functions above it.
registerNatives({
  'org/lwjgl/opengl/GL15': {

    'nglBindBuffer(IIJ)V': function(thread, arg0, arg1, arg2) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nglDeleteBuffers(IJJ)V': function(thread, arg0, arg1, arg2) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nglGenBuffers(IJJ)V': function(thread, arg0, arg1, arg2) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nglIsBuffer(IJ)Z': function(thread, arg0, arg1) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nglBufferData(IJJIJ)V': function(thread, arg0, arg1, arg2, arg3, arg4) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nglBufferSubData(IJJJJ)V': function(thread, arg0, arg1, arg2, arg3, arg4) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nglGetBufferSubData(IJJJJ)V': function(thread, arg0, arg1, arg2, arg3, arg4) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nglMapBuffer(IIJLjava/nio/ByteBuffer;J)Ljava/nio/ByteBuffer;': function(thread, arg0, arg1, arg2, arg3, arg4) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nglUnmapBuffer(IJ)Z': function(thread, arg0, arg1) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nglGetBufferParameteriv(IIJJ)V': function(thread, arg0, arg1, arg2, arg3) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nglGetBufferPointerv(IIJJ)Ljava/nio/ByteBuffer;': function(thread, arg0, arg1, arg2, arg3) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nglGenQueries(IJJ)V': function(thread, arg0, arg1, arg2) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nglDeleteQueries(IJJ)V': function(thread, arg0, arg1, arg2) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nglIsQuery(IJ)Z': function(thread, arg0, arg1) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nglBeginQuery(IIJ)V': function(thread, arg0, arg1, arg2) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nglEndQuery(IJ)V': function(thread, arg0, arg1) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nglGetQueryiv(IIJJ)V': function(thread, arg0, arg1, arg2, arg3) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nglGetQueryObjectiv(IIJJ)V': function(thread, arg0, arg1, arg2, arg3) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nglGetQueryObjectuiv(IIJJ)V': function(thread, arg0, arg1, arg2, arg3) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    }

  }
});
