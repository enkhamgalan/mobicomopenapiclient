//
//  MobiComRequestTokenViewController.m
//  client
//
//  Created by Javkhlant on 5/17/13.
//  Copyright (c) 2013 MobiCom. All rights reserved.
//

#import "MobiComRequestTokenViewController.h"
#import "LRURLRequestOperation.h"
#import "NSDictionary+QueryString.h"
#import "MobiComAppDelegate.h"
#import "MobiComRetrieveResourceViewController.h"

@interface MobiComRequestTokenViewController ()

@end

@implementation MobiComRequestTokenViewController

@synthesize tokenEndpointField;
@synthesize appIdField;
@synthesize appKeyField;
@synthesize authCodeField;
@synthesize redirectUrlField;
@synthesize requestTokenButton;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    tokenEndpointField.delegate = self;
    appIdField.delegate = self;
    appKeyField.delegate = self;
    authCodeField.delegate = self;
    redirectUrlField.delegate = self;
    
    [tokenEndpointField setText:[MobiComAppDelegate tokenUrl]];
    [appIdField setText:[MobiComAppDelegate clientId]];
    [appKeyField setText:[MobiComAppDelegate secret]];
    [redirectUrlField setText:[MobiComAppDelegate redirectUrl]];
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

- (void)verifyAuthorizationWithAccessCode:(NSString *)accessCode {
    @synchronized(self) {
        NSDictionary *params = [NSMutableDictionary dictionary];
        [params setValue:@"authorization_code" forKey:@"grant_type"];
        [params setValue:[appIdField text] forKey:@"client_id"];
        [params setValue:[appKeyField text] forKey:@"client_secret"];
        [params setValue:[redirectUrlField text] forKey:@"redirect_uri"];
        [params setValue:accessCode forKey:@"code"];
        
        NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:[tokenEndpointField text]]];
        [request setHTTPMethod:@"POST"];
        [request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
        [request setHTTPBody:[[params stringWithFormEncodedComponents] dataUsingEncoding:NSUTF8StringEncoding]];
        
        // Get response
        NSHTTPURLResponse* urlResponse = nil;
        NSError* error = [[NSError alloc] init];
        NSData* responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:&urlResponse error:&error];
//        NSString* result = [[NSString alloc] initWithData:responseData encoding:NSUTF8StringEncoding];
        
        /* Хариу амжилттай эсэхийг шалгах */
        if ([urlResponse statusCode] >= 200 && [urlResponse statusCode] < 300) {
            NSError *parserError;
            NSDictionary *authData = [NSJSONSerialization JSONObjectWithData:responseData options:0 error:&parserError];
            
            if (authData == nil) {
                /* Буцаж ирсэн хариу утгыг query string болгон decode хийх */
                NSString *responseString = [[NSString alloc] initWithData:responseData encoding:NSUTF8StringEncoding];
                authData = [NSDictionary dictionaryWithFormEncodedString:responseString];
            }
            
            /* Access token утга байгаа эсэхийг шалгах */
            if ([authData objectForKey:@"access_token"] == nil) {
                NSAssert(NO, @"Unhandled parsing failure");
            }
            
            MobiComRetrieveResourceViewController* view = [[MobiComRetrieveResourceViewController alloc] initWithNibName:@"MobiComRetrieveResourceViewController" bundle:nil];
            [self presentModalViewController:view animated:YES];
            
            [[view accessTokenField] setText:[authData objectForKey:@"access_token"]];
            [[view refreshTokenField] setText:[authData objectForKey:@"refresh_token"]];
            [[view expiresInField] setText:[NSString stringWithFormat:@"%@", [authData objectForKey:@"expires_in"]]];
            [[view scopeField] setText:[MobiComAppDelegate scope]];
            [[view resourceEndpointField] setText:[MobiComAppDelegate scope]];
        } else {
            [NSException raise:@"Connection failure" format:@"status code is %d", [urlResponse statusCode]];
        }
    }
}

- (void)onRequestTokenClick:(id)sender {
    [self verifyAuthorizationWithAccessCode:[authCodeField text]];
}

@end
