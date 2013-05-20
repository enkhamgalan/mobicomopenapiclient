//
//  MobiComWebViewController.h
//  client
//
//  Created by Javkhlant on 5/17/13.
//  Copyright (c) 2013 MobiCom. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MobiComWebViewController : UIViewController {
    IBOutlet UIWebView* webView;
}

- (IBAction)onGetCodeClick:(id)sender;

@property (nonatomic, retain) IBOutlet UIButton* getCodeButton;
@property (nonatomic, retain) UIWebView* webView;

@end
