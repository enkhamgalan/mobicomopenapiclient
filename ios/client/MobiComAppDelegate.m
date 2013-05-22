//
//  MobiComAppDelegate.m
//  client
//
//  Created by Javkhlant on 5/17/13.
//  Copyright (c) 2013 MobiCom. All rights reserved.
//

#import "MobiComAppDelegate.h"
#import "MobiComViewController.h"

/* MobiCom Open API баталгаажуулалт */
static NSString* clientId = @"ТАНЫ АПП АйДи"; // app ID
static NSString* secret = @"ТАНЫ АПП КЕЙ"; // app key
static NSString* scope = @"http://api.mobicom.mn/oauth/v1/resource/voicemail/on"; // scope - хамрах эрх
static NSString* otherScope = @""; // бусад scope
static NSString* authUrl = @"http://api.mobicom.mn/oauth/v1/authorizer/authz"; // баталгаажуулах холбоос
static NSString* tokenUrl = @"http://api.mobicom.mn/oauth/v1/tokenizer/token"; // зөвшөөрөл авах холбоос
static NSString* redirectUrl = @"http://localhost5"; // амжилттай тохиолдолд буцах холбоос
static NSString* accountType = @"VoiceMail"; // үйлчилгээний төрөл

@implementation MobiComAppDelegate

+ (NSString*) clientId { return clientId; }
+ (NSString*) secret { return secret; }
+ (NSString*) scope { return scope; }
+ (NSString*) otherScope { return scope; }
+ (NSString*) authUrl { return authUrl; }
+ (NSString*) tokenUrl { return tokenUrl; }
+ (NSString*) redirectUrl { return redirectUrl; }
+ (NSString*) accountType { return accountType; }

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    // Override point for customization after application launch.
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
        // iPhone тохиолдолд
        self.viewController = [[MobiComViewController alloc] initWithNibName:@"MobiComViewController_iPhone" bundle:nil];
    } else {
        // iPad тохиолдолд
        self.viewController = [[MobiComViewController alloc] initWithNibName:@"MobiComViewController_iPad" bundle:nil];
    }
    self.window.rootViewController = self.viewController;
    [self.window makeKeyAndVisible];
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

+ (void)initialize {
    /* Client тохиргоо */
    [[NXOAuth2AccountStore sharedStore] setClientID:clientId
                                             secret:secret
                                              scope:[NSSet setWithObjects:scope, otherScope, nil]
                                   authorizationURL:[NSURL URLWithString:authUrl]
                                           tokenURL:[NSURL URLWithString:tokenUrl]
                                        redirectURL:[NSURL URLWithString:redirectUrl]
                                     forAccountType:accountType];
}

@end
