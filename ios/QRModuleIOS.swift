//
//  QRModule.swift
//  AwesomeProject
//
//  Created by Shiva on 02/02/23.
//

import Foundation
import BarCodeScanner
import AVFoundation
import UIKit

@objc(QRModuleIOS)
//class QRModule: RCTViewManager {
class QRModuleIOS: RCTViewManager, BarCodeScannerViewDelegate {
  let qrScannerView = BarCodeScannerView()
  
  
//  @objc var onViewReady: RCTDirectEventBlock?
//    // zoom
//    @objc var enableZoomGesture = false {
//      didSet {
//        if enableZoomGesture {
//          addPinchGestureRecognizer()
//        } else {
//          removePinchGestureRecognizer()
//        }
//      }
//    }
  
//  CGFloat(self.zoom.doubleValue)
  
//  @property NSNumber *width; // declaring a strong property

  
//  var width = 100.0;
  
//  #define RCT_EXPORT_VIEW_PROPERTY(width, type)
//    +(NSArray<NSString *> *)propConfig_##name RCT_DYNAMIC
//    {
//      return @[ @ #type ];
//    }
  
//  @objc var width = 0.0
//
//      @objc public func setCount(count: NSNumber) {
//          self.count = count
//          // do something more about animating
//      }
//    @objc func increment() {
//      count = count.intValue + 1 as NSNumber
//    }
  
//  @objc func setCount(_ val: NSNumber) {
//    count = val
//  }
  
//  @objc var width = 100;

//  @objc var height = 100.0;
  
//  override init(frame: CGRect) {
//      super.init(frame: frame)
//      setupView()
//    }
//
//    required init?(coder aDecoder: NSCoder) {
//      super.init(coder: aDecoder)
//      setupView()
//    }
  
  
  @objc
  override func view() -> UIView! {
//    let label = UILabel()
//    label.text = "Hello swift"
//    label.textAlignment = .center
//    return label
    
//    print(self.width);
//    print(height);
//    print("count is: \(count)")
    
    
    let screenRect = UIScreen.main.bounds
    let screenWidth = screenRect.size.width
    let screenHeight = screenRect.size.height
    
//    let windowRect = self.view().window?.frame
//    let windowWidth = windowRect?.size.width
//    let windowHeight = windowRect?.size.height
    
    qrScannerView.frame.size.width = screenWidth
    qrScannerView.frame.size.height = screenHeight
//    qrScannerView.frame.size.width = screenWidth
//    qrScannerView.frame.size.height = screenHeight
    
//    var width: NSNumber = 100 {
//      didSet {
//        qrScannerView.frame.size.width = CGFloat(200.0)
//      }
//    }
//
//    var height: NSNumber = 100 {
//      didSet {
//        qrScannerView.frame.size.height = CGFloat(200.0)
//      }
//    }


    qrScannerView.configure(delegate: self, input: .init(isBlurEffectEnabled: false))
    qrScannerView.startRunning()
    return qrScannerView;
    
//    var myUIView = UIView(frame: CGRect(x: 0, y: 0, width: 200, height: 200));
//
//    myUIView.tintColor = .red
//
////    myUIView = qrScannerView
//
//    return myUIView;
    
    
//    return QRViewController().view;
  }
  
//  //call back to send data back to javascript
//  @objc
//  func getCode(_ callback:RCTResponseSenderBlock) {
//    count += 1;
//    print(count);
//    callback([count])
//    [self sendEventWithName:@"CancelEvent" body:@"Tap`enter code here` on Cancel button"];
//    sendEvent(withName: "onDecrement", body: ["count decreased", codeVal])
//  }

  @objc
  override static func requiresMainQueueSetup() -> Bool{
    return true;
  }

  func barCodeScannerView(_ codeScannerView: BarCodeScanner.BarCodeScannerView, authorizationDidFailure error: BarCodeScanner.BarCodeScannerError) {

  }

  func barCodeScannerView(_ codeScannerView: BarCodeScanner.BarCodeScannerView, didFailure error: BarCodeScanner.BarCodeScannerError) {

  }

  @objc
  func barCodeScannerView(_ codeScannerView: BarCodeScanner.BarCodeScannerView, didSuccess code: String, barCodeType: AVMetadataObject.ObjectType) {
          print("debug barCodeType: barCodeType = \(barCodeType)")
          RNEventEmitter.emitter.sendEvent(withName: "onCodeSuccess", body: code)
          qrScannerView.rescan()
  }

//  @objc
//  override func constantsToExport() -> [AnyHashable: Any]!{
//    return ["initialCount": codeVal]
//  }

//  override func supportedEvents() -> [String]! {
//      return ["onCode"]
//  }
//
//  //promise to send data back to javascript
//  @objc
//  func decrement(_ resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
//    if(count == 0) {
//      let error = NSError(domain: "", code: 200, userInfo: nil);
//      reject("ERROR_COUNT", "count cannot be negative", error);
//    }
//    else {
//      count -= 1;
//      resolve("count is: \(count)");
////      sendEvent(withName: "onDecrement", body: ["count decreased", count])
//    }
//  }
}
