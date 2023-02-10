//
//  QRModule.m
//  AwesomeProject
//
//  Created by Shiva on 02/02/23.
//

#import <Foundation/Foundation.h>

#import "React/RCTBridgeModule.h"
#import "React/RCTEventEmitter.h"
#import "React/RCTViewManager.h"
#import "CoreGraphics/CGGeometry.h"

@interface RCT_EXTERN_MODULE(QRModuleIOS, RCTViewManager)

//RCT_EXPORT_VIEW_PROPERTY(width, NSNumber)
//
//RCT_EXPORT_VIEW_PROPERTY(height, NSNumber)
//
//RCT_CUSTOM_VIEW_PROPERTY(width, NSNumber, QRModule) {
//
//    NSLog(@"%@ -- width is: -- ", json[0]);
//
////    width = [RCTConvert NSNumber:json];
////    [view.tableView reloadData];
//}

//RCT_EXPORT_VIEW_PROPERTY(count, NSNumber)

//RCT_CUSTOM_VIEW_PROPERTY(width, NSNumber, QRModule)
//{
//  NSLog(@"%@ -- ", json[0]);
//
//}
//
//RCT_CUSTOM_VIEW_PROPERTY(height, NSNumber, QRModule)
//{
//  NSLog(@"%@ -- ", json[0]);
//}

//RCT_CUSTOM_VIEW_PROPERTY(customProperty, NSArray, QRModule)
//{
//  NSLog(@"%@ -- %@ == %@", json[0], json[1], self.view);
////  self.view.frame.size.width=100;
//}

//RCT_EXTERN_METHOD(getCode:(RCTResponseSenderBlock)callback)

RCT_EXTERN_METHOD(decrement:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject)
- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
@end

@interface RCT_EXTERN_MODULE(RNEventEmitter, RCTEventEmitter)

RCT_EXTERN_METHOD(supportedEvents)

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

@end
