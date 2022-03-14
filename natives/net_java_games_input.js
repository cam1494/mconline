// This entire object is exported. Feel free to define private helper functions above it.
registerNatives({
  'net/java/games/input/DummyWindow': {

    'createWindow()J': function(thread) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nDestroy(J)V': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    }

  },

  'net/java/games/input/IDirectInput': {

    'createIDirectInput()J': function(thread) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nEnumDevices(J)V': function(thread, javaThis, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nRelease(J)V': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    }

  },

  'net/java/games/input/IDirectInputDevice': {

    'nCreatePeriodicEffect(J[BIIIIII[I[JIIIIIIIII)J': function(thread, arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nPoll(J)I': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nAcquire(J)I': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nUnacquire(J)I': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetDeviceData(JILnet/java/games/input/DataQueue;[Ljava/lang/Object;II)I': function(thread, arg0, arg1, arg2, arg3, arg4, arg5) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetDeviceState(J[I)I': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nSetDataFormat(JI[Lnet/java/games/input/DIDeviceObject;)I': function(thread, arg0, arg1, arg2) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nEnumEffects(JI)I': function(thread, javaThis, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nEnumObjects(JI)I': function(thread, javaThis, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetRangeProperty(JI[J)I': function(thread, arg0, arg1, arg2) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetDeadzoneProperty(JI)I': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nSetBufferSize(JI)I': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nSetCooperativeLevel(JJI)I': function(thread, arg0, arg1, arg2) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nRelease(J)V': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    }

  },

  'net/java/games/input/IDirectInputEffect': {

    'nRelease(J)V': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nSetGain(JI)I': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nStart(JII)I': function(thread, arg0, arg1, arg2) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nStop(J)I': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    }

  },

  'net/java/games/input/LinuxEventDevice': {

    'nOpen(Ljava/lang/String;Z)J': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nUploadRumbleEffect(JIIIIIIII)I': function(thread, arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nUploadConstantEffect(JIIIIIIIIIII)I': function(thread, arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nEraseEffect(JI)V': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nWriteEvent(JIII)V': function(thread, arg0, arg1, arg2, arg3) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetInputID(J)Lnet/java/games/input/LinuxInputID;': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetNumEffects(J)I': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetVersion(J)I': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetNextEvent(JLnet/java/games/input/LinuxEvent;)Z': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetAbsInfo(JILnet/java/games/input/LinuxAbsInfo;)V': function(thread, arg0, arg1, arg2) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetBits(JI[B)V': function(thread, arg0, arg1, arg2) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetDeviceUsageBits(J[B)V': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetKeyStates(J[B)V': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetName(J)Ljava/lang/String;': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nClose(J)V': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    }

  },

  'net/java/games/input/LinuxJoystickDevice': {

    'nOpen(Ljava/lang/String;)J': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetNextEvent(JLnet/java/games/input/LinuxJoystickEvent;)Z': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetNumButtons(J)I': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetNumAxes(J)I': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetAxisMap(J)[B': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetButtonMap(J)[C': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetVersion(J)I': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetName(J)Ljava/lang/String;': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nClose(J)V': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    }

  },

  'net/java/games/input/OSXHIDDevice': {

    'nGetDeviceProperties(J)Ljava/util/Map;': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nReleaseDevice(JJ)V': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetElementValue(JJLnet/java/games/input/OSXEvent;)V': function(thread, arg0, arg1, arg2) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nCreateQueue(J)J': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nOpen(J)V': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nClose(J)V': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    }

  },

  'net/java/games/input/OSXHIDDeviceIterator': {

    'nCreateIterator()J': function(thread) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nReleaseIterator(J)V': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nNext(J)Lnet/java/games/input/OSXHIDDevice;': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    }

  },

  'net/java/games/input/OSXHIDQueue': {

    'nOpen(JI)V': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nClose(J)V': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nStart(J)V': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nStop(J)V': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nReleaseQueue(J)V': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nAddElement(JJ)V': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nRemoveElement(JJ)V': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetNextEvent(JLnet/java/games/input/OSXEvent;)Z': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    }

  },

  'net/java/games/input/RawDevice': {

    'nGetName(J)Ljava/lang/String;': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetInfo(Lnet/java/games/input/RawDevice;J)Lnet/java/games/input/RawDeviceInfo;': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    }

  },

  'net/java/games/input/RawInputEnvironmentPlugin': {

    'enumerateDevices(Lnet/java/games/input/RawInputEventQueue;Ljava/util/List;)V': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nEnumSetupAPIDevices([BLjava/util/List;)V': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'getKeyboardClassGUID()[B': function(thread) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'getMouseClassGUID()[B': function(thread) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    }

  },

  'net/java/games/input/RawInputEventQueue': {

    'nPoll(J)V': function(thread, javaThis, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nRegisterDevices(IJ[Lnet/java/games/input/RawDeviceInfo;)V': function(thread, arg0, arg1, arg2) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    }

  },

  'net/java/games/input/WinTabContext': {

    'nGetNumberOfSupportedDevices()I': function(thread) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nOpen(J)J': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nClose(J)V': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetPackets(J)[Lnet/java/games/input/WinTabPacket;': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    }

  },

  'net/java/games/input/WinTabDevice': {

    'nGetName(I)Ljava/lang/String;': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetAxisDetails(II)[I': function(thread, arg0, arg1) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetCursorNames(I)[Ljava/lang/String;': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    },

    'nGetMaxButtonCount(I)I': function(thread, arg0) {
      console.log('Ljava/lang/UnsatisfiedLinkError;', 'Native method not implemented.');
    }

  }
});
