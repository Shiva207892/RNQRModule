//
//  RNEventEmitter.swift
//  AwesomeProject
//
//  Created by Shiva on 05/02/23.
//

import Foundation

@objc(RNEventEmitter)
open class RNEventEmitter: RCTEventEmitter {

  public static var emitter: RCTEventEmitter!

  override init() {
    super.init()
    RNEventEmitter.emitter = self
  }

  open override func supportedEvents() -> [String] {
    ["onCodeSuccess"]      // etc.
  }
  
  @objc
  public override static func requiresMainQueueSetup() -> Bool{
    return true;
  }
}
