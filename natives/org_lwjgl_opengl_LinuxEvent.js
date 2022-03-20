// This entire object is exported. Feel free to define private helper functions above it.
registerNatives({
  'org/lwjgl/opengl/LinuxEvent': {

    'createEventBuffer()Ljava/nio/ByteBuffer;': function(thread) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'getPending(J)I': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nSendEvent(Ljava/nio/ByteBuffer;JJZJ)V': function(thread, arg0, arg1, arg2, arg3, arg4) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nFilterEvent(Ljava/nio/ByteBuffer;J)Z': function(thread, arg0, arg1) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nNextEvent(JLjava/nio/ByteBuffer;)V': function(thread, arg0, arg1) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetType(Ljava/nio/ByteBuffer;)I': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetWindow(Ljava/nio/ByteBuffer;)J': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nSetWindow(Ljava/nio/ByteBuffer;J)V': function(thread, arg0, arg1) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetFocusMode(Ljava/nio/ByteBuffer;)I': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetFocusDetail(Ljava/nio/ByteBuffer;)I': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetClientMessageType(Ljava/nio/ByteBuffer;)J': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetClientData(Ljava/nio/ByteBuffer;I)I': function(thread, arg0, arg1) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetClientFormat(Ljava/nio/ByteBuffer;)I': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetButtonTime(Ljava/nio/ByteBuffer;)J': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetButtonState(Ljava/nio/ByteBuffer;)I': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetButtonType(Ljava/nio/ByteBuffer;)I': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetButtonButton(Ljava/nio/ByteBuffer;)I': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetButtonRoot(Ljava/nio/ByteBuffer;)J': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetButtonXRoot(Ljava/nio/ByteBuffer;)I': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetButtonYRoot(Ljava/nio/ByteBuffer;)I': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetButtonX(Ljava/nio/ByteBuffer;)I': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetButtonY(Ljava/nio/ByteBuffer;)I': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetKeyAddress(Ljava/nio/ByteBuffer;)J': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetKeyTime(Ljava/nio/ByteBuffer;)I': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetKeyType(Ljava/nio/ByteBuffer;)I': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetKeyKeyCode(Ljava/nio/ByteBuffer;)I': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetKeyState(Ljava/nio/ByteBuffer;)I': function(thread, arg0) {
      thread.throwNewException('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    }

  }
});
