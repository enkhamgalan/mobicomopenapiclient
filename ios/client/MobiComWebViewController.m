//
//  MobiComWebViewController.m
//  client
//
//  Created by Javkhlant on 5/17/13.
//  Copyright (c) 2013 MobiCom. All rights reserved.
//

#import "MobiComWebViewController.h"
#import "MobiComAppDelegate.h"
#import "MobiComRequestTokenViewController.h"

@interface MobiComWebViewController ()

@end

@implementation MobiComWebViewController

@synthesize getCodeButton;
@synthesize webView;

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
    
    [[NXOAuth2AccountStore sharedStore] requestAccessToAccountWithType:[MobiComAppDelegate accountType]
                                   withPreparedAuthorizationURLHandler:^(NSURL* preparedURL) {
       // Open a web view or similar
       NSURLRequest *requestObj = [NSURLRequest requestWithURL:preparedURL];
       
       //Load the request in the UIWebView.
       [webView loadRequest:requestObj];
   }];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)onGetCodeClick:(id)sender {
    MobiComRequestTokenViewController* view = [[MobiComRequestTokenViewController alloc] initWithNibName:@"MobiComRequestTokenViewController" bundle:nil];
    [self presentModalViewController:view animated:YES];
    
    // Вэб хуудсын title-аас code хуулж авах 
    NSString* title = [webView stringByEvaluatingJavaScriptFromString: @"document.title"];
    NSRange range = [title rangeOfString:@"="];
    NSString *substring = [[title substringFromIndex:NSMaxRange(range)] stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceCharacterSet]];
    [[view authCodeField] setText:substring];
}

@end
