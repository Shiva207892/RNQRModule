import {
  NativeModules,
  View,
  requireNativeComponent,
  NativeEventEmitter,
  LogBox,
  Platform,
} from 'react-native';
import React, {useEffect} from 'react';
import Toast from 'react-native-simple-toast';

LogBox.ignoreLogs([
  'Module RNEventEmitter requires main queue setup since it overrides ',
  'new NativeEventEmitter()',
]);

const QRModule = requireNativeComponent(
  Platform.OS == 'android' ? 'QRModuleAndroid' : 'QRModuleIOS',
);
const eventEmitter = new NativeEventEmitter(NativeModules.RNEventEmitter);

export default function App() {
  useEffect(() => {
    console.log('in use effect');
    eventEmitter.addListener('onCodeSuccess', res =>
      Toast.show(res, Toast.LONG),
    );
    return () => eventEmitter.removeAllListeners('onCodeSuccess');
  });

  return (
    <View
      style={{
        justifyContent: 'center',
        alignItems: 'center',
        height: '100%',
        backgroundColor: 'tomato',
      }}>
      <View
        style={{
          margin: 50,
          overflow: 'hidden',
          position: 'absolute',
          width: '80%',
          height: '40%',
          backgroundColor: 'purple',
        }}>
        <QRModule
          style={{flex: 1, alignItems: 'center', justifyContent: 'center'}}
        />
      </View>
    </View>
  );
}
