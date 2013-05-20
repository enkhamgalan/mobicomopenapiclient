//
//  MobiComViewController.m
//  client
//
//  Created by Javkhlant on 5/17/13.
//  Copyright (c) 2013 MobiCom. All rights reserved.
//

#import "MobiComViewController.h"
#import "MobiComAppDelegate.h"
#import "MobiComWebViewController.h"

@interface MobiComViewController ()

@end

@implementation MobiComViewController

@synthesize appIdField;
@synthesize appKeyField;
@synthesize authUrlField;
@synthesize redirectUrlField;
@synthesize scopeField;
@synthesize retrieveCodeButton;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    
    appIdField.delegate = self;
    appKeyField.delegate = self;
    authUrlField.delegate = self;
    redirectUrlField.delegate = self;
    scopeField.delegate = self;
    
    [appIdField setText:[MobiComAppDelegate clientId]];
    [appKeyField setText:[MobiComAppDelegate secret]];
    [authUrlField setText:[MobiComAppDelegate authUrl]];
    [redirectUrlField setText:[MobiComAppDelegate redirectUrl]];
    [scopeField setText:[MobiComAppDelegate scope]];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (BOOL)textFieldShouldReturn:(UITextField*)textField {
    [textField resignFirstResponder];
    return YES;
}

- (BOOL)disablesAutomaticKeyboardDismissal {
    return NO;
}

- (void)onRetrieveCodeClick:(id)sender {
    
    /* Зөвшөөрөл эрх олгох холбоосыг боловсруулах */
    [[NXOAuth2AccountStore sharedStore] setClientID:[appIdField text]
                                             secret:[appKeyField text]
                                              scope:[NSSet setWithObjects:[scopeField text], nil]
                                   authorizationURL:[NSURL URLWithString:[authUrlField text]]
                                           tokenURL:[NSURL URLWithString:@""]
                                        redirectURL:[NSURL URLWithString:[redirectUrlField text]]
                                     forAccountType:@""];
    
    MobiComWebViewController* view = [[MobiComWebViewController alloc] initWithNibName:@"MobiComWebViewController" bundle:nil];
    [self presentModalViewController:view animated:YES];
}

@end
