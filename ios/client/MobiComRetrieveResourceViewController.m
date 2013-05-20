//
//  MobiComRetrieveResourceViewController.m
//  client
//
//  Created by Javkhlant on 5/17/13.
//  Copyright (c) 2013 MobiCom. All rights reserved.
//

#import "MobiComRetrieveResourceViewController.h"
#import "MobiComAppDelegate.h"
#import "MobiComResourceViewController.h"
#import "NSDictionary+QueryString.h"

@interface MobiComRetrieveResourceViewController ()

@end

@implementation MobiComRetrieveResourceViewController

@synthesize accessTokenField;
@synthesize refreshTokenField;
@synthesize expiresInField;
@synthesize resourceEndpointField;
@synthesize scopeField;
@synthesize retrieveResourceButton;

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
    
    accessTokenField.delegate = self;
    refreshTokenField.delegate = self;
    expiresInField.delegate = self;
    resourceEndpointField.delegate = self;
    scopeField.delegate = self;
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

- (void)onRetrieveResourceClick:(id)sender {
    NSString* url = [NSString stringWithFormat:@"%@?access_token=%@", [resourceEndpointField text], [accessTokenField text]];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:url]];
//    [request setHTTPMethod:@"GET"];
    [request setHTTPMethod:@"POST"];
    [request setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
    
    // Get response
    NSHTTPURLResponse* urlResponse = nil;
    NSError* error = [[NSError alloc] init];
    NSData* responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:&urlResponse error:&error];
    NSString* result = [[NSString alloc] initWithData:responseData encoding:NSUTF8StringEncoding];
    
    /* Хариу амжилттай эсэхийг шалгах */
    if ([urlResponse statusCode] >= 200 && [urlResponse statusCode] < 300) {
        NSError *parserError;
        NSDictionary *authData = [NSJSONSerialization JSONObjectWithData:responseData options:0 error:&parserError];
        
        if (authData == nil) {
            /* Буцаж ирсэн хариу утгыг query string болгон decode хийх */
            NSString *responseString = [[NSString alloc] initWithData:responseData encoding:NSUTF8StringEncoding];
            authData = [NSDictionary dictionaryWithFormEncodedString:responseString];
        }
        
        MobiComResourceViewController* view = [[MobiComResourceViewController alloc] initWithNibName:@"MobiComResourceViewController" bundle:nil];
        [self presentModalViewController:view animated:YES];
        [[view valuesLabel] setText:result];
    }
}

@end
