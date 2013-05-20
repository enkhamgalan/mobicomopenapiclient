//
//  MobiComAppDelegate.h
//  client
//
//  Created by Javkhlant on 5/17/13.
//  Copyright (c) 2013 MobiCom. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "NXOAuth2.h"

@class MobiComViewController;

@interface MobiComAppDelegate : UIResponder <UIApplicationDelegate>

+ (NSString*)clientId;
+ (NSString*)secret;
+ (NSString*)scope;
+ (NSString*)otherScope;
+ (NSString*)authUrl;
+ (NSString*)tokenUrl;
+ (NSString*)redirectUrl;
+ (NSString*)accountType;

@property (strong, nonatomic) UIWindow *window;
@property (strong, nonatomic) MobiComViewController *viewController;

+ (void)initialize;

@end
